package com.diyetapp.backend.repository;

import com.diyetapp.backend.entity.Hasta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HastaRepository extends JpaRepository<Hasta, Long> {
    Optional<Hasta> findByTcNo(String tcNo);
}
