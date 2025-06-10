package com.diyetapp.backend.controller;

import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.service.HastaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/hastalar")
public class HastaController {

    private final HastaService hastaService;

    public HastaController(HastaService hastaService) {
        this.hastaService = hastaService;
    }

    // Tüm hastaları getir
    @GetMapping
    public ResponseEntity<List<Hasta>> getAllHastalar() {
        return ResponseEntity.ok(hastaService.getAllHastalar());
    }

    // TC'ye göre hasta bul
    @GetMapping("/{tcNo}")
    public ResponseEntity<Hasta> getHastaByTcNo(@PathVariable String tcNo) {
        Optional<Hasta> hasta = hastaService.getHastaByTcNo(tcNo);
        return hasta.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Yeni hasta oluştur
    @PostMapping
    public ResponseEntity<Hasta> createHasta(@RequestBody Hasta hasta) {
       Hasta saved = hastaService.createHasta(hasta);
       return ResponseEntity.ok(saved);
    }



    // Hasta sil
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHasta(@PathVariable Long id) {
        hastaService.deleteHasta(id);
        return ResponseEntity.noContent().build();
    }
}
