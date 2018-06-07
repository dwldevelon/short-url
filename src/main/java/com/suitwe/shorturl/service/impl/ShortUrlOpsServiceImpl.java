package com.suitwe.shorturl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * TODO
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/1
 */
@Service
public class ShortUrlOpsServiceImpl {
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public ShortUrlOpsServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void putUrls(Map<String, String> map) {
        redisTemplate.opsForHash().putAll("shortUrl", map);
    }

    public void putCount(Map<String, String> map) {
        redisTemplate.opsForHash().putAll("shortUrlCount", map);
    }

    public String getUrl(String tag) {
        return (String) redisTemplate.opsForHash().get("shortUrl", tag);
    }

    public void setUrl(String tag, String url) {
        redisTemplate.opsForHash().put("shortUrl", tag, url);
    }

    public boolean exist(String tag) {
        return redisTemplate.opsForHash().hasKey("shortUrl", tag);
    }

    public long increment(String tag) {
        return redisTemplate.opsForHash().increment("shortUrlCount", tag, 1);
    }

    public Map<Object, Object> getAllCount() {
        return redisTemplate.opsForHash().entries("shortUrlCount");
    }

    public void clearCount() {
        redisTemplate.delete("shortUrlCount");
    }

    public void clearUrl() {
        redisTemplate.delete("shortUrl");
    }
}
