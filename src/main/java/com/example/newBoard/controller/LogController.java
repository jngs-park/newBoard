package com.example.newBoard.controller;

import com.example.newBoard.entity.ArticleLog;
import com.example.newBoard.repository.ArticleLogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final ArticleLogRepository articleLogRepository;

    public LogController(ArticleLogRepository articleLogRepository) {
        this.articleLogRepository = articleLogRepository;
    }

    @GetMapping
    public List<ArticleLog> getAllLogs() {
        return articleLogRepository.findAll();
    }
}
