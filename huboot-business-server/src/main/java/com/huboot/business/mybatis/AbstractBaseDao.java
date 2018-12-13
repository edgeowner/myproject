package com.huboot.business.mybatis;

import com.huboot.business.common.utils.JsonUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tonymac on 14/12/8.
 */
public abstract class AbstractBaseDao<T extends AbstractDomain<PK>, PK extends Serializable> implements IBaseDao<T, PK> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    protected String packageName;

    public AbstractBaseDao(String packageName) {
        this.packageName = packageName;
    }

    /**
     * 从子类获取SqlSessionTemplate，用来支持多数据源
     *
     * @return
     */
    protected abstract SqlSessionTemplate getSqlSession();

    @SuppressWarnings("unchecked")
    @Override
    public PK create(T domain) {
        getSqlSession().insert(packageName + ".create", domain);
        return (PK) domain.getId();
    }

    @Override
    public List<T> findAll() {
        return getSqlSession().selectList(packageName + ".findAll");
    }

    @Override
    public int update(T domain) {
        return getSqlSession().update(packageName + ".update", domain);
    }

    @Override
    public int merge(T domain) {
        return getSqlSession().update(packageName + ".merge", domain);
    }

    @Override
    public int mergeByBeanProp(T tage, T sour) {
        Map<String, T> map = new HashMap<String, T>();
        map.put("tage", tage);
        map.put("sour", sour);
        return getSqlSession().update(packageName + ".mergeByBeanProp", map);
    }

    @Override
    public int remove(PK pk) {
        return getSqlSession().delete(packageName + ".remove", pk);
    }

    @Override
    public T find(PK pk) {
        return getSqlSession().selectOne(packageName + ".find", pk);
    }

    @Override
    public List<T> find(List<PK> pkList) {
        return getSqlSession().selectList(packageName + ".findByPKList", pkList);
    }

    @Override
    public Integer findCountByBeanProp(T domain) {
        return getSqlSession().selectOne(packageName + ".findCountByBeanProp", domain);
    }

    @Override
    public List<T> findByBeanProp(T domain) {
        return getSqlSession().selectList(packageName + ".findByBeanProp", domain);
    }

    @Override
    public List<T> findByBeanPropWithOrder(T domain, List<CompositeOrder> compositeOrderList) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("dom", domain);
        map.put("compositeOrderList", compositeOrderList);
        return getSqlSession().selectList(packageName + ".findByBeanPropWithOrder", map);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pager<T> findForPager(T domain, Integer curPage, Integer pageSize) {
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(domain);
        if (curPage == null) {
            curPage = 1;
        }

        if (pageSize == null) {
            pageSize = 20;
        }

        return findForPager("findForPager", parameter, curPage, pageSize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pager<T> findForPager(QueryCondition queryCondition)
            throws ApiException {
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);
        int curPage = queryCondition.getPage();
        int pageSize = queryCondition.getPer_page();
        return findForPager("dyFindForPager", parameter, curPage, pageSize);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pager<T> findForPager(QueryCondition queryCondition, String sqlId) {
        if (sqlId == null || "".equals(sqlId)) {
            throw new ApiException(ErrorCodeEnum.ErrorSQLID);
        }
//		else{
//			//in case sqlId looks like ".dyFindForPager"
//			//sqlId.replaceAll(".", "");
//		}
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);
        int curPage = queryCondition.getPage();
        int pageSize = queryCondition.getPer_page();
        return findForPager(sqlId, parameter, curPage, pageSize);

    }

    protected Pager<T> findForPager(String sqlId, Map<String, Object> parameter, Integer curPage, Integer pageSize)
            throws ApiException {

        Integer pageCount = null;
        if (curPage == 0) {
            curPage = 1;
        }

        if (pageSize == 0) {
            pageSize = 20;
        }

        int rowsCount = getSqlSession().selectOne(packageName + "." + sqlId + "Count", parameter);
        try {
            pageCount = ((rowsCount / pageSize) > (rowsCount / pageSize) ? (rowsCount / pageSize) + 1 : rowsCount / pageSize);
            if (rowsCount % pageSize > 0) {
                pageCount = pageCount + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //变相解决查询所有
        if (pageSize != null && pageSize.intValue() == -1) {
            pageCount = 0;
            curPage = 1;
            pageSize = rowsCount;
        }
        List<T> list = null;
        Map<String, Object> itemsPanorama = new HashMap();
        if (((curPage - 1) * pageSize) < rowsCount) {
            parameter.put("startNum", (curPage - 1) * pageSize);
            parameter.put("pageSize", pageSize);
            list = getSqlSession().selectList(packageName + "." + sqlId, parameter);
            itemsPanorama = findForPagerMap(sqlId, parameter);
        }
        //构建一个空list
        if (list == null) {
            list = new ArrayList<T>();
        }
        Pager<T> pager = new Pager<T>();

        pager.setItemsPanorama(itemsPanorama);
        pager.setPageItems(list);
        pager.setRowsCount(rowsCount);
        pager.setCurrPage(curPage);
        pager.setPageCount(pageCount.intValue());
        pager.setPageSize(pageSize);

        if (list == null || list.isEmpty()) {
            pager.setPageRowsCount(0);
        } else {
            pager.setPageRowsCount(list.size());
        }
        return pager;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> countWithGroupBy(QueryCondition queryCondition) throws ApiException {
        if (queryCondition == null) {
            return new ArrayList<Map<String, Object>>();
        }
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);
        List<Map<String, Object>> list = getSqlSession().selectList(packageName + ".countWithGroupBy", parameter);
        if (list == null) {
            return new ArrayList<Map<String, Object>>();
        }

        return list;
    }

    @Override
    public Pager<Map<String, Object>> findForPagerMap(QueryCondition queryCondition) {
        return this.findForPagerMapBySqlId(queryCondition, "findForPagerMap");
    }

    @SuppressWarnings("unchecked")
    protected Pager<Map<String, Object>> findForPagerMapBySqlId(QueryCondition queryCondition, String sqlId) {
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);
        Integer curPage = queryCondition.getPage();
        Integer pageSize = queryCondition.getPer_page();

        Integer pageCount = null;
        if (curPage == 0) {
            curPage = 1;
        }

        if (pageSize == 0) {
            pageSize = 20;
        }

        int rowsCount = getSqlSession().selectOne(packageName + "." + sqlId + "Count", parameter);
        try {
            pageCount = ((rowsCount / pageSize) > (rowsCount / pageSize) ? (rowsCount / pageSize) + 1 : rowsCount / pageSize);
            if (rowsCount % pageSize > 0) {
                pageCount = pageCount + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //变相解决查询所有
        if (pageSize != null && pageSize.intValue() == -1) {
            pageCount = 0;
            curPage = 1;
            pageSize = rowsCount;
        }
        List<Map<String, Object>> list = null;
        if (((curPage - 1) * pageSize) < rowsCount) {
            parameter.put("startNum", (curPage - 1) * pageSize);
            parameter.put("pageSize", pageSize);
            list = getSqlSession().selectList(packageName + "." + sqlId, parameter);
        }
        //构建一个空list
        if (list == null) {
            list = new ArrayList<Map<String, Object>>();
        }
        Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();

        pager.setPageItems(list);
        pager.setRowsCount(rowsCount);
        pager.setCurrPage(curPage);
        pager.setPageCount(pageCount.intValue());
        pager.setPageSize(pageSize);

        if (list == null || list.isEmpty()) {
            pager.setPageRowsCount(0);
        } else {
            pager.setPageRowsCount(list.size());
        }
        return pager;
    }

    @Override
    public Map<String, Object> findForMap(QueryCondition queryCondition) throws ApiException {
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);
        Map<String, String> sqlExpressionMap = (Map<String, String>) parameter.get("expressionMap");
        Map<String, Object> resultMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(sqlExpressionMap)) {
            resultMap = getSqlSession().selectOne(packageName + ".findForMap", parameter);
        }
        return resultMap;
    }

    private Map<String, Object> findForPagerMap(String sqlId, Map<String, Object> parameter) throws ApiException {
        Map<String, String> sqlExpressionMap = (Map<String, String>) parameter.get("expressionMap");
        Map<String, Object> resultMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(sqlExpressionMap)) {
            resultMap = getSqlSession().selectOne(packageName + "." + sqlId + "Map", parameter);
        }
        return resultMap;
    }


    @SuppressWarnings("unchecked")
    protected Pager<Map<String, Object>> findForMapBySqlId(QueryCondition queryCondition, String sqlId) {
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);

        List<Map<String, Object>> list = null;
        list = getSqlSession().selectList(packageName + "." + sqlId, parameter);

        //构建一个空list
        if (list == null) {
            list = new ArrayList<Map<String, Object>>();
        }
        Pager<Map<String, Object>> pager = new Pager<Map<String, Object>>();

        pager.setPageItems(list);

        return pager;
    }


    @SuppressWarnings("unchecked")
    protected List<Map<String, Object>> findForListMapBySqlId(QueryCondition queryCondition, String sqlId) {
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);

        List<Map<String, Object>> list = null;
        list = getSqlSession().selectList(packageName + "." + sqlId, parameter);

        //构建一个空list
        if (list == null) {
            list = new ArrayList<Map<String, Object>>();
        }

        return list;
    }

    @SuppressWarnings("unchecked")
    protected T findForDomainBySqlId(QueryCondition queryCondition, String sqlId) {
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);

        return getSqlSession().selectOne(packageName + "." + sqlId, parameter);
    }



    @SuppressWarnings("unchecked")
    protected List<T> findForListDomainBySqlId(QueryCondition queryCondition, String sqlId) {
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);

        return getSqlSession().selectList(packageName + "." + sqlId, parameter);
    }


    @SuppressWarnings("unchecked")
    protected int deleteFromBySqlId(QueryCondition queryCondition, String sqlId) {
        Map<String, Object> parameter = JsonUtils.fromObjectToMap(queryCondition);

        return getSqlSession().delete(packageName + "." + sqlId, parameter);
    }



}
