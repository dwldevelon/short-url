package com.suitwe.shorturl.service;

import java.util.Map;

/**
 * redis短网址操作接口
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/7
 */
public interface ShortUrlOpsService {
    /**
     * 存入短连接url信息
     * @param map url信息
     */
    void putUrls(Map<String, String> map);

    /**
     * 存入短连接访问计数
     * @param map 访问计数信息
     */
    void putCount(Map<String, String> map);

    /**
     * 得到一个短链接地址
     * @param tag 短连接
     * @return 原始地址
     */
    String getUrl(String tag);

    /**
     * 存入短连接地址
     * @param tag 短连接
     * @param url 原始地址
     */
    void setUrl(String tag, String url);

    /**
     * 判断短连接是否存在
     * @param tag 短连接
     * @return 结果
     */
    boolean exist(String tag);

    /**
     * 设置访问计数
     * @param tag 短连接
     * @param count 原始地址
     */
    void setCount(String tag,int count);

    /**
     * 短连接访问计数加一
     * @param tag 短连接
     * @return 当前访问计数
     */
    long increment(String tag);

    /**
     * 获取所有短连接访问个数
     * @return 访问计数信息
     */
    Map<Object, Object> getAllCount();

    /**
     * 清除计数
     */
    void clearCount();

    /**
     * 清除url信息
     */
    void clearUrl();
}
