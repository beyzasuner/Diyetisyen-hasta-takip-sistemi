package com.diyetapp.backend.entity;

import jakarta.persistence.*;

@Entity
public class Diyetisyen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 11)
    private String tcNo;

    @Column(length = 60, nullable = false)
    private String sifre;

    @Column(length = 50)
    private String ad;

    @Column(length = 50)
    private String soyad;

    @Column(length = 20)
    private String telefon;

    @Column(length = 100)
    private String email;
}
