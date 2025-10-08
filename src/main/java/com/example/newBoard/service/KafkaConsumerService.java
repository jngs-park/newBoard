package com.example.newBoard.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "article-topic", groupId = "newboard-group")
    public void listen(String message) {
        System.out.println("📩 Kafka Consumer 수신: " + message);
    }
}
