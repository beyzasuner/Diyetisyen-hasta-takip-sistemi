package com.diyetapp.backend.service;

import com.diyetapp.backend.dto.OgunCreateDTO;
import com.diyetapp.backend.dto.OgunDTO;
import com.diyetapp.backend.dto.OgunEkleDTO;
import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.entity.Ogun;
import com.diyetapp.backend.mapper.OgunMapper;
import com.diyetapp.backend.repository.HastaRepository;
import com.diyetapp.backend.repository.OgunRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
public class OgunServiceImpl implements OgunService {

    private final OgunRepository ogunRepository;
    private final HastaRepository hastaRepository;
    private final OgunMapper ogunMapper;

    public OgunServiceImpl(OgunRepository ogunRepository,
                           HastaRepository hastaRepository,
                           OgunMapper ogunMapper) {
        this.ogunRepository = ogunRepository;
        this.hastaRepository = hastaRepository;
        this.ogunMapper = ogunMapper;
    }

    @Override
    public OgunDTO createOgun(OgunEkleDTO dto) {
        Hasta hasta = hastaRepository.findByTcNo(dto.getTcNo())
                .orElseThrow(() -> new IllegalArgumentException("Hasta bulunamadı: " + dto.getTcNo()));

        Ogun ogun = ogunMapper.dtoToOgun(dto, hasta);
        Ogun saved = ogunRepository.save(ogun);
        return ogunMapper.toDTO(saved);
    }


    @Override
    public List<OgunDTO> getOgunListByHastaTc(String tcNo) {
        return hastaRepository.findByTcNo(tcNo)
                .map(ogunRepository::findByHasta)
                .orElse(Collections.emptyList())
                .stream()
                .map(ogunMapper::toDTO)
                .toList();
    }

    @Override
    public List<OgunDTO> getOgunListByHastaTcAndDate(String tcNo, LocalDate tarih) {
        return hastaRepository.findByTcNo(tcNo)
                .map(hasta -> ogunRepository.findByHastaAndTarih(hasta, tarih))
                .orElse(Collections.emptyList())
                .stream()
                .map(ogunMapper::toDTO)
                .toList();
    }
}