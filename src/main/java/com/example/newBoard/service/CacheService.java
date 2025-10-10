package com.example.newBoard.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    private final RedisTemplate<String, Object> redisTemplate;

    public CacheService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveLastArticleTitle(String title) {
        redisTemplate.opsForValue().set("lastArticle", title);
    }

    public String getLastArticleTitle() {
        Object value = redisTemplate.opsForValue().get("lastArticle");
        return value != null ? value.toString() : "캐시 없음";
    }
}