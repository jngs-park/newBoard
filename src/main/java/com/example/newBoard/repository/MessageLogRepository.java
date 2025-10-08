package com.example.newBoard.repository;

import com.example.newBoard.entity.MessageLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageLogRepository extends JpaRepository<MessageLog, Long> {
}
