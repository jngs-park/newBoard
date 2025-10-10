package com.example.newBoard.repository;

import com.example.newBoard.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;

    public interface ArticleRepository extends JpaRepository<Article, Long> {
        @Query("select a from Article a left join fetch a.author")
        List<Article> findAllWithAuthor();

        @Query("select a from Article a left join fetch a.author where a.id = :id")
        Optional<Article> findByIdWithAuthor(@Param("id") Long id);
    }
