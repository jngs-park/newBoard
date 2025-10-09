package com.example.newBoard.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "article_logs")
public class ArticleLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;
    private String message;
    private LocalDateTime receivedAt;

    public ArticleLog() {}

    public ArticleLog(String topic, String message, LocalDateTime receivedAt) {
        this.topic = topic;
        this.message = message;
        this.receivedAt = receivedAt;
    }

    public Long getId() {
        return id;
    }

    public String getTopic() {
        return topic;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }
}
