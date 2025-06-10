package com.diyetapp.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiyetisyenCreateDTO {  //kayıt için

    private String tcNo;
    private String sifre;
    private String ad;
    private String soyad;
    private String telefon;
    private String email;
}
