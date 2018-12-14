package com.huboot.business.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具类
 *
 * @author Yung·Fu (FUYONG1208 AT GMAIL DOT com)
 */
public class Tools {
    /**
     * 获取当前时间14位字符串+4位随机数 18位
     *
     * @return String e.g."201312142102582374"
     */
    public static String getCurrentTimeRandom() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sdf.format(new Date()) + getRandom(4);
    }

    /**
     * <b>当前时间搓加随机数 18位<b><br/>
     *
     * @return 138702705717598172
     */
    public static String getTimeToRubRandom() {
        return System.currentTimeMillis() + getRandom(5);
    }

    /**
     * <b>根据参数生成随机数<b><br/>
     *
     * @param digit 位数
     * @return String
     */
    public static String getRandom(int digit) {
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
     * 生成一个UUID
     *
     * @return String 生成的UUID
     */
    public static String randomUUID() {
        String s = UUID.randomUUID().toString();
        s = s.replace("-", ""); // 去掉uuid中的“-”
        return s.toUpperCase();
    }


   /* public static URI generateUrl(UriBuilder ub, String path, Map<String, String> queryParam) {
        ub.path(path);
        if (queryParam != null) {
            Iterator<String> itr = queryParam.keySet().iterator();
            while (itr.hasNext()) {
                String key = itr.next();
                ub.queryParam(key, queryParam.get(key));
            }
        }
        return ub.build();
    }*/

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

    public static boolean isTwoDecimals(BigDecimal amount) {
        return Pattern.matches("^\\d*.\\d{2}$", amount.toString());
    }

    public static boolean isLessOrEqualMillion(BigDecimal amount) {
        return amount.compareTo(new BigDecimal("100000000.00")) <= 0;
    }

    public static String amountUnitConverison(BigDecimal amount) {
        return amount.movePointRight(2).toString();
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

    public static void main(String[] args) {
  /*      BigDecimal amount = new BigDecimal("1234");
        System.out.println(Tools.isTwoDecimals(amount));
        System.out.println(Tools.isLessOrEqualMillion(amount));
        System.out.println(Tools.checkMobile("13455512345"));
        System.out.println(Tools.checkName("你是个小毛驴吧哈哈"));
        System.out.println(Tools.amountUnitConverison(amount));*/
    }

    /**
     * 解析出url请求的路径，包括页面
     *
     * @param strURL url地址
     * @return url路径
     */
    public static String getUrlParamValue(String strURL, String name) {
        if (StringUtils.isBlank(strURL)) {
            return "";
        }
        String strPage = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (arrSplit.length > 1) {
            for (String param : arrSplit) {
                String[] arrParam = param.split("[=]");
                if(name.equals(arrParam[0])){
                    return arrParam[1];
                }
            }
        }
        return "";
    }
}
