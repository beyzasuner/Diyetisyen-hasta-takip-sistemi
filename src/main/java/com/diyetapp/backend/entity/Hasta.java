package com.diyetapp.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // ✨ BU SATIRI EKLE!
@Entity
@Table(name = "hasta")
public class Hasta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diyetisyen_id") // veritabanındaki sütun adı
    private Diyetisyen diyetisyen;


    @Column(unique = true, length = 11)
    private String tcNo;

    @Column(length = 60, nullable = false)
    private String sifre;


    @Column(length = 50)
    private String ad;

    @Column(length = 50)
    private String soyad;

    private Integer yas;
    private Integer boy; // cm olarak örn. 171
    private Double kilo; // kg olarak örn. 68.2

    private String cinsiyet;

    @Column(columnDefinition = "TEXT")
    private String saglikDurumu;

    private Double suTuketimi; // litre cinsinden

    private String fizikselAktivite; // örn. "Yürüyüş 30dk"

    private Double vki;
}

