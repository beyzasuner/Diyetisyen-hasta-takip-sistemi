package com.diyetapp.backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OgunEkleDTO {

    private String ogunTuru;             // SABAH, ÖĞLE, AKŞAM, DİĞER, SU
    private String ogunAdi;              // Kahvaltı, Öğle Yemeği vs.
    private String ogunAdiIngilizce;     // Breakfast, Lunch vs.
    private String yemekDetayi;          // 1 yumurta, 1 bardak süt gibi
    private Double kaloriMiktari;        // Başlangıçta 0.0 olabilir
    private Double porsiyonMiktari;      // Sabit 1.0 olabilir
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate tarih;             // Günün tarihi
    private String tcNo;                 // JWT’den alınan hasta kimliği
}
