package com.huboot.business.mybatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by tonymac on 14/11/17.
 */
public interface IBaseDao<T, PK extends Serializable> {
    /**
     *  新增
     *
     * @param domain
     * @return
     */
    public PK create(T domain);

    /**
     * 查找所有的记录
     *
     * @return
     */
    public List<T> findAll();

    /**
     * 更新记录
     *
     * @param domain
     */
    public int update(T domain);

    /**
     * 更新指定非空字段
     *
     * @param domain
     */
    public int merge(T domain);

    /**
     * 更新记录
     * @param tage 要跟新的字段
     * @param sour 条件字段
     * @return
     */
    public int mergeByBeanProp(T tage, T sour);

    /**
     * 根据主键删除记录
     *
     * @param pk
     */
    public int remove(PK pk);

    /**
     * 根据主键查找记录
     *
     * @param pk
     * @return
     */
    public T find(PK pk);

    /**
     * 根据主键List查找记录
     *
     * @param pkList
     * @return
     */
    public List<T> find(List<PK> pkList);

    /**
     * 根据实体成员属性查找记录数
     *
     * @param domain
     * @return
     */
    public Integer findCountByBeanProp(T domain);

    /**
     * 根据实体成员属性查找记录
     *
     * @param domain
     * @return
     */
    public List<T> findByBeanProp(T domain);

    /**
     * 根据属性查询数据并按照某个字段排序
     *
     * @param domain
     * @return
     * @throws ApiException
     */
    public List<T> findByBeanPropWithOrder(T domain, List<CompositeOrder> compositeOrderList) throws ApiException;

    /**
     *  根据实体成员属性做分页查询
     *
     * @param domain
     * @param curPage
     * @param pageSize
     * @return
     */
    public Pager<T> findForPager(T domain, Integer curPage, Integer pageSize);

    /**
     * 动态查询
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    public Pager<T> findForPager(QueryCondition queryCondition)
            throws ApiException;

    /**
     * 动态查询
     *
     * @param queryCondition
     * @param sqlId
     * @return
     * @throws ApiException
     */
    public Pager<T> findForPager(QueryCondition queryCondition, String sqlId)
            throws ApiException;

    /**
     * 动态查询,返回map
     *
     * @param queryCondition
     * @return
     */
    public Pager<Map<String, Object>> findForPagerMap(QueryCondition queryCondition);

    /**
     * 计算被group的字段的count
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    public List<Map<String, Object>> countWithGroupBy(QueryCondition queryCondition) throws ApiException;
    /**
     * SQL函数
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    public Map<String, Object> findForMap(QueryCondition queryCondition) throws ApiException;
}
