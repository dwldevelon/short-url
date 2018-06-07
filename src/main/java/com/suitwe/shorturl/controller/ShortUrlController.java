package com.suitwe.shorturl.controller;

import com.suitwe.shorturl.domain.Result;
import com.suitwe.shorturl.domain.UrlRequest;
import com.suitwe.shorturl.service.ShortUrlService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.InetAddress;
import java.util.Random;

/**
 * 短连接操作控制器
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/7
 */
@Controller
public class ShortUrlController {
    @Autowired
    private ShortUrlService shortUrlService;

    @ResponseBody
    @PostMapping("/shortUrl")
    public Result generateShortUrl(@Valid UrlRequest urlRequest) {
        if (!StringUtils.isEmpty(urlRequest.getTag())) {
            if (shortUrlService.generateShortUrl(urlRequest.getTag(), urlRequest.getUrl())) {
                return new Result(200, "生成成功！", urlRequest.getTag());
            } else {
                return new Result(0, "生成失败，该短链接已被使用", null);
            }
        } else {
            // 填充默认值
            if (urlRequest.getType() == null) {
                urlRequest.setType(-1);
            }
            if (urlRequest.getLength() == null) {
                urlRequest.setLength(4 + (new Random()).nextInt(12));
            }
            if (urlRequest.getGenerator() == null) {
                urlRequest.setGenerator(1);
            }

            // 生成短链接
            String tag = shortUrlService.generateShortUrl(urlRequest.getUrl(), urlRequest.getType(), urlRequest.getLength(), urlRequest.getGenerator());
            if (StringUtils.isEmpty(tag)) {
                return new Result(0, "生成失败，请重试", null);
            } else {
                return new Result(0, "生成成功", tag);
            }
        }
    }

    /**
     * 短链接访问操作，如果不存在记录则跳转到主页
     *
     * @param tag 短连接标识
     */
    @GetMapping("/{tag:[0-9a-zA-Z]{4,16}}")
    public ModelAndView setShortUrl(@PathVariable("tag") String tag, HttpServletRequest request) {
        String url = shortUrlService.getUrl(tag);
        if (url == null) {
            return new ModelAndView("forward:idx.html");
        } else {
            Logger.getLogger(this.getClass()).info("IP:[" + getIpAddr(request) + "] 短连接访问：短连接:" + tag + ",原始链接:" + url);
            return new ModelAndView("redirect:" + url);
        }
    }


    /**
     * 获取ip地址
     *
     * @param request 请求参数
     * @return ip地址
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if (ip.equals("127.0.0.1")) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ip != null && ip.length() > 15) {
            if (ip.indexOf(",") > 0) {
                ip = ip.substring(0, ip.indexOf(","));
            }
        }
        return ip;
    }

}
