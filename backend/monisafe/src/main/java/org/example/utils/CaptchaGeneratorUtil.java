package org.example.utils;

import java.util.Random;

// 验证码生成工具类
public class CaptchaGeneratorUtil {

    // 方法生成纯数字的验证码
    public static String generateDigitCaptcha(int length) {
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            captcha.append(random.nextInt(10)); // 生成0-9之间的数字
        }
        return captcha.toString();
    }

    // 方法生成数字和字母混合的验证码
    public static String generateAlphaNumericCaptcha(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            captcha.append(chars.charAt(random.nextInt(chars.length())));
        }
        return captcha.toString();
    }

    // 主方法用于测试
//    public static void main(String[] args) {
//        System.out.println("Digit Captcha: " + generateDigitCaptcha(6));
//        System.out.println("AlphaNumeric Captcha: " + generateAlphaNumericCaptcha(6));
//    }
}
