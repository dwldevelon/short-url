package com.suitwe.shorturl.service.impl;

import com.suitwe.shorturl.dao.ShortUrlDao;
import com.suitwe.shorturl.entity.ShortUrl;
import com.suitwe.shorturl.service.ShortUrlOpsService;
import com.suitwe.shorturl.service.ShortUrlService;
import com.suitwe.shorturl.utils.convert.ConvertUtil;
import com.suitwe.shorturl.utils.convert.Md5ConvertUtil;
import com.suitwe.shorturl.utils.convert.UuidConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * 短网址操作实现
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/7
 */
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    private final ShortUrlDao shortUrlDao;
    private final ShortUrlOpsService shortUrlOpsService;

    @Autowired
    public ShortUrlServiceImpl(ShortUrlDao shortUrlDao, ShortUrlOpsService shortUrlOpsService) {
        this.shortUrlDao = shortUrlDao;
        this.shortUrlOpsService = shortUrlOpsService;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean generateShortUrl(String tag, String url) {
        // 判断是否存在该key
        if (shortUrlOpsService.exist(tag)) {
            return false;
        }
        ShortUrl shortUrl = new ShortUrl(tag, url, 0, new Date());
        // 存数据库
        shortUrlDao.save(shortUrl);
        // 放入缓存
        shortUrlOpsService.setUrl(tag, url);
        return true;
    }

    @Override
    public String generateShortUrl(String url, int type, int length, int generator) {
        ConvertUtil convertUtil;
        switch (generator) {
            case 0:
                convertUtil = Md5ConvertUtil.getInstance();
                break;
            case 1:
                convertUtil = UuidConvertUtil.getInstance();
                break;
            default:
                convertUtil = Md5ConvertUtil.getInstance();
        }

        String[] tags = convertUtil.shortString(url, type, length);
        for (String tag : tags) {
            if (generateShortUrl(tag, url)) {
                return tag;
            }
        }
        return null;
    }

    @Override
    public String getUrl(String tag) {
        if (shortUrlOpsService.exist(tag)) {
            shortUrlOpsService.increment(tag);
            return shortUrlOpsService.getUrl(tag);
        } else {
            ShortUrl shortUrl = shortUrlDao.findByTag(tag);
            if (shortUrl == null) {
                return null;
            } else {
                shortUrlOpsService.setUrl(tag, shortUrl.getUrl());
                shortUrlOpsService.setCount(tag, shortUrl.getCount() + 1);
                return shortUrl.getUrl();
            }
        }
    }
}
