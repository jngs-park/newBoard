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

    // ✅ 전체 게시글 조회
    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    // ✅ 단일 게시글 조회
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id);
    }

    // ✅ JWT 인증 기반 게시글 생성
    public Article createArticle(String token, String title, String content) {
        // "Bearer " 제거
        String jwt = token.substring(7);
        String username = jwtUtil.extractUsername(jwt);

        User author = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 사용자입니다."));

        Article article = new Article(title, content, author);
        Article saved = articleRepository.save(article);

        // ✅ Redis 캐시에 마지막 게시글 제목 저장
        cacheService.saveLastArticleTitle(title);

        return saved;
    }

    // ✅ 인증 없이 게시글 생성 (테스트용)
    public Article createArticleWithoutAuth(String title, String content) {
        Article article = new Article(title, content, null);
        Article saved = articleRepository.save(article);

        // ✅ Redis 캐시에 마지막 게시글 제목 저장
        cacheService.saveLastArticleTitle(title);

        return saved;
    }

    // ✅ 게시글 수정
    public Article updateArticle(Long id, String title, String content) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        article.setTitle(title);
        article.setContent(content);
        return articleRepository.save(article);
    }

    // ✅ 게시글 삭제
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
