package com.diyetapp.backend.repository;

import com.diyetapp.backend.entity.ChatLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatLogRepository extends JpaRepository<ChatLog, Long> {
    List<ChatLog> findByHastaId(Long hastaId);
}
