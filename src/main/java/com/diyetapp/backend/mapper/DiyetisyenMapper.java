package com.diyetapp.backend.mapper;

import com.diyetapp.backend.dto.DiyetisyenCreateDTO;
import com.diyetapp.backend.dto.DiyetisyenDTO;
import com.diyetapp.backend.entity.Diyetisyen;
import org.springframework.stereotype.Component;

@Component
public class DiyetisyenMapper {

    public Diyetisyen toEntity(DiyetisyenCreateDTO dto) {
        return Diyetisyen.builder()
                .tcNo(dto.getTcNo())
                .sifre(dto.getSifre())
                .ad(dto.getAd())
                .soyad(dto.getSoyad())
                .telefon(dto.getTelefon())
                .email(dto.getEmail())
                .build();
    }

    public DiyetisyenDTO toDTO(Diyetisyen entity) {
        return DiyetisyenDTO.builder()
                .ad(entity.getAd())
                .soyad(entity.getSoyad())
                .telefon(entity.getTelefon())
                .email(entity.getEmail())
                .build();
    }
}
