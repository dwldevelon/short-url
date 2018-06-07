package com.suitwe.shorturl.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

/**
 * 短连接生成请求参数类
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/7
 */
public class UrlRequest {
    /**
     * 原始链接
     */
    @Size(min = 4, message = "原始链接格式错误")
    private String url;
    /**
     * 指定字符串
     */
    private String tag;
    /**
     * 字符集 0:数字 1:小写字母 2:大写字母 3:大小写字母 其他:数字+大小写字母
     */
    private Integer type;
    /**
     * 输出长度4~16之间，超出范围会自动修正
     */
    @Min(value = 4, message = "输出长度4~16之间,不填则随机")
    @Max(value = 16, message = "输出长度4~16之间,不填则随机")
    private Integer length;
    /**
     * 生成方式：0:MD5方式，1:UUID方式
     */
    @Min(value = 0, message = "配置参数为:0:md5方式，1:uuid方式")
    @Max(value = 0, message = "配置参数为:0:md5方式，1:uuid方式")
    private Integer generator;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getGenerator() {
        return generator;
    }

    public void setGenerator(Integer generator) {
        this.generator = generator;
    }
}
