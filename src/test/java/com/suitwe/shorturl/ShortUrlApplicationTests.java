package com.suitwe.shorturl;

import com.suitwe.shorturl.utils.convert.ConvertUtil;
import com.suitwe.shorturl.utils.convert.Md5ConvertUtil;
import com.suitwe.shorturl.utils.convert.UuidConvertUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;
import java.util.regex.Pattern;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlApplicationTests {

    @Test
    public void contextLoads() {
    }

    private void checkTag(int length, int charset, String tag[]) {
        // 检查备选长度
        assert tag.length == 4 : "生成失败";
        // 检查短字符串长度
        assert tag[0].length() == length : "结果长度不符合";
        // 检查字符集
        switch (charset) {
            case 0:
                assert Pattern.matches("[0-9]*", tag[0]) : "字符集错误,应为纯数字";
                break;
            case 1:
                assert Pattern.matches("[a-z]*", tag[0]) : "字符集错误,应为纯小写字母";
                break;
            case 2:
                assert Pattern.matches("[A-Z]*", tag[0]) : "字符集错误,应为纯大写字母";
                break;
            case 3:
                assert Pattern.matches("[a-zA-Z]*", tag[0]) : "字符集错误,应为纯字母";
                break;
            default:
                assert Pattern.matches("[0-9a-zA-Z]*", tag[0]) : "字符集错误,应为字母和数字混合";
        }
    }


    @Test
    public void md5ConvertTest() {
        ConvertUtil convertUtil = Md5ConvertUtil.getInstance();
        Random r = new Random();
        int length = r.nextInt(13) + 4;
        int charset = r.nextInt(5);

        System.out.println(String.format("短字符串转换,字符集:%d,长度:%d", charset, length));

        String tag[] = convertUtil.shortString("Cheivin", charset, length);
        checkTag(length, charset, tag);

        for (String s : tag) {
            System.out.println("生成结果:" + s);
        }
    }

    @Test
    public void md5ConvertCheckTest() {
        ConvertUtil convertUtil = Md5ConvertUtil.getInstance();
        Random r = new Random();
        int length = r.nextInt(13) + 4;
        int charset = r.nextInt(5);

        System.out.println(String.format("短字符串转换,字符集:%d,长度:%d", charset, length));

        String tag1[] = convertUtil.shortString("Cheivin", charset, length);
        String tag2[] = convertUtil.shortString("Cheivin", charset, length);

        for (int i = 0; i < tag1.length; i++) {
            assert tag1[i].equals(tag2[i]) : "两次md5生成结果不一致";
        }
        System.out.println("两次生成结果一致");
    }

    @Test
    public void uuidConvertTest() {
        ConvertUtil convertUtil = UuidConvertUtil.getInstance();
        Random r = new Random();
        int length = r.nextInt(13) + 4;
        int charset = r.nextInt(5);

        System.out.println(String.format("短字符串转换,字符集:%d,长度:%d", charset, length));

        String tag[] = convertUtil.shortString("Cheivin", charset, length);
        checkTag(length, charset, tag);

        for (String s : tag) {
            System.out.println("生成结果:" + s);
        }
    }

    @Test
    public void uuidConvertCheckTest() {
        ConvertUtil convertUtil = UuidConvertUtil.getInstance();
        Random r = new Random();
        int length = r.nextInt(13) + 4;
        int charset = r.nextInt(5);

        System.out.println(String.format("短字符串转换,字符集:%d,长度:%d", charset, length));

        String tag1[] = convertUtil.shortString("Cheivin", charset, length);
        String tag2[] = convertUtil.shortString("Cheivin", charset, length);

        for (int i = 0; i < tag1.length; i++) {
            assert !tag1[i].equals(tag2[i]) : "两次uuid生成结果不应一致";
        }
        System.out.println("两次生成结果不同");
    }

}
