package com.diyetapp.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HastaDTO {  // gösterilecek bilgiler
    private String ad;
    private String soyad;
    private Integer yas;
    private Integer boy;               // cm
    private Double kilo;               // kg
    private String cinsiyet;
    private String saglikDurumu;
    private Double suTuketimi;
    private String fizikselAktivite;
    private Double vki;
}
