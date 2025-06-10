package com.diyetapp.backend.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ChatLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hasta_id")
    private Hasta hasta;

    @ManyToOne(optional = true)
    @JoinColumn(name = "diyetisyen_id")
    private Diyetisyen diyetisyen; // Eğer sadece hasta-chatbot konuşmasıysa null olabilir

    private String gonderen; // "hasta", "diyetisyen", "chatbot"

    @Column(columnDefinition = "TEXT")
    private String mesaj;

    private LocalDateTime zaman;
}
