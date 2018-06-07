package com.suitwe.shorturl.dao;

import com.suitwe.shorturl.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 短连接dao层操作
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/7
 */
public interface ShortUrlDao extends JpaRepository<ShortUrl, Integer> {
    /**
     * 根据短连接获取数据库信息
     *
     * @param tag 短连接
     * @return 数据记录
     */
    ShortUrl findByTag(String tag);
}
