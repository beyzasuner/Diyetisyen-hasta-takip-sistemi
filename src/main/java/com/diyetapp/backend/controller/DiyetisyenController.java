package com.diyetapp.backend.controller;

import com.diyetapp.backend.dto.DiyetisyenCreateDTO;
import com.diyetapp.backend.dto.DiyetisyenDTO;
import com.diyetapp.backend.dto.HastaDTO;
import com.diyetapp.backend.entity.Diyetisyen;
import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.mapper.DiyetisyenMapper;
import com.diyetapp.backend.mapper.HastaMapper;
import com.diyetapp.backend.service.DiyetisyenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/diyetisyenler")
public class DiyetisyenController {

    private final DiyetisyenService diyetisyenService;
    private final DiyetisyenMapper diyetisyenMapper;
    private final HastaMapper hastaMapper;

    public DiyetisyenController(DiyetisyenService diyetisyenService, DiyetisyenMapper diyetisyenMapper, HastaMapper hastaMapper) {
        this.diyetisyenService = diyetisyenService;
        this.diyetisyenMapper = diyetisyenMapper;
        this.hastaMapper = hastaMapper;
    }

    @PostMapping
    public ResponseEntity<DiyetisyenDTO> create(@RequestBody DiyetisyenCreateDTO dto) {
        Diyetisyen entity = diyetisyenMapper.toEntity(dto);
        Diyetisyen saved = diyetisyenService.createDiyetisyen(entity);
        return ResponseEntity.ok(diyetisyenMapper.toDTO(saved));
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAll() {
        List<Diyetisyen> list = diyetisyenService.getAllDiyetisyenler();

        List<Map<String, Object>> dtoList = list.stream().map(d -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", d.getId());                    // ✅ ID eklendi
            map.put("ad", d.getAd());
            map.put("soyad", d.getSoyad());
            map.put("telefon", d.getTelefon());
            map.put("email", d.getEmail());
            return map;
        }).toList();

        return ResponseEntity.ok(dtoList);
    }


    @GetMapping("/{tcNo}")
    public ResponseEntity<DiyetisyenDTO> getByTcNo(@PathVariable String tcNo) {
        Optional<Diyetisyen> found = diyetisyenService.getByTcNo(tcNo);
        return found.map(diyetisyenMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ TOKEN'dan gelen kullanıcı bilgisiyle kendi profilini getir
    @GetMapping("/me")
    public ResponseEntity<DiyetisyenDTO> getProfile(Principal principal) {
        String fullTc = principal.getName();
        String tcNo = fullTc.contains("-") ? fullTc.split("-")[1] : fullTc;

        Optional<Diyetisyen> found = diyetisyenService.getByTcNo(tcNo);
        return found.map(diyetisyenMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ TOKEN'dan gelen kullanıcı bilgisiyle kendi hastalarını getir
    @GetMapping("/me/hastalar")
    public ResponseEntity<List<Map<String, Object>>> getHastalarim(Principal principal) {
        String fullTc = principal.getName();
        String tcNo = fullTc.contains("-") ? fullTc.split("-")[1] : fullTc;

        List<Hasta> hastalar = diyetisyenService.getHastalarim(tcNo);

        List<Map<String, Object>> dtoList = hastalar.stream().map(h -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", h.getId()); // ✅ burası önemli
            map.put("ad", h.getAd());
            map.put("soyad", h.getSoyad());
            map.put("boy", h.getBoy());
            map.put("kilo", h.getKilo());
            map.put("cinsiyet", h.getCinsiyet());
            map.put("yas", h.getYas());
            map.put("vki", h.getVki());
            map.put("saglikDurumu", h.getSaglikDurumu());
            map.put("suTuketimi", h.getSuTuketimi());
            map.put("fizikselAktivite", h.getFizikselAktivite());
            return map;
        }).toList();

        return ResponseEntity.ok(dtoList);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        diyetisyenService.deleteDiyetisyen(id);
        return ResponseEntity.noContent().build();
    }
}