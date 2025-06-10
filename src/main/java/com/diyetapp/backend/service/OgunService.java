package com.diyetapp.backend.service;

import com.diyetapp.backend.dto.OgunDTO;
import com.diyetapp.backend.dto.OgunEkleDTO;

import java.time.LocalDate;
import java.util.List;

public interface OgunService {

    OgunDTO createOgun(OgunEkleDTO dto);

    List<OgunDTO> getOgunListByHastaTc(String tcNo);

    List<OgunDTO> getOgunListByHastaTcAndDate(String tcNo, LocalDate tarih);
}