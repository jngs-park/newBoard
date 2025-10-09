package com.example.newBoard.service;

import com.example.newBoard.entity.ArticleLog;
import com.example.newBoard.repository.ArticleLogRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class KafkaConsumerService {

    private final ArticleLogRepository articleLogRepository;
    private final CacheService cacheService;

    public KafkaConsumerService(ArticleLogRepository articleLogRepository, CacheService cacheService) {
        this.articleLogRepository = articleLogRepository;
        this.cacheService = cacheService;
    }

    @KafkaListener(
            topics = "article-topic",
            groupId = "newboard-group",
            properties = { "auto.offset.reset=earliest" }
    )
    public void consume(String message) {
        System.out.println("✅ [KafkaConsumerService] 메시지 수신 시도 중...");

        try {
            System.out.println("✅ Kafka 메시지 수신: " + message);
            ArticleLog log = new ArticleLog("article-topic", message, LocalDateTime.now());
            articleLogRepository.save(log);
            cacheService.saveLastArticleTitle(message);
            System.out.println("💾 DB 저장 완료: " + message);
        } catch (Exception e) {
            System.out.println("❌ Consumer 처리 중 예외 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
