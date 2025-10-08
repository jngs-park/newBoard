package com.example.newBoard.controller;

import com.example.newBoard.entity.Article;
import com.example.newBoard.service.ArticleService;
import org.springframework.web.bind.annotation.*;
import com.example.newBoard.service.CacheService;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;
    private final CacheService cacheService;

    public ArticleController(ArticleService articleService, CacheService cacheService) {
        this.articleService = articleService;
        this.cacheService = cacheService;
    }


    // ✅ 전체 조회
    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.findAll();
    }

    // ✅ 단건 조회
    @GetMapping("/{id}")
    public Optional<Article> getArticleById(@PathVariable Long id) {
        return articleService.findById(id);
    }

    // ✅ 게시글 작성 (JWT 인증 필요)
    @PostMapping
    public Article createArticle(
            @RequestHeader("Authorization") String token,
            @RequestBody Article article) {
        return articleService.createArticle(token, article.getTitle(), article.getContent());
    }

    // ✅ 게시글 수정
    @PutMapping("/{id}")
    public Article updateArticle(@PathVariable Long id, @RequestBody Article article) {
        return articleService.updateArticle(id, article.getTitle(), article.getContent());
    }

    // ✅ 게시글 삭제
    @DeleteMapping("/{id}")
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }

    @GetMapping("/cache")
    public Map<String, String> getLastCachedArticle() {
        String lastArticle = cacheService.getLastArticleTitle();
        Map<String, String> response = new HashMap<>();
        response.put("lastArticle", lastArticle);
        return response;
    }

}
