package com.example.newBoard.controller;

import com.example.newBoard.entity.Article;
import com.example.newBoard.service.ArticleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Article API", description = "게시글 CRUD 및 Kafka 전송 관련 API")
@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // ✅ 전체 게시글 조회
    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.findAll();
    }

    // ✅ 단일 게시글 조회
    @GetMapping("/{id}")
    public Optional<Article> getArticleById(@PathVariable Long id) {
        return articleService.findById(id);
    }

    // ✅ 게시글 생성 (인증 없이 테스트용)
    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleService.createArticleWithoutAuth(article.getTitle(), article.getContent());
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

    // ✅ Redis 캐시에서 마지막 게시글 제목 조회
    @GetMapping("/cache")
    public String getLastArticleFromCache() {
        return articleService.getLastCachedTitle();
    }
}
