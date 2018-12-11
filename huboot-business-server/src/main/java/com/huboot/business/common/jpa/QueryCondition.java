package com.huboot.business.common.jpa;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by xlaoy on 2017/12/25 0025.
 */
public class QueryCondition {

    //查询类型
    private Class<?> clazz;
    //bean条件查询
    private List<ConditionMap> conditionMapList;
    //排序条件
    private Sort sort;
    //页数
    private int page = 0;
    //条数
    private int size = 0;

    private QueryCondition() {
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public static QueryCondition from(Class<?> clazz) {
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.clazz = clazz;
        queryCondition.conditionMapList = new ArrayList<>();
        return queryCondition;
    }

    public QueryCondition where(List<ConditionMap> conditionMapList) {
        this.conditionMapList = conditionMapList;
        return this;
    }

    public QueryCondition where(Consumer<List<ConditionMap>> consumer) {
        consumer.accept(this.conditionMapList);
        return this;
    }

    public QueryCondition where(ConditionMap conditionMap) {
        this.conditionMapList.add(conditionMap);
        return this;
    }

    public QueryCondition where() {
        return this;
    }

    /**
     * 取结果记录数
     * @param size
     * @return
     */
    public QueryCondition limit(int size) {
        this.size = size;
        return this;
    }

    /**
     * 分页查询
     * @param page
     * @param size
     * @return
     */
    public QueryCondition limit(int page, int size) {
        this.page = page;
        this.size = size;
        return this;
    }

    public QueryCondition sort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public List<ConditionMap> getConditionMapList() {
        return conditionMapList;
    }

    public Sort getSort() {
        return sort;
    }

    public int getPage() {
        if(page <= 0) {
            page = 1;
        }
        return page;
    }

    public int getSize() {
        if(size < 0) {
            size = 0;
        }
        return size;
    }

}
