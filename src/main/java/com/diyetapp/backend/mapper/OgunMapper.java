package com.diyetapp.backend.mapper;

import com.diyetapp.backend.dto.OgunCreateDTO;
import com.diyetapp.backend.dto.OgunDTO;
import com.diyetapp.backend.dto.OgunEkleDTO;
import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.entity.Ogun;
import com.diyetapp.backend.repository.HastaRepository;
import org.springframework.stereotype.Component;

@Component
public class OgunMapper {

    private final HastaRepository hastaRepository;

    public OgunMapper(HastaRepository hastaRepository) {
        this.hastaRepository = hastaRepository;
    }

    // DTO → Entity
    public Ogun toEntity(OgunCreateDTO dto) {
        Hasta hasta = hastaRepository.findByTcNo(dto.getTcNo()).orElseThrow(() ->
                new IllegalArgumentException("Hasta bulunamadı: " + dto.getTcNo()));

        return Ogun.builder()
                .hasta(hasta)
                .ogunTuru(dto.getOgunTuru())
                .ogunAdi(dto.getOgunAdi())
                .ogunAdiIngilizce(dto.getOgunAdiIngilizce())
                .kaloriMiktari(dto.getKaloriMiktari())
                .porsiyonMiktari(dto.getPorsiyonMiktari())
                .tarih(dto.getTarih())
                .build();
    }

    // Entity → DTO
    public OgunDTO toDTO(Ogun ogun) {
        return OgunDTO.builder()
                .ogunTuru(ogun.getOgunTuru())
                .ogunAdi(ogun.getOgunAdi())
                .ogunAdiIngilizce(ogun.getOgunAdiIngilizce())
                .kaloriMiktari(ogun.getKaloriMiktari())
                .porsiyonMiktari(ogun.getPorsiyonMiktari())
                .tarih(ogun.getTarih())
                .build();
    }
    public Ogun dtoToOgun(OgunEkleDTO dto, Hasta hasta) {
        return Ogun.builder()
                .ogunTuru(dto.getOgunTuru())
                .ogunAdi(dto.getOgunAdi())
                .ogunAdiIngilizce(dto.getOgunAdiIngilizce())
                .yemekDetayi(dto.getYemekDetayi())              // ✅ Yeni alan eklendi
                .kaloriMiktari(dto.getKaloriMiktari())
                .porsiyonMiktari(dto.getPorsiyonMiktari())
                .tarih(dto.getTarih())
                .hasta(hasta)
                .build();
    }
}
