package com.diyetapp.backend.controller;

import com.diyetapp.backend.dto.GunlukRaporDTO;
import com.diyetapp.backend.entity.Ogun;
import com.diyetapp.backend.repository.OgunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("/api/rapor")
@CrossOrigin(origins = "*") // frontend'den erişim için
public class RaporController {

    @Autowired
    private OgunRepository ogunRepository;

    @GetMapping("/hasta/{id}/haftalik")
    public ResponseEntity<List<GunlukRaporDTO>> getHaftalikRapor(@PathVariable Long id) {
        LocalDate bugun = LocalDate.now();
        LocalDate yediGunOnce = bugun.minusDays(6);

        List<Ogun> ogunler = ogunRepository.findByHastaIdAndTarihBetween(id, yediGunOnce, bugun);
        Map<LocalDate, GunlukRaporDTO> raporMap = new TreeMap<>();

        for (Ogun ogun : ogunler) {
            LocalDate tarih = ogun.getTarih();
            GunlukRaporDTO gun = raporMap.getOrDefault(tarih, new GunlukRaporDTO(tarih));
            gun.addOgun(ogun);
            raporMap.put(tarih, gun);
        }

        return ResponseEntity.ok(new ArrayList<>(raporMap.values()));
    }
    @GetMapping("/me/haftalik")
    public ResponseEntity<List<GunlukRaporDTO>> getHaftalikRaporForHasta(Authentication auth) {
        String tcNo = auth.getName().replace("HASTA-", "");

        Long hastaId = ogunRepository.findHastaIdByTcNo(tcNo); // 🔥 Bu methodu repository'de yazacağız

        LocalDate bugun = LocalDate.now();
        LocalDate yediGunOnce = bugun.minusDays(6);

        List<Ogun> ogunler = ogunRepository.findByHastaIdAndTarihBetween(hastaId, yediGunOnce, bugun);
        Map<LocalDate, GunlukRaporDTO> raporMap = new TreeMap<>();

        for (Ogun ogun : ogunler) {
            LocalDate tarih = ogun.getTarih();
            GunlukRaporDTO gun = raporMap.getOrDefault(tarih, new GunlukRaporDTO(tarih));
            gun.addOgun(ogun);
            raporMap.put(tarih, gun);
        }

        return ResponseEntity.ok(new ArrayList<>(raporMap.values()));
    }

}


