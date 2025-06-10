package com.diyetapp.backend.controller;

import com.diyetapp.backend.dto.AuthRequest;
import com.diyetapp.backend.dto.AuthResponse;
import com.diyetapp.backend.dto.HastaCreateWithIdDTO;
import com.diyetapp.backend.entity.Diyetisyen;
import com.diyetapp.backend.entity.Hasta;
import com.diyetapp.backend.mapper.HastaMapper;
import com.diyetapp.backend.repository.DiyetisyenRepository;
import com.diyetapp.backend.repository.HastaRepository;
import com.diyetapp.backend.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HastaRepository hastaRepository;

    @Autowired
    private DiyetisyenRepository diyetisyenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private HastaMapper hastaMapper;

    // ✅ GİRİŞ
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getTcNo(), request.getSifre())
            );

            User user = (User) authentication.getPrincipal();
            String role = user.getAuthorities().stream().findFirst().get().getAuthority();
            String token = jwtUtil.generateToken(user.getUsername(), role);

            System.out.println("Gelen TC (username): " + request.getTcNo());

            return ResponseEntity.ok(new AuthResponse(token));
        } catch (Exception e) {
            return ResponseEntity.status(403).body("Giriş başarısız: " + e.getMessage());
        }
    }

    // ✅ HASTA KAYIT (ID ile)
    @PostMapping("/register/hasta")
    public ResponseEntity<String> registerHasta(@RequestBody HastaCreateWithIdDTO dto) {
        if (hastaRepository.existsByTcNo(dto.getTcNo())) {
            return ResponseEntity.badRequest().body("Bu TC ile zaten bir hasta var.");
        }

        dto.setSifre(passwordEncoder.encode(dto.getSifre()));
        Hasta hasta = hastaMapper.toEntity(dto);
        hastaRepository.save(hasta);

        System.out.println("✅ Hasta kayıt edildi: " + dto.getTcNo() + " → Diyetisyen ID: " + dto.getDiyetisyenId());

        return ResponseEntity.ok("Hasta kaydı başarılı!");
    }

    // ✅ DİYETİSYEN KAYIT
    @PostMapping("/register/diyetisyen")
    public ResponseEntity<String> registerDiyetisyen(@RequestBody Diyetisyen diyetisyen) {
        if (diyetisyenRepository.existsByTcNo(diyetisyen.getTcNo())) {
            return ResponseEntity.badRequest().body("Bu TC ile zaten bir diyetisyen var.");
        }

        diyetisyen.setSifre(passwordEncoder.encode(diyetisyen.getSifre()));
        diyetisyenRepository.save(diyetisyen);

        return ResponseEntity.ok("Diyetisyen kaydı başarılı!");
    }
}
