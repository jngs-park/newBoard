package com.example.newboard.service;

import com.example.newboard.entity.Article;
import com.example.newboard.repository.ArticleRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public Optional<Article> getArticle(Long id) {
        return articleRepository.findById(id);
    }

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public Article updateArticle(Long id, Article updated) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(updated.getTitle());
                    article.setContent(updated.getContent());
                    return articleRepository.save(article);
                })
                .orElseThrow(() -> new RuntimeException("Article not found"));
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
