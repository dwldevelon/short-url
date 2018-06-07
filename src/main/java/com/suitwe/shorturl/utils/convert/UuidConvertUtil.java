package com.suitwe.shorturl.utils.convert;

import java.util.UUID;

/**
 * UUID生成短网址方式
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/7
 */
public class UuidConvertUtil implements ConvertUtil {
    @Override
    public String convert(String str) {
        String uuid = UUID.randomUUID().toString();
        return uuid.substring(0, 8) + uuid.substring(9, 13)
                + uuid.substring(14, 18) + uuid.substring(19, 23)
                + uuid.substring(24);
    }
}
