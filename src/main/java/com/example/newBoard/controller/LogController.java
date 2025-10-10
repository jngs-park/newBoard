package com.example.newBoard.controller;

import com.example.newBoard.entity.ArticleLog;
import com.example.newBoard.repository.ArticleLogRepository;
import com.example.newBoard.service.CacheService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Article API", description = "게시글 CRUD 및 Kafka 전송 관련 API")
@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final ArticleLogRepository articleLogRepository;
    private final CacheService cacheService; // ✅ 추가
    // ✅ 생성자에 CacheService 주입 추가
    public LogController(ArticleLogRepository articleLogRepository, CacheService cacheService) {
        this.articleLogRepository = articleLogRepository;
        this.cacheService = cacheService;
    }


    @GetMapping
    public List<ArticleLog> getAllLogs() {
        return articleLogRepository.findAll();
    }

    // ✅ 최신 로그 조회 (Redis 기반)
    @GetMapping("/latest")
    public String getLatestKafkaLog() {
        return cacheService.getLastArticleTitle();
    }
}