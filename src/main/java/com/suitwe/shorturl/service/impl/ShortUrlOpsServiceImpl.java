package com.suitwe.shorturl.service.impl;

import com.suitwe.shorturl.service.ShortUrlOpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * redis短网址操作
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/1
 */
@Service
public class ShortUrlOpsServiceImpl implements ShortUrlOpsService {
    private final StringRedisTemplate redisTemplate;

    @Autowired
    public ShortUrlOpsServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void putUrls(Map<String, String> map) {
        redisTemplate.opsForHash().putAll("shortUrl", map);
    }

    @Override
    public void putCount(Map<String, String> map) {
        redisTemplate.opsForHash().putAll("shortUrlCount", map);
    }

    @Override
    public String getUrl(String tag) {
        return (String) redisTemplate.opsForHash().get("shortUrl", tag);
    }

    @Override
    public void setUrl(String tag, String url) {
        redisTemplate.opsForHash().put("shortUrl", tag, url);
    }

    @Override
    public boolean exist(String tag) {
        return redisTemplate.opsForHash().hasKey("shortUrl", tag);
    }

    @Override
    public void setCount(String tag, int count) {
        redisTemplate.opsForHash().put("shortUrlCount", tag, String.valueOf(count));
    }

    @Override
    public long increment(String tag) {
        return redisTemplate.opsForHash().increment("shortUrlCount", tag, 1);
    }

    @Override
    public Map<Object, Object> getAllCount() {
        return redisTemplate.opsForHash().entries("shortUrlCount");
    }

    @Override
    public void clearCount() {
        redisTemplate.delete("shortUrlCount");
    }

    @Override
    public void clearUrl() {
        redisTemplate.delete("shortUrl");
    }
}
