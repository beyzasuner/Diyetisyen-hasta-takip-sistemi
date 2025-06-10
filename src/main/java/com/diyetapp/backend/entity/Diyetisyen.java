package com.diyetapp.backend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder // ✨ BU SATIRI EKLE!
@Entity
public class Diyetisyen {

    @OneToMany(mappedBy = "diyetisyen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hasta> hastalar;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(unique = true, length = 11,nullable = false)
    private String tcNo;

    @Column(length = 60, nullable = false)
    private String sifre;

    @Column(length = 50,nullable=false)
    private String ad;

    @Column(length = 50, nullable = false)
    private String soyad;

    @Column(length = 20)
    private String telefon;

    @Column(length = 100)
    private String email;
}
