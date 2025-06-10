package com.diyetapp.backend.repository;

import com.diyetapp.backend.entity.Ogun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OgunRepository extends JpaRepository<Ogun, Long> {
    List<Ogun> findByHastaId(Long hastaId);
}
