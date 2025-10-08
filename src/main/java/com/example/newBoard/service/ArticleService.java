package com.example.newBoard.service;

import com.example.newBoard.entity.Article;
import com.example.newBoard.entity.User;
import com.example.newBoard.repository.ArticleRepository;
import com.example.newBoard.repository.UserRepository;
import com.example.newBoard.util.JwtUtil;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final CacheService cacheService;

    public ArticleService(ArticleRepository articleRepository,
                          UserRepository userRepository,
                          JwtUtil jwtUtil,
                          CacheService cacheService) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.cacheService = cacheService;
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(String token, String title, String content) {
        // "Bearer " 제거
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);

        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 사용자입니다."));
        Article article = new Article(title, content, author);
        Article saved = articleRepository.save(article);

        // Redis 캐시에 저장
        cacheService.saveLastArticleTitle(title);

        return saved;
    }

    public Article updateArticle(Long id, String title, String content) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        article.setTitle(title);
        article.setContent(content);
        return articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
