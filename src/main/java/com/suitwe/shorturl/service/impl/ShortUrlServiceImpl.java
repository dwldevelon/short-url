package com.suitwe.shorturl.service.impl;

import com.suitwe.shorturl.dao.ShortUrlDao;
import com.suitwe.shorturl.entity.ShortUrl;
import com.suitwe.shorturl.service.ShortUrlOpsService;
import com.suitwe.shorturl.service.ShortUrlService;
import com.suitwe.shorturl.utils.convert.ConvertUtil;
import com.suitwe.shorturl.utils.convert.Md5ConvertUtil;
import com.suitwe.shorturl.utils.convert.UuidConvertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    /**
     * 每小时存储一次
     */
    @Scheduled(cron = "0 0 0/1 * * ? ")
    public void saveCount2Db() {
        // 获取缓存访问计数
        Map<Object, Object> map = shortUrlOpsService.getAllCount();
        LinkedList<String> tagList = new LinkedList<>();
        map.forEach((k, v) -> tagList.add(k.toString()));

        // 查询对象列表
        List<ShortUrl> shortUrlList = shortUrlDao.findByTag(tagList);

        // 更新访问计数
        shortUrlList.forEach(url -> url.setCount(Integer.valueOf((String) map.get(url.getTag()))));

        // 分批存入数据库，5000条一次
        int times = (int) Math.ceil(shortUrlList.size() / 5000.0);
        for (int i = 0; i < times; i++) {
            List<ShortUrl> urlList = shortUrlList
                    .subList(i * 5000, (i + 1) * 5000 < shortUrlList.size() ? (i + 1) * 5000 : shortUrlList.size());
            shortUrlDao.save(urlList);
        }
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
