package com.huboot.business.mybatis;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;

/**
 * Created by user on 2016-05-09.
 */
public class AssertUtil {

    public static void isTrue(boolean expr, ErrorCodeEnum errorCodeEnum, String message) {
        if (!expr) {
            if (message == null)
                throw new ApiException(errorCodeEnum);
            else
                throw new ApiException(errorCodeEnum, message);
        }
    }

    public static void notNull(Object object, ErrorCodeEnum errorCodeEnum, String message) {
        isTrue(object != null, errorCodeEnum, message);
    }

    public static void notNull(Object object, ErrorCodeEnum errorCodeEnum) {
        notNull(object, errorCodeEnum, null);
    }

    public static void notNull(Object object, String message) {
        notNull(object, ErrorCodeEnum.NOTNULL, message);
    }

    public static void notNull(Object object) {
        notNull(object, ErrorCodeEnum.NOTNULL);
    }

    /**
     * 校验集合
     */
    public static void notEmpty(Collection<?> collection, ErrorCodeEnum errorCodeEnum, String message) {
        if (CollectionUtils.isEmpty(collection)) {
            if (StringUtils.isEmpty(message)) {
                throw new ApiException(errorCodeEnum);
            } else {
                throw new ApiException(errorCodeEnum, message);
            }
        }
    }

    public static void notEmpty(Collection<?> collection, ErrorCodeEnum errorCodeEnum) {
        notEmpty(collection, errorCodeEnum, null);
    }

    public static void notEmpty(Collection<?> collection) {
        notEmpty(collection, ErrorCodeEnum.NOTNULL);
    }

    /**
     * 校验字符串
     */
    public static void notBlank(String str, ErrorCodeEnum errorCodeEnum, String message) {
        if (!StringUtils.hasText(str)) {
            if (StringUtils.hasText(message)) {
                throw new ApiException(errorCodeEnum, message);
            } else {
                throw new ApiException(errorCodeEnum);
            }
        }
    }

    public static void notBlank(String str, String message) {
        notBlank(str, ErrorCodeEnum.NOTNULL, message);
    }

    public static void notBlank(String str, ErrorCodeEnum errorCodeEnum) {
        notNull(str, errorCodeEnum, null);
    }

    public static void notBlank(String str) {
        notBlank(str, ErrorCodeEnum.NOTNULL);
    }

    /**
     * 输入参数错误
     *
     * @param expr
     * @param message
     */
    public static void paramValid(boolean expr, String message) {
        isTrue(expr, ErrorCodeEnum.ParamsError, message);
    }


}
