package com.suitwe.shorturl.utils.convert;

/**
 * 短地址转换工具
 *
 * @author cheivin
 * @version 1.0
 * @date 2018/6/7
 */
public interface ConvertUtil {
    /**
     * 可用字符集
     */
    char[] CHARS = new char[]{
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
            , 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    /**
     * 将字符串信息转换为32位字符串
     *
     * @param str 原始字符串
     * @return 转换后
     */
    String convert(String str);

    /**
     * 字符串缩短
     *
     * @param str    原始字符串
     * @param type   字符集 0:数字 1:小写字母 2:大写字母 3:大小写字母 其他:数字+大小写字母
     * @param length 输出长度4~16之间，超出范围会自动修正
     * @return 缩短后字符串
     */
    String[] shortString(String str, int type, int length);
}
