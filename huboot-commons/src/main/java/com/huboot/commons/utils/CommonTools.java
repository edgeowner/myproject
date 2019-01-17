package com.huboot.commons.utils;

import org.springframework.util.StringUtils;

import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 *
 * @author Yung·Fu (FUYONG1208 AT GMAIL DOT com)
 */
public class CommonTools {

    /**
     * <b>根据参数生成随机数,仅含数字<b><br/>
     *
     * @param digit 位数
     * @return String
     */
    public static String getRandomNumber(int digit) {
        if (digit > 0) {
            StringBuffer sb = new StringBuffer();
            String str = "0123456789";
            Random r = new Random();
            for (int i = 0; i < digit; i++) {
                int num = r.nextInt(str.length());
                sb.append(str.charAt(num));
                str = str.replace((str.charAt(num) + ""), "");
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * <b>根据参数生成随机数，数字+字母<b><br/>
     *
     * @param digit 位数
     * @return String
     */
    public static String getRandom(int digit) {
        if (digit > 0) {
            StringBuffer sb = new StringBuffer();
            String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            Random r = new Random();
            for (int i = 0; i < digit; i++) {
                int num = r.nextInt(str.length());
                sb.append(str.charAt(num));
                str = str.replace((str.charAt(num) + ""), "");
            }
            return sb.toString();
        } else {
            return "";
        }
    }

    /**
     * 生成一个UUID
     *
     * @return String 生成的UUID
     */
    public static String randomUUID() {
        String s = UUID.randomUUID().toString();
        s = s.replace("-", ""); // 去掉uuid中的“-”
        return s.toUpperCase();
    }

    /**
     * 首字母 如果为小写变大写,如果为小写变大写
     *
     * @param str
     * @return
     */
    public static String initialToUpperOrLower(String str) {
        if (str != null && !"".equals(str)) {
            char[] chars = new char[1];
            chars[0] = str.charAt(0);
            String temp = new String(chars);
            if (chars[0] >= 'A' && chars[0] <= 'Z') {
                return str.replaceFirst(temp, temp.toLowerCase());
            }
            return str.replaceFirst(temp, temp.toUpperCase());
        }
        return str;
    }

    public static boolean checkMobile(String mobile) {
//        Pattern pattern = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
        Pattern pattern = Pattern.compile("^[1][0-9]{10}$");
        Matcher isNum = pattern.matcher(mobile);
        return isNum.matches();
    }

    public static boolean checkName(String name) {
        Pattern pattern = Pattern.compile("^(([\u4E00-\u9FA5]{2,7})|([a-zA-Z]{3,10}))$");
        Matcher isName = pattern.matcher(name);
        return isName.matches();
    }

    public static String igem(String str) {
        return StringUtils.isEmpty(str) ? "" : str;
    }

}
