package com.suitwe.shorturl.service;

/**
 * 短网址操作接口
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/7
 */
public interface ShortUrlService {

    /**
     * 根据短网址获取原始地址
     *
     * @param tag 短网址
     * @return 原始地址
     */
    String getUrl(String tag);

    /**
     * 用指定字符串生成短网址
     *
     * @param tag 指定字符串
     * @param url 原始地址
     * @return 结果
     */
    boolean generateShortUrl(String tag, String url);

    /**
     * 生成url的短网址并返回短网址结果
     *
     * @param url    原始地址
     * @param type   字符集 0:数字 1:小写字母 2:大写字母 3:大小写字母 其他:数字+大小写字母
     * @param length 输出长度4~16之间，超出范围会自动修正
     * @return 短网址结果
     */
    String generateShortUrl(String url, int type, int length);
}
