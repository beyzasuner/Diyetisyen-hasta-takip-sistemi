package com.diyetapp.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HastaCreateDTO {

    private String ad;
    private String soyad;
    private Integer yas;
    private Integer boy;               // cm
    private Double kilo;               // kg
    private String cinsiyet;
    private String tcNo;
    private String sifre;
    private Double suTuketimi;
    private String fizikselAktivite;
    private String saglikDurumu;      // hasta kendi girecek → şeker, tansiyon vb.

    private String diyetisyenTcNo;  //  ilişkili diyetisyenin tcNo'su

}
