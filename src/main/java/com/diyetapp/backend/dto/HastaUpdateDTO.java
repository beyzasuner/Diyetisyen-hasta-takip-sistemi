package com.diyetapp.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HastaUpdateDTO {
    private String ad;
    private String soyad;
    private Integer yas;
    private Integer boy;
    private Double kilo;
    private String cinsiyet;
    private String fizikselAktivite;
    private String saglikDurumu;
    private Double suTuketimi;
}
