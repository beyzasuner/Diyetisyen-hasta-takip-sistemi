package com.diyetapp.backend.controller;


import com.diyetapp.backend.dto.HastaCreateWithIdDTO;
import com.diyetapp.backend.dto.HastaDTO;
import com.diyetapp.backend.dto.HastaUpdateDTO;
import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.mapper.HastaMapper;
import com.diyetapp.backend.service.HastaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/hastalar")
public class HastaController {

    private final HastaService hastaService;
    private final HastaMapper hastaMapper;

    public HastaController(HastaService hastaService, HastaMapper hastaMapper) {
        this.hastaService = hastaService;
        this.hastaMapper = hastaMapper;
    }

    // ✅ Yeni hasta oluştur (POST)
    @PostMapping
    public ResponseEntity<HastaDTO> createHasta(@RequestBody HastaCreateWithIdDTO dto) {
        Hasta hasta = hastaMapper.toEntity(dto);

        // VKİ zaten mapper'da hesaplanıyorsa gerek yok; ama burada da kalabilir:
        double vki = hasta.getKilo() / Math.pow(hasta.getBoy() / 100.0, 2);
        hasta.setVki(vki);

        Hasta saved = hastaService.createHasta(hasta);
        return ResponseEntity.ok(hastaMapper.toDTO(saved));
    }



    // ✅ Tüm hastaları getir (GET)
    @GetMapping
    public ResponseEntity<List<HastaDTO>> getAllHastalar() {
        List<Hasta> hastalar = hastaService.getAllHastalar();
        List<HastaDTO> dtoList = hastalar.stream()
                .map(hastaMapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtoList);
    }


    // ✅ TC’ye göre hasta bul (GET /{tcNo})
    @GetMapping("/{tcNo}")
    public ResponseEntity<HastaDTO> getHastaByTcNo(@PathVariable String tcNo) {
        Optional<Hasta> hastaOpt = hastaService.getHastaByTcNo(tcNo);
        return hastaOpt.map(hasta -> ResponseEntity.ok(hastaMapper.toDTO(hasta)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/id/{id}")
    public ResponseEntity<Map<String, Object>> getHastaById(@PathVariable Long id) {
        return hastaService.getHastaById(id)
                .map(hasta -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", hasta.getId());
                    map.put("ad", hasta.getAd());
                    map.put("soyad", hasta.getSoyad());
                    map.put("yas", hasta.getYas());
                    map.put("boy", hasta.getBoy());
                    map.put("kilo", hasta.getKilo());
                    map.put("vki", hasta.getVki());
                    map.put("cinsiyet", hasta.getCinsiyet());
                    map.put("saglikDurumu", hasta.getSaglikDurumu());
                    map.put("suTuketimi", hasta.getSuTuketimi());
                    map.put("fizikselAktivite", hasta.getFizikselAktivite());
                    return ResponseEntity.ok(map);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("/{tcNo}")
    public ResponseEntity<HastaDTO> updateHasta(@PathVariable String tcNo, @RequestBody HastaUpdateDTO dto) {
        Optional<Hasta> hastaOpt = hastaService.getHastaByTcNo(tcNo);
        if (hastaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Hasta hasta = hastaOpt.get();
        hastaMapper.updateHastaFromDto(dto, hasta);

        Hasta updated = hastaService.updateHasta(hasta);
        return ResponseEntity.ok(hastaMapper.toDTO(updated));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHasta(@PathVariable Long id) {
        hastaService.deleteHasta(id);
        return ResponseEntity.noContent().build();
    }
    // ✅ Giriş yapan hastanın kendi bilgilerini getir
//    @GetMapping("/me")
//    public ResponseEntity<HastaDTO> getCurrentHasta(Authentication auth) {
//        String tcNo = auth.getName();
//        if (tcNo.startsWith("HASTA-")) {
//            tcNo = tcNo.replace("HASTA-", "");
//        }
//
//        return hastaService.getHastaByTcNo(tcNo)
//                .map(hasta -> ResponseEntity.ok(hastaMapper.toDTO(hasta)))
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> getCurrentHasta(Authentication auth) {
        String tcNo = auth.getName();
        if (tcNo.startsWith("HASTA-")) {
            tcNo = tcNo.replace("HASTA-", "");
        }

        return hastaService.getHastaByTcNo(tcNo)
                .map(hasta -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", hasta.getId()); // ✅ ID EKLENDİ
                    map.put("ad", hasta.getAd());
                    map.put("soyad", hasta.getSoyad());
                    map.put("yas", hasta.getYas());
                    map.put("boy", hasta.getBoy());
                    map.put("kilo", hasta.getKilo());
                    map.put("vki", hasta.getVki());
                    map.put("cinsiyet", hasta.getCinsiyet());
                    map.put("saglikDurumu", hasta.getSaglikDurumu());
                    return ResponseEntity.ok(map);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



}