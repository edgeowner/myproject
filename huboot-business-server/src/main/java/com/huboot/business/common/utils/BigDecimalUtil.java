package com.huboot.business.common.utils;

import com.huboot.business.common.component.exception.BizException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class BigDecimalUtil {

    /***************
     *  A  ==  B
     * @param numberA
     * @param numberB
     * @return
     * @throws NullPointerException
     */
    public static boolean eq(BigDecimal numberA, BigDecimal numberB) throws NullPointerException {
        if (numberA == null || numberB == null) {
            return false;
        }
        return numberA.compareTo(numberB) == 0;
    }

    /***************
     *  A  ==  B
     * @param numberA
     * @param numberB
     * @return
     * @throws NullPointerException
     */
    public static boolean neq(BigDecimal numberA, BigDecimal numberB) throws NullPointerException {
        return !eq(numberA, numberB);
    }

    /***************
     *  A  >  B
     * @param numberA
     * @param numberB
     * @return
     * @throws NullPointerException
     */
    public static boolean gt(BigDecimal numberA, BigDecimal numberB) throws NullPointerException {
        if (numberA == null || numberB == null) {
            return false;
        }
        return numberA.compareTo(numberB) > 0;
    }


    /************
     * A >= B
     * @param numberA
     * @param numberB
     * @return
     * @throws NullPointerException
     */
    public static boolean gte(BigDecimal numberA, BigDecimal numberB) throws NullPointerException {
        if (numberA == null || numberB == null) {
            return false;
        }
        return numberA.compareTo(numberB) >= 0;
    }


    /****
     * A  <  B
     * @param numberA
     * @param numberB
     * @return
     * @throws NullPointerException
     */
    public static boolean lt(BigDecimal numberA, BigDecimal numberB) throws NullPointerException {
        if (numberA == null || numberB == null) {
            return false;
        }
        return numberA.compareTo(numberB) < 0;
    }


    /***
     *  A <= B
     * @param numberA
     * @param numberB
     * @return
     * @throws NullPointerException
     */
    public static boolean lte(BigDecimal numberA, BigDecimal numberB) throws NullPointerException {
        if (numberA == null || numberB == null) {
            return false;
        }
        return numberA.compareTo(numberB) <= 0;
    }


    /**
     * 格式化货币
     *
     * @param number
     * @return
     */
    public static String formatCurrency(BigDecimal number) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        try {
            return format.format(number);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取一位小数--四舍五入
     *
     * @return
     */
    public static BigDecimal getOneDecimalRound(BigDecimal number) {
        BigDecimal result = number.setScale(1, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * 两数相除 取一位小数--四舍五入
     *
     * @param number
     * @param divisor
     * @return number/divisor
     */
    public static BigDecimal divideOneDecimalRound(BigDecimal number, BigDecimal divisor) {

        return number.divide(divisor, 1, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 获取两位小数--直接截取两位小数
     *
     * @return
     */
    public static BigDecimal getTwoDecimal(BigDecimal number) {
        DecimalFormat df = new DecimalFormat("#.00");

        return new BigDecimal(df.format(number));
    }

    /**
     * 获取两位小数--四舍五入
     *
     * @return
     */
    public static BigDecimal getTwoDecimalRound(BigDecimal number) {
        BigDecimal result = number.setScale(2, BigDecimal.ROUND_HALF_UP);
        return result;
    }

    /**
     * 获取两位小数--四舍五入
     * 去除小数位为0
     * @return
     */
    public static BigDecimal getTwoDecimalRoundInt(BigDecimal number) {
        DecimalFormat df = new DecimalFormat("#.##");
        BigDecimal result = new BigDecimal(df.format(number.setScale(2, BigDecimal.ROUND_HALF_UP)));
        return result;
    }

    /**
     * 获取两位小数--四舍五入
     * 返回字符串类型
     * @return
     */
    public static String getTwoDecimalRoundString(BigDecimal number) {
        DecimalFormat df = new DecimalFormat("#.##");
        String result = df.format(number.setScale(2, BigDecimal.ROUND_HALF_UP));
        return result;
    }

    /**
     * 两数相除 取两位小数--四舍五入
     *
     * @param number
     * @param divisor
     * @return number/divisor
     */
    public static BigDecimal divideTwoDecimalRound(BigDecimal number, BigDecimal divisor) {
        return number.divide(divisor, 2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 两数相除 取四位小数--四舍五入
     *
     * @param number
     * @param divisor
     * @return number/divisor
     */
    public static BigDecimal divideFourDecimalRound(BigDecimal number, BigDecimal divisor) {
        return number.divide(divisor, 4, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 两数相除 取两位小数--不需要取舍
     *
     * @param number
     * @param divisor
     * @return number/divisor
     */
    public static BigDecimal divideTwoDecimal(BigDecimal number, BigDecimal divisor) {

        return number.divide(divisor, 2, BigDecimal.ROUND_DOWN);
    }

    /**
     * 两数相除 取四位小数--不需要取舍
     *
     * @param number
     * @param divisor
     * @return number/divisor
     */
    public static BigDecimal divideFourDecimal(BigDecimal number, BigDecimal divisor) {
        return number.divide(divisor, 4, BigDecimal.ROUND_DOWN);
    }

    /***
     * 百分比格式化
     * **/
    public String print(double num1, double num2){
        if(Double.compare(num2, 0) == 0) throw new BizException("输入有误");
        double ratio = num1 / num2;
        NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(2);//设置保留几位小数
        return format.format(ratio);
    }
}
