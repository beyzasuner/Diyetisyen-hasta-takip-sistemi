package com.diyetapp.backend.service;

import com.diyetapp.backend.entity.Diyetisyen;
import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.repository.DiyetisyenRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DiyetisyenService {

    private final DiyetisyenRepository diyetisyenRepository;

    public DiyetisyenService(DiyetisyenRepository diyetisyenRepository) {
        this.diyetisyenRepository = diyetisyenRepository;
    }

    public Diyetisyen createDiyetisyen(Diyetisyen diyetisyen) {
        return diyetisyenRepository.save(diyetisyen);
    }

    public List<Diyetisyen> getAllDiyetisyenler() {
        return diyetisyenRepository.findAll();
    }

    public Optional<Diyetisyen> getByTcNo(String tcNo) {
        return diyetisyenRepository.findByTcNo(tcNo);
    }
    public List<Hasta> getHastalarim(String tcNo) {
        Optional<Diyetisyen> diyOpt = diyetisyenRepository.findByTcNo(tcNo);
        return diyOpt.map(Diyetisyen::getHastalar)
                .orElse(Collections.emptyList());
    }


    public void deleteDiyetisyen(Long id) {
        diyetisyenRepository.deleteById(id);
    }
}