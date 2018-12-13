package com.huboot.business.mybatis;


import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.*;

/**
 * @author zhangtiebin@hn-zhixin.com
 * @ClassName: QueryCondition
 * @Description: 查询条件定义
 * @date 2015年7月1日 下午10:47:27
 */
public class QueryCondition implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1633555554549219609L;
    @Deprecated
    private String[] order;// 指定排序字段，如order=colum
    private String direction;// 指定排序方式,asc为正序 desc为倒序,默认是asc
    private Integer page = 1;// 限定传输的记录数,如page=1,显示第一页的数据
    private Integer per_page = 10;// 指定分页的页大小,例如每页显示10个记录,per_page=10
    private Integer max_count;// 限制返回的最大数量，默认返回1024条，如果不需要限制设置为-1
    private Boolean random;// 限定随机选取结果集，程序中会取id>=random(min(id), max(id))

    private String[] group_by;// 以某些字段进行聚合，竖线分割
    private List<Condition> havingList;// 以某些字段进行聚合，过滤条件

    private String[] show_fileds;//

    private List<Condition> conditionList;// 其他查询条件

    private List<CompositeOrder> compositeOrderList;

    private String tableName;

    private Map<String, String> expressionMap;//SQL函数，例如：totalAmount,SUM(amount)

    @Deprecated
    public String[] getOrder() {
        return order;
    }

    @Deprecated
    public void setOrder(String[] order) {
        this.order = order;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }

    public Integer getMax_count() {
        return max_count;
    }

    public void setMax_count(Integer max_count) {
        this.max_count = max_count;
    }

    public Boolean getRandom() {
        return random;
    }

    public void setRandom(Boolean random) {
        this.random = random;
    }

    public String[] getGroup_by() {
        return group_by;
    }

    public void setGroup_by(String[] group_by) {
        this.group_by = group_by;
    }

    public List<Condition> getConditionList() {
        return conditionList;
    }

    public void setConditionList(List<Condition> conditionList) {
        this.conditionList = conditionList;
    }

    public String[] getShow_fileds() {
        return show_fileds;
    }

    public void setShow_fileds(String[] show_fileds) {
        this.show_fileds = show_fileds;
    }

    public Map<String, String> getExpressionMap() {
        return expressionMap;
    }

    public void setExpressionMap(Map<String, String> expressionMap) {
        this.expressionMap = expressionMap;
    }

    /**
     * 增加查询条件
     *
     * @param toAddconditionList
     * @return
     */
    public QueryCondition addCondition(List<Condition> toAddconditionList) {
        if (conditionList == null) {
            conditionList = new ArrayList<Condition>();
        }

        conditionList.addAll(toAddconditionList);

        return this;
    }

    /**
     * 增加查询条件
     *
     * @param condition
     * @return
     */
    public QueryCondition addCondition(Condition condition) {
        if (conditionList == null) {
            conditionList = new ArrayList<Condition>();
        }

        conditionList.add(condition);

        return this;
    }


    /**
     * 增加查询条件
     *
     * @param name    bean字段名称
     * @param colName 数据库字段名称
     * @param op      算子
     * @param value   条件值
     * @return
     */
    public QueryCondition addCondition(String name, String colName, QueryOperatorEnum op, Object value) {
        if (conditionList == null) {
            conditionList = new ArrayList<Condition>();
        }
        Condition condition = new Condition(name, op, value, colName);

        conditionList.add(condition);

        return this;
    }

    /**
     * 增加查询条件
     *
     * @param colName 数据库字段名称
     * @param op      算子
     * @param value   条件值
     * @return
     */
    public QueryCondition addConditionByColName(String colName, QueryOperatorEnum op, Object value) {
        if (conditionList == null) {
            conditionList = new ArrayList<Condition>();
        }
        Condition condition = new Condition(colName, op, value, colName);

        conditionList.add(condition);

        return this;
    }

    /**
     * 增加查询条件
     *
     * @param key
     * @param value
     * @return
     */
    public QueryCondition addExpressionMapByColName(String key, String value) {
        if (expressionMap == null) {
            expressionMap = new HashMap<>();
        }
        expressionMap.put(key, value);
        return this;
    }

    /**
     * 增加分组查询条件
     *
     * @param colName 数据库字段名称
     * @param op      算子
     * @param value   条件值
     * @return
     */
    public QueryCondition addHavingConditionByColName(String colName, QueryOperatorEnum op, Object value) {
        if (havingList == null) {
            havingList = new ArrayList<Condition>();
        }
        Condition condition = new Condition(colName, op, value, colName);

        havingList.add(condition);

        return this;
    }

    /**
     * 根据字段名称与算子移除查询条件
     *
     * @param colName
     * @param op
     * @return
     */
    public QueryCondition removeConditionByColName(String colName, QueryOperatorEnum op) {
        if (conditionList != null) {
            Iterator<Condition> it = conditionList.iterator();
            Condition condition = null;
            while (it.hasNext()) {
                condition = it.next();
                if (condition.getColName() != null && condition.getColName().equals(colName) &&
                        condition.getOp() == op) {
                    it.remove();
                }
            }
        }

        return this;
    }

    /**
     * 根据字段名称移除查询条件
     *
     * @param colName
     * @return
     */
    public QueryCondition removeConditionByColName(String colName) {
        if (conditionList != null) {
            Iterator<Condition> it = conditionList.iterator();
            Condition condition = null;
            while (it.hasNext()) {
                condition = it.next();
                if (condition.getColName() != null && condition.getColName().equals(colName)) {
                    it.remove();
                }
            }
        }
        return this;
    }

    /**
     * 根据domain字段名称与算子移除查询条件
     *
     * @param fieldName
     * @param op
     * @return
     */
    public QueryCondition removeConditionByFieldName(String fieldName, QueryOperatorEnum op) {
        if (conditionList != null) {
            Iterator<Condition> it = conditionList.iterator();
            Condition condition = null;
            while (it.hasNext()) {
                condition = it.next();
                if (condition.getName() != null && condition.getName().equals(fieldName) &&
                        condition.getOp() == op) {
                    it.remove();
                }
            }
        }

        return this;
    }

    /**
     * 根据domain字段名称移除查询条件
     *
     * @param fieldName
     * @param fieldName
     * @return
     */
    public QueryCondition removeConditionByField(String fieldName) {
        if (conditionList != null) {
            Iterator<Condition> it = conditionList.iterator();
            Condition condition = null;
            while (it.hasNext()) {
                condition = it.next();
                if (condition.getName() != null && condition.getName().equals(fieldName)) {
                    it.remove();
                }
            }
        }
        return this;
    }

    /**
     * 根据数据字段名称拿条件list
     *
     * @param colName
     * @return
     */
    public List<Condition> getConditionByColName(String colName) {
        List<Condition> list = new ArrayList<Condition>();
        if (conditionList != null) {
            Iterator<Condition> it = conditionList.iterator();
            Condition condition = null;
            while (it.hasNext()) {
                condition = it.next();
                if (condition.getColName() != null && condition.getColName().equals(colName)) {
                    list.add(condition);
                }
            }
        }
        return list;
    }

    /**
     * 根据domain中字段名称拿条件list
     *
     * @param fieldName
     * @return
     */
    public List<Condition> getConditionByFieldName(String fieldName) {
        List<Condition> list = new ArrayList<Condition>();
        if (conditionList != null) {
            Iterator<Condition> it = conditionList.iterator();
            Condition condition = null;
            while (it.hasNext()) {
                condition = it.next();
                if (condition.getName() != null && condition.getName().equals(fieldName)) {
                    list.add(condition);
                }
            }
        }
        return list;
    }

    /**
     * 根据domain中字段名称拿条件list
     *
     * @param fieldName
     * @return
     */
    public Condition getFirstConditionByFieldName(String fieldName) {
        List<Condition> list = new ArrayList<Condition>();
        if (conditionList != null) {
            Iterator<Condition> it = conditionList.iterator();
            Condition condition = null;
            while (it.hasNext()) {
                condition = it.next();
                if (condition.getName() != null && condition.getName().equals(fieldName)) {
                    list.add(condition);
                }
            }
        }
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 增加查询条件
     *
     * @param FieldName 查询字段名称映射domain的成员属性名称
     * @param colName   排序方向
     * @param direction 查询字段名称，映射数据字段名称
     * @return
     */
    public QueryCondition addCompositeOrder(String FieldName, String colName, String direction) {
        if (compositeOrderList == null) {
            compositeOrderList = new ArrayList<CompositeOrder>();
        }
        CompositeOrder compositeOrder = new CompositeOrder(FieldName, colName, direction);

        compositeOrderList.add(compositeOrder);

        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<CompositeOrder> getCompositeOrderList() {
        return compositeOrderList;
    }

    public void setCompositeOrderList(List<CompositeOrder> compositeOrderList) {
        this.compositeOrderList = compositeOrderList;
    }

    public List<Condition> getHavingList() {
        return havingList;
    }

    public void setHavingList(List<Condition> havingList) {
        this.havingList = havingList;
    }
}
