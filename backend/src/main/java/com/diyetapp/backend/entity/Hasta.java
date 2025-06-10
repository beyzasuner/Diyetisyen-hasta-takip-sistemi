package com.diyetapp.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "hasta")
@Data
@NoArgsConstructor


public class Hasta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, length = 11)
    private String tcNo;

    @Column(length = 60, nullable = false)
    private String sifre;
    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }


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

