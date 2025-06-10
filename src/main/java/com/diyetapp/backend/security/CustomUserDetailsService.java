package com.diyetapp.backend.security;

import com.diyetapp.backend.entity.Diyetisyen;
import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.repository.DiyetisyenRepository;
import com.diyetapp.backend.repository.HastaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private HastaRepository hastaRepository;

    @Autowired
    private DiyetisyenRepository diyetisyenRepository;

    @Override
    public UserDetails loadUserByUsername(String tcInput) throws UsernameNotFoundException {
        System.out.println(">>> loadUserByUsername çağrıldı: " + tcInput);

        if (!tcInput.contains("-")) {
            throw new UsernameNotFoundException("Giriş formatı hatalı.");
        }

        String[] parts = tcInput.split("-", 2);
        String rol = parts[0];
        String tcNo = parts[1];

        switch (rol) {
            case "HASTA":
                return hastaRepository.findByTcNo(tcNo)
                        .map(hasta -> User.builder()
                                .username(tcInput)
                                .password(hasta.getSifre())
                                .roles("HASTA")
                                .build())
                        .orElseThrow(() -> new UsernameNotFoundException("Hasta bulunamadı: " + tcNo));

            case "DIYETISYEN":
                return diyetisyenRepository.findByTcNo(tcNo)
                        .map(diyetisyen -> User.builder()
                                .username(tcInput)
                                .password(diyetisyen.getSifre())
                                .roles("DIYETISYEN")
                                .build())
                        .orElseThrow(() -> new UsernameNotFoundException("Diyetisyen bulunamadı: " + tcNo));

            default:
                throw new UsernameNotFoundException("Geçersiz rol");
        }
    }


}