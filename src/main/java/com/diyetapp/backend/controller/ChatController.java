package com.diyetapp.backend.controller;

import com.diyetapp.backend.service.VertexAIService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ChatController {

    @PostMapping("/chat")
    public Map<String, String> sohbet(@RequestBody Map<String, String> istek) {
        String soru = istek.get("soru");
        String yanit = VertexAIService.getChatResponse(soru);
        return Map.of("cevap", yanit);
    }
}
