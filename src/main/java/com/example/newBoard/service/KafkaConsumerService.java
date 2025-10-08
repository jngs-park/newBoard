package com.example.newBoard.service;

import com.example.newBoard.entity.MessageLog;
import com.example.newBoard.repository.MessageLogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    private final MessageLogRepository messageLogRepository;

    public KafkaConsumerService(MessageLogRepository messageLogRepository) {
        this.messageLogRepository = messageLogRepository;
    }

    @KafkaListener(topics = "article-topic", groupId = "newboard-group")
    public void consume(String message) {
        System.out.println("✅ Consumed message: " + message);

        MessageLog log = new MessageLog("article-topic", message);
        messageLogRepository.save(log);

        System.out.println("💾 Saved message log to DB: " + message);
    }
}
