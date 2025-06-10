package com.diyetapp.backend.repository;

import com.diyetapp.backend.entity.Rapor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaporRepository extends JpaRepository<Rapor, Long> {
    List<Rapor> findByHastaId(Long hastaId);
}
