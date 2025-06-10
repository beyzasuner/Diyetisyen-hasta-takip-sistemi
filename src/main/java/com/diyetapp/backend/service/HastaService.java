package com.diyetapp.backend.service;

import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.repository.HastaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HastaService {

    private final HastaRepository hastaRepository;

    public HastaService(HastaRepository hastaRepository) {
        this.hastaRepository = hastaRepository;
    }

    public List<Hasta> getAllHastalar() {
        return hastaRepository.findAll();
    }

    public Optional<Hasta> getHastaByTcNo(String tcNo) {
        return hastaRepository.findByTcNo(tcNo);
    }
    public Optional<Hasta> getHastaById(Long id) {
        return hastaRepository.findById(id);
    }




    public Hasta createHasta(Hasta hasta) {
        return hastaRepository.save(hasta);
    }
    public Hasta updateHasta(Hasta hasta) {
        return hastaRepository.save(hasta);
    }


    public void deleteHasta(Long id) {
        hastaRepository.deleteById(id);
    }
}