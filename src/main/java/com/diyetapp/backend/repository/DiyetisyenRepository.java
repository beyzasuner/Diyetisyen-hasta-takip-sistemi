package com.diyetapp.backend.repository;

import com.diyetapp.backend.entity.Diyetisyen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiyetisyenRepository extends JpaRepository<Diyetisyen, Long> {
    Optional<Diyetisyen> findByTcNo(String tcNo);
    boolean existsByTcNo(String tcNo);
}
