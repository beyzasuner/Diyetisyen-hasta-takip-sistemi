package com.diyetapp.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Ogun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id", nullable = false)
    private Hasta hasta;

    @Column(length = 20)
    private String ogunTuru; // Kahvaltı, Öğle, Akşam, Ara Öğün

    @Column(length = 100)
    private String ogunAdi; // Türkçe örn. “1 adet elma ve 1 bardak süt”

    private String ogunAdiIngilizce; // Translate edilmiş versiyon

    @Column(name = "yemek_detayi", columnDefinition = "TEXT")
    private String yemekDetayi;

    private Double kaloriMiktari;

    private Double porsiyonMiktari;

    private LocalDate tarih; // Öğün tarihi
    @Column(name = "karbonhidrat_miktari")
    private Double karbonhidratMiktari;

    @Column(name = "protein_miktari")
    private Double proteinMiktari;

    @Column(name = "yag_miktari")
    private Double yagMiktari;

}
