package com.diyetapp.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DiyetisyenDTO { //(Kullanıcıya gösterilecek veri

    private String ad;
    private String soyad;
    private String telefon;
    private String email;
}
