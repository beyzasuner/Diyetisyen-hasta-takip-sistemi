package com.diyetapp.backend.dto;

import com.diyetapp.backend.entity.Ogun;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GunlukRaporDTO {
    private LocalDate tarih;
    private String sabah;
    private String ogle;
    private String aksam;
    private String diger;
    private String su;
    private double toplamKalori;

    public GunlukRaporDTO(LocalDate tarih) {
        this.tarih = tarih;
        this.sabah = "";
        this.ogle = "";
        this.aksam = "";
        this.diger = "";
        this.su = "";
        this.toplamKalori = 0;
    }

    public void addOgun(Ogun ogun) {
        switch (ogun.getOgunTuru()) {
            case "SABAH" -> sabah = ogun.getYemekDetayi();
            case "ÖĞLE" -> ogle = ogun.getYemekDetayi();
            case "AKŞAM" -> aksam = ogun.getYemekDetayi();
            case "DİĞER" -> diger = ogun.getYemekDetayi();
            case "SU" -> su = ogun.getYemekDetayi();
        }
        this.toplamKalori += ogun.getKaloriMiktari();
    }
}
