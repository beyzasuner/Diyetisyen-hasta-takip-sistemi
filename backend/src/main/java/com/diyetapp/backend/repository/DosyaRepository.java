package com.diyetapp.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DosyaRepository extends JpaRepository<Dosya, Long> {
    List<Dosya> findByHastaId(Long hastaId);
}
