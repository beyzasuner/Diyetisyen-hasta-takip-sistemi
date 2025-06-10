package com.diyetapp.backend.controller;

import com.diyetapp.backend.dto.OgunCreateDTO;
import com.diyetapp.backend.dto.OgunDTO;
import com.diyetapp.backend.dto.OgunEkleDTO;
import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.entity.Ogun;
import com.diyetapp.backend.mapper.OgunMapper;
import com.diyetapp.backend.repository.HastaRepository;
import com.diyetapp.backend.repository.OgunRepository;
import com.diyetapp.backend.service.OgunService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ogunler")
public class OgunController {

    private final OgunService ogunService;
    private final OgunRepository ogunRepository;
    private final HastaRepository hastaRepository;
    private final OgunMapper ogunMapper;

    public OgunController(OgunService ogunService,OgunRepository ogunRepository, HastaRepository hastaRepository, OgunMapper ogunMapper) {
        this.ogunService = ogunService;
        this.ogunRepository = ogunRepository;
        this.hastaRepository = hastaRepository;
        this.ogunMapper = ogunMapper;
    }

    @PostMapping
    public ResponseEntity<OgunDTO> create(@RequestBody Map<String, Object> dtoMap, Authentication auth) {
        String tcNo = auth.getName();
        if (tcNo.startsWith("HASTA-")) {
            tcNo = tcNo.replace("HASTA-", "");
        }

        // Tarihi LocalDate'e manuel çeviriyoruz (dto yerine map)
        String tarihStr = dtoMap.get("tarih").toString();
        LocalDate tarih = LocalDate.parse(tarihStr);

        // Hasta kontrolü
        Hasta hasta = hastaRepository.findByTcNo(tcNo)
                .orElseThrow(() -> new RuntimeException("Hasta bulunamadı"));

        // Ogun nesnesini oluştur
        Ogun ogun = new Ogun();
        ogun.setHasta(hasta);
        ogun.setOgunTuru(dtoMap.get("ogunTuru").toString());
        ogun.setOgunAdi(dtoMap.get("ogunAdi").toString());
        ogun.setOgunAdiIngilizce(dtoMap.get("ogunAdiIngilizce").toString());
        ogun.setYemekDetayi(dtoMap.get("yemekDetayi").toString());
        ogun.setKaloriMiktari(Double.parseDouble(dtoMap.get("kaloriMiktari").toString()));
        ogun.setPorsiyonMiktari(Double.parseDouble(dtoMap.get("porsiyonMiktari").toString()));
        ogun.setTarih(tarih);

        // Opsiyonel makro verileri
        if (dtoMap.containsKey("karbonhidratMiktari")) {
            ogun.setKarbonhidratMiktari(Double.parseDouble(dtoMap.get("karbonhidratMiktari").toString()));
        }
        if (dtoMap.containsKey("proteinMiktari")) {
            ogun.setProteinMiktari(Double.parseDouble(dtoMap.get("proteinMiktari").toString()));
        }
        if (dtoMap.containsKey("yagMiktari")) {
            ogun.setYagMiktari(Double.parseDouble(dtoMap.get("yagMiktari").toString()));
        }

        // Kaydet ve döndür
        Ogun saved = ogunRepository.save(ogun);
        return ResponseEntity.ok(ogunMapper.toDTO(saved));
    }



    @GetMapping
    public ResponseEntity<List<OgunDTO>> getOgunlerByToken(Authentication auth) {
        String tcNo = auth.getName();
        List<OgunDTO> list = ogunService.getOgunListByHastaTc(tcNo);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/tarih")
    public ResponseEntity<List<OgunDTO>> getOgunlerByTokenAndDate(
            @RequestParam("tarih") LocalDate tarih,
            Authentication auth
    ) {
        String tcNo = auth.getName();
        List<OgunDTO> list = ogunService.getOgunListByHastaTcAndDate(tcNo, tarih);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/hasta/{tcNo}")
    public ResponseEntity<List<OgunDTO>> getOgunlerByHastaTc(@PathVariable String tcNo) {
        List<OgunDTO> list = ogunService.getOgunListByHastaTc(tcNo);
        return ResponseEntity.ok(list);
    }

    // ✅ 4. BUGÜNÜN öğünlerini getir (tarih path parametresiyle)
    @GetMapping("/me/{tarih}")
    public ResponseEntity<List<OgunDTO>> getOgunlerByDateAndAuth(@PathVariable String tarih, Authentication auth) {
        String tcNo = auth.getName();
        if (tcNo.startsWith("HASTA-")) {
            tcNo = tcNo.replace("HASTA-", "");
        }
        LocalDate localDate = LocalDate.parse(tarih);
        List<OgunDTO> list = ogunService.getOgunListByHastaTcAndDate(tcNo, localDate);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/makrolar/{hastaId}/{tarih}")
    public ResponseEntity<Map<String, Double>> getMakrolarForHastaAndDate(
            @PathVariable Long hastaId,
            @PathVariable String tarih,
            Authentication auth) {

        LocalDate date = LocalDate.parse(tarih);


        // 🔐 Güvenlik kontrolü
        String girisYapanTc = auth.getName().replace("HASTA-", "");

        // Eğer kullanıcı hasta ise kendi verisini görsün, başkasını göremesin
        Hasta girisYapanHasta = hastaRepository.findByTcNo(girisYapanTc).orElse(null);
        if (girisYapanHasta != null && !girisYapanHasta.getId().equals(hastaId)) {
            return ResponseEntity.status(403).build(); // ❌ yetkisiz erişim
        }

        List<Ogun> ogunler = ogunRepository.findByHasta_IdAndTarih(hastaId, date);

        double karbonhidrat = 0, protein = 0, yag = 0;
        for (Ogun ogun : ogunler) {
            karbonhidrat += ogun.getKarbonhidratMiktari() != null ? ogun.getKarbonhidratMiktari() : 0;
            protein += ogun.getProteinMiktari() != null ? ogun.getProteinMiktari() : 0;
            yag += ogun.getYagMiktari() != null ? ogun.getYagMiktari() : 0;
        }

        Map<String, Double> makroMap = Map.of(
                "karbonhidrat", karbonhidrat,
                "protein", protein,
                "yag", yag
        );
        System.out.println("🔐 Giriş yapan kullanıcı: " + auth.getName());
        System.out.println("🧾 İstenen hastaId: " + hastaId);


        return ResponseEntity.ok(makroMap);
    }
    @GetMapping("/makrolar/me/{tarih}")
    public ResponseEntity<Map<String, Double>> getMakrolarForCurrentHastaAndDate(
            @PathVariable String tarih,
            Authentication auth
    ) {
        String tcNo = auth.getName();
        System.out.println("👤 Giriş yapan kullanıcı: " + auth.getName());
        System.out.println("🟢 auth.getName() = " + auth.getName());
        System.out.println("🔵 Temizlenen tcNo = " + tcNo);


        if (tcNo.startsWith("HASTA-")) {
            tcNo = tcNo.replace("HASTA-", "");
        }
        System.out.println("🟡 findByTcNo ile hasta aranıyor: " + tcNo);


        Hasta hasta = hastaRepository.findByTcNo(tcNo)
                .orElseThrow(() -> new RuntimeException("Hasta bulunamadı"));

        LocalDate date = LocalDate.parse(tarih);
        List<Ogun> ogunler = ogunRepository.findByHasta_IdAndTarih(hasta.getId(), date);

        double karbonhidrat = 0, protein = 0, yag = 0;

        for (Ogun ogun : ogunler) {
            karbonhidrat += ogun.getKarbonhidratMiktari() != null ? ogun.getKarbonhidratMiktari() : 0;
            protein += ogun.getProteinMiktari() != null ? ogun.getProteinMiktari() : 0;
            yag += ogun.getYagMiktari() != null ? ogun.getYagMiktari() : 0;
        }

        Map<String, Double> makroMap = Map.of(
                "karbonhidrat", karbonhidrat,
                "protein", protein,
                "yag", yag
        );

        return ResponseEntity.ok(makroMap);
    }



}
