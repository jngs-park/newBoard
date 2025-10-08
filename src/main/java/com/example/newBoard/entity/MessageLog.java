package com.example.newBoard.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message_logs")
public class MessageLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String message;

    private LocalDateTime receivedAt;

    public MessageLog() {}

    public MessageLog(String topic, String message) {
        this.topic = topic;
        this.message = message;
        this.receivedAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getTopic() { return topic; }
    public String getMessage() { return message; }
    public LocalDateTime getReceivedAt() { return receivedAt; }
}
