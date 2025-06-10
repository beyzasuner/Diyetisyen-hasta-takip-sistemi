package com.diyetapp.backend.repository;

import com.diyetapp.backend.entity.Besin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BesinRepository extends JpaRepository<Besin, Long> {
}
