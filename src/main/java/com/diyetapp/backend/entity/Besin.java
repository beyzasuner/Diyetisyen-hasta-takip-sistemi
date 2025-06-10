package com.diyetapp.backend.entity;

import jakarta.persistence.*;

@Entity
public class Besin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String besinAdi; // İngilizce adı

    @Column(length = 100)
    private String besinAdiTurkce; // Türkçe çeviri

    private String porsiyonOlcusu;
    private String porsiyonOlcusuTurkce;

    private Double kalori;
    private Double protein;
    private Double karbonhidrat;
    private Double yag;
}
