package com.diyetapp.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Rapor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private Hasta hasta;

    @ManyToOne
    @JoinColumn(name = "diyetisyen_id")
    private Diyetisyen diyetisyen;

    private String baslik;

    @Column(columnDefinition = "TEXT")
    private String icerik;

    private String raporTuru; // Haftalık, aylık, yıllık

    private LocalDateTime olusturulmaTarihi;
}
