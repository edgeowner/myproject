package com.huboot.commons.jpa;

import java.util.Arrays;
import java.util.List;

/**
 * Created by xlaoy on 2017/12/25 0025.
 */
public class ConditionMap {

    private String field;
    private QuerySignEnum signEnum;
    private Object value;


    private ConditionMap(String field, QuerySignEnum signEnum, Object value) {
        this.field = field;
        this.signEnum = signEnum;
        this.value = value;
    }

    public static ConditionMap eq(String field, Object value) {
        return new ConditionMap(field, QuerySignEnum.eq, value);
    }

    public static ConditionMap lt(String field, Object value) {
        return new ConditionMap(field, QuerySignEnum.lt, value);
    }

    public static ConditionMap lte(String field, Object value) {
        return new ConditionMap(field, QuerySignEnum.lte, value);
    }

    public static ConditionMap gt(String field, Object value) {
        return new ConditionMap(field, QuerySignEnum.gt, value);
    }

    public static ConditionMap gte(String field, Object value) {
        return new ConditionMap(field, QuerySignEnum.gte, value);
    }

    public static ConditionMap ne(String field, Object value) {
        return new ConditionMap(field, QuerySignEnum.ne, value);
    }

    public static ConditionMap in(String field, List value) {
        return new ConditionMap(field, QuerySignEnum.in, value);
    }

    public static ConditionMap like(String field, Object value) {
        return new ConditionMap(field, QuerySignEnum.like, value);
    }

    /**
     * ConditionMap的field参数，占位使用
     *
     * @param conditionMap
     * @return
     */
    public static ConditionMap or(ConditionMap... conditionMap) {
        return new ConditionMap("field", QuerySignEnum.or, Arrays.asList(conditionMap));
    }

    public static ConditionMap build(String field, QuerySignEnum signEnum, Object value) {
        return new ConditionMap(field, signEnum, value);
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public QuerySignEnum getSignEnum() {
        return signEnum;
    }

    public void setSignEnum(QuerySignEnum signEnum) {
        this.signEnum = signEnum;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
