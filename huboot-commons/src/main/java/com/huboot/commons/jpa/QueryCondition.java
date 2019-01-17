package com.huboot.commons.jpa;

import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by xlaoy on 2017/12/25 0025.
 */
public class QueryCondition {

    //查询类型
    private Class<? extends AbstractEntity> clazz;
    //bean条件查询
    private List<ConditionMap> conditionMapList;
    //排序条件
    private Sort sort;
    //页数
    private int page = 0;
    //条数
    private int size = 0;
    //内链接字段名称
    private List<String> innerList = new ArrayList<>();
    //作外链接名称
    private List<String> leftOuterList = new ArrayList<>();

    private QueryCondition() {
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public static QueryCondition from(Class<? extends AbstractEntity> clazz) {
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

    public QueryCondition and(ConditionMap conditionMap) {
        this.conditionMapList.add(conditionMap);
        return this;
    }

    public QueryCondition where() {
        return this;
    }

    /**
     * OneToOn、ManyToMany 字段名称
     * @param inner
     * @return
     */
    public QueryCondition innerJoin(String inner) {
        this.innerList.add(inner + ".");
        return this;
    }

    /**
     * OneToOn、ManyToMany 字段名称
     * @param leftOuter
     * @return
     */
    public QueryCondition leftOuterJoin(String leftOuter) {
        this.leftOuterList.add(leftOuter + ".");
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

    public List<String> getInnerList() {
        return innerList;
    }

    public List<String> getLeftOuterList() {
        return leftOuterList;
    }
}
