package com.diyetapp.backend.mapper;

import com.diyetapp.backend.dto.*;
import com.diyetapp.backend.entity.Diyetisyen;
import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.repository.DiyetisyenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HastaMapper {

    private final DiyetisyenRepository diyetisyenRepository;

    public HastaMapper(DiyetisyenRepository diyetisyenRepository) {
        this.diyetisyenRepository = diyetisyenRepository;
    }

    public HastaDTO toDTO(Hasta hasta) {
        return HastaDTO.builder()
                .ad(hasta.getAd())
                .soyad(hasta.getSoyad())
                .yas(hasta.getYas())
                .boy(hasta.getBoy())
                .kilo(hasta.getKilo())
                .cinsiyet(hasta.getCinsiyet())
                .saglikDurumu(hasta.getSaglikDurumu())
                .suTuketimi(hasta.getSuTuketimi())
                .fizikselAktivite(hasta.getFizikselAktivite())
                .vki(hasta.getVki())
                .build();
    }


    public Hasta toEntity(HastaCreateWithIdDTO dto) {
        double vki = dto.getKilo() / Math.pow(dto.getBoy() / 100.0, 2);

        Hasta hasta = Hasta.builder()
                .ad(dto.getAd())
                .soyad(dto.getSoyad())
                .yas(dto.getYas())
                .boy(dto.getBoy())
                .kilo(dto.getKilo())
                .cinsiyet(dto.getCinsiyet())
                .tcNo(dto.getTcNo())
                .sifre(dto.getSifre())
                .suTuketimi(dto.getSuTuketimi())
                .fizikselAktivite(dto.getFizikselAktivite())
                .saglikDurumu(dto.getSaglikDurumu())
                .vki(vki)
                .build();

        if (dto.getDiyetisyenId() != null) {
            Diyetisyen d = new Diyetisyen();
            d.setId(dto.getDiyetisyenId());
            hasta.setDiyetisyen(d);
        }

        return hasta;
    }



    public void updateHastaFromDto(HastaUpdateDTO dto, Hasta hasta) {
        hasta.setAd(dto.getAd());
        hasta.setSoyad(dto.getSoyad());
        hasta.setYas(dto.getYas());
        hasta.setBoy(dto.getBoy());
        hasta.setKilo(dto.getKilo());
        hasta.setCinsiyet(dto.getCinsiyet());
        hasta.setSaglikDurumu(dto.getSaglikDurumu());
        hasta.setSuTuketimi(dto.getSuTuketimi());
        hasta.setFizikselAktivite(dto.getFizikselAktivite());

        // VKİ yeniden hesapla:
        double vki = hasta.getKilo() / Math.pow(hasta.getBoy() / 100.0, 2);
        hasta.setVki(vki);
    }
}
