package com.huboot.business.mybatis;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author zhangtiebin@hn-zhixin.com
 * @ClassName: IBaseService
 * @Description: 基本服务接口
 * @date 2015年6月23日 下午4:40:50
 */
public interface IBaseService<Domain, PK extends Serializable> {
    /**
     * 创建一个对象
     *
     * @param domain
     * @return
     * @throws ApiException
     */
    public PK create(Domain domain) throws ApiException;

    /**
     * 创建一组对象
     *
     * @param domainList
     * @return
     * @throws ApiException
     */
    public List<Domain> create(List<Domain> domainList) throws ApiException;

    /**
     * 更新一个对象
     *
     * @param domain
     * @throws ApiException
     */
    public int update(Domain domain) throws ApiException;

    /**
     * 更新指定非空字段
     *
     * @param domain
     * @throws ApiException
     */
    public int merge(Domain domain) throws ApiException;

    /**
     * 更新记录
     * @param tage 要跟新的字段
     * @param sour 条件字段
     * @return
     */
    public int mergeByBeanProp(Domain tage, Domain sour);

    /**
     * 更新多个对象
     *
     * @param domainList
     * @throws ApiException
     */
    public int update(List<Domain> domainList) throws ApiException;

    /**
     * 根据主键删除一个对象
     *
     * @param pk
     * @throws ApiException
     */
    public int remove(PK pk) throws ApiException;


    /**
     * 根据主键集合删除一组对象
     *
     * @param pkList
     * @throws ApiException
     */
    public int remove(List<PK> pkList) throws ApiException;


    /**
     * 根据主键查询一个对象
     *
     * @param pk
     * @return
     * @throws ApiException
     */
    public Domain find(PK pk) throws ApiException;

    /**
     * 根据主键List查找记录
     *
     * @param pkList
     * @return
     */
    public List<Domain> find(List<PK> pkList) throws ApiException;

    /**
     * 根据主键查询一个对象
     *
     * @return
     * @throws ApiException
     */
    public List<Domain> findAll() throws ApiException;

    /**
     * 根据实体成员属性查找记录数
     *
     * @param domain
     * @return
     */
    public Integer findCountByBeanProp(Domain domain) throws ApiException;

    /**
     * 根据属性查询数据
     *
     * @param domain
     * @return
     * @throws ApiException
     */
    public List<Domain> findByBeanProp(Domain domain) throws ApiException;

    /**
     * 根据属性查询数据并按照某个字段排序
     *
     * @param domain
     * @return
     * @throws ApiException
     */
    public List<Domain> findByBeanPropWithOrder(Domain domain, List<CompositeOrder> compositeOrderList) throws ApiException;


    /***
     * 根据属性查询数据 返回结果的第一个,如果结果有多个 将【报错】
     *
     * @param domain
     * @return null or object
     * @throws ApiException
     */
    public Domain getSingleByBeanProp(Domain domain) throws ApiException;


    /**********
     * 根据属性查询数据 ,返回第一个
     *
     * @param domain
     * @return null or object
     * @throws ApiException
     */
    public Domain getFirstByBeanProp(Domain domain) throws ApiException;


    /**
     * 分页查询
     *
     * @param domain
     * @param curPage
     * @param pageSize
     * @return
     * @throws ApiException
     */
    public Pager<Domain> findForPager(Domain domain, Integer curPage, Integer pageSize) throws ApiException;

    /**
     * 分页查询
     *
     * @param queryCondition
     * @return
     * @throws ApiException
     */
    public Pager<Domain> findForPager(QueryCondition queryCondition) throws ApiException;

    /**
     * 动态查询,返回map
     *
     * @param queryCondition
     * @return
     */
    public Pager<Map<String, Object>> findForPagerMap(QueryCondition queryCondition) throws ApiException;

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

    /**
     * 创建一个对象Init
     *
     * @param domain
     * @return
     * @throws ApiException
     */
    public PK createInit(Domain domain) throws ApiException;
}
