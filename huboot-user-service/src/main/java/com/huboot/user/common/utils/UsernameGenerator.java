package com.huboot.user.common.utils;


import com.huboot.commons.utils.AppAssert;

public class UsernameGenerator {

    private static final String SEPARATOR = "_";//初始化值

    /**
     * 生成用户名
     *
     * @param value
     * @return
     */
    public static String getUsername(String prefix, Integer value) {
        AppAssert.hasText(prefix, "公司代码不能为空");
        AppAssert.notNull(value, "编号不能为空");
        return prefix + SEPARATOR + autoGenericCode(value, 4);
    }

    /**
     * 不够位数的在前面补0，保留num的长度位数字
     *
     * @param code
     * @return
     */
    private static String autoGenericCode(Integer code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d",code );
        return result;
    }
}
