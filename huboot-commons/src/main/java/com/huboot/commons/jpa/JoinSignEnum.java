package com.huboot.commons.jpa;

import lombok.Getter;

/**
 * Created by xlaoy on 2017/12/23 0023.
 */
public enum JoinSignEnum {

    //关联查询使用的分隔符,仔细研究使用方法
    //暂时这么定：https://blog.csdn.net/drbing/article/details/51848529
    join(".", "join", "."),
    left_join(".-", "left join", "-"),
    right_join(".+", "right join", "+"),;

    @Getter
    private String name;
    @Getter
    private String dbOp;
    @Getter
    private String splitSign;

    private JoinSignEnum(String name, String dbOp, String splitSign) {
        this.name = name;
        this.splitSign = splitSign;
        this.dbOp = dbOp;
    }

}
