package com.diyetapp.backend.repository;

import com.diyetapp.backend.entity.Ogun;
import com.diyetapp.backend.entity.Hasta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OgunRepository extends JpaRepository<Ogun, Long> {

    // Belirli bir hastanın tüm öğünlerini getir
    List<Ogun> findByHasta(Hasta hasta);

    // Belirli bir hasta + belirli gün için
    List<Ogun> findByHastaAndTarih(Hasta hasta, LocalDate tarih);

    List<Ogun> findByHastaIdAndTarihBetween(Long hastaId, LocalDate startDate, LocalDate endDate);
    List<Ogun> findByHasta_IdAndTarih(Long hastaId, LocalDate tarih);
    @Query("SELECT h.id FROM Hasta h WHERE h.tcNo = :tcNo")
    Long findHastaIdByTcNo(@Param("tcNo") String tcNo);

}
