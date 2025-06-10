package com.diyetapp.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Dosya {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private Hasta hasta;

    private String dosyaAdi;

    private String dosyaYolu; // örn: uploads/2025/rapor1.pdf

    private LocalDateTime yuklemeTarihi;
}
