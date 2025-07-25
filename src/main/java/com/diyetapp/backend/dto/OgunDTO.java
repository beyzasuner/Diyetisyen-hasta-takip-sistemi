package com.diyetapp.backend.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OgunDTO {
    private String ogunTuru;
    private String ogunAdi;
    private String ogunAdiIngilizce;
    private Double kaloriMiktari;
    private Double porsiyonMiktari;
    private LocalDate tarih;
}
