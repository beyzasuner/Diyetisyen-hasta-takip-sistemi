package com.diyetapp.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HastaCreateWithIdDTO {
    private String ad;
    private String soyad;
    private Integer yas;
    private Integer boy;
    private Double kilo;
    private String cinsiyet;
    private String tcNo;
    private String sifre;
    private Double suTuketimi;
    private String fizikselAktivite;
    private String saglikDurumu;
    private Long diyetisyenId; // artık doğrudan ID
}
