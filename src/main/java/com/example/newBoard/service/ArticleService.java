package com.example.newBoard.service;

import com.example.newBoard.entity.Article;
import com.example.newBoard.entity.User;
import com.example.newBoard.repository.ArticleRepository;
import com.example.newBoard.repository.UserRepository;
import com.example.newBoard.util.JwtUtil;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CacheService cacheService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ArticleService(
            ArticleRepository articleRepository,
            UserRepository userRepository,
            JwtUtil jwtUtil,
            CacheService cacheService,
            KafkaTemplate<String, String> kafkaTemplate
    ) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.cacheService = cacheService;
        this.kafkaTemplate = kafkaTemplate;
    }

    // ✅ 인증 없이 게시글 생성 (테스트용)
    public Article createArticleWithoutAuth(String title, String content) {
        Article article = new Article(title, content, null);
        Article saved = articleRepository.save(article);

        // ✅ Redis 캐시에 저장
        cacheService.saveLastArticleTitle(title);

        // ✅ Kafka 토픽으로 메시지 전송
        String message = "게시글 등록: " + title + " | 내용: " + content;
        kafkaTemplate.send("article-topic", message);
        System.out.println("🚀 Kafka 전송 완료: " + message);

        return saved;
    }
}
