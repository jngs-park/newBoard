package com.example.newBoard.repository;

import com.example.newBoard.entity.ArticleLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleLogRepository extends JpaRepository<ArticleLog, Long> {
}
