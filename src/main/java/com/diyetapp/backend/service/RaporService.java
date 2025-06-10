package com.diyetapp.backend.service;

import com.diyetapp.backend.dto.GunlukRaporDTO;
import com.diyetapp.backend.entity.Ogun;
import com.diyetapp.backend.repository.OgunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class RaporService {

    @Autowired
    private OgunRepository ogunRepository;

    public List<GunlukRaporDTO> getHaftalikRapor(Long hastaId) {
        LocalDate bugun = LocalDate.now();
        LocalDate yediGunOnce = bugun.minusDays(6); // Son 7 günü al

        // Hasta ID ve tarih aralığına göre öğünleri getir
        List<Ogun> ogunler = ogunRepository.findByHastaIdAndTarihBetween(hastaId, yediGunOnce, bugun);

        // Tarihe göre günlük raporları gruplayacağız
        Map<LocalDate, GunlukRaporDTO> raporMap = new TreeMap<>(); // tarih sırasına göre sıralı tutar

        for (Ogun ogun : ogunler) {
            LocalDate tarih = ogun.getTarih();

            // Eğer o gün için rapor DTO’su yoksa oluştur
            GunlukRaporDTO gunlukRapor = raporMap.getOrDefault(tarih, new GunlukRaporDTO(tarih));
            gunlukRapor.addOgun(ogun);

            raporMap.put(tarih, gunlukRapor);
        }

        return new ArrayList<>(raporMap.values());
    }
}
