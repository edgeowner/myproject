package com.huboot.commons.utils;


import javax.persistence.Table;

public class HibernateToolsUtil {
    /**
     * 获得表名
     *
     * @param clazz 映射到数据库的po类
     * @return String
     */

    @SuppressWarnings("unchecked")
    public static String getTableName(Class clazz) {
        //获取到table 的名字
        boolean exists = clazz.isAnnotationPresent(Table.class); //class 中有没有table的注解
        if (!exists) {
            return "";
        }
        Table t = (Table) clazz.getAnnotation(Table.class);
        return t.name();
    }
}
