package com.huboot.business.common.jpa;

import com.huboot.business.common.component.exception.BizException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 */
public abstract class DefaultBaseDao {

    private EntityManager entityManager;

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     *
     * @param params
     * @param pageIndex
     * @param pageSize
     * @param <R>
     * @return
     * @throws Exception
     */
    public <R> Page<R> queryForPager(StringBuilder sql, Class<R> resultClazz, Map<String, Object> params, Integer pageIndex, Integer pageSize){
        if(pageIndex == null || pageIndex <= 0){
            pageIndex = 1;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        if(StringUtils.isEmpty(sql.toString())){
            throw new BizException("sql 不能为空");
        }
        int count = getCount(sql.toString(), params);
        if(count == 0){
            return getShowPage(Collections.emptyList(), 0, pageIndex, pageSize);
        }
        String baseSql = sql.append(" limit " + (pageIndex - 1) * pageSize + ", " + pageSize).toString();
        List<R> result = getList(baseSql, params, resultClazz);
        return getShowPage(result, count, pageIndex - 1, pageSize);
    }

    /**
     * 获取count
     *
     * @param params
     * @return
     */
    public int getCount(String sql, Map<String, Object> params){
        String countSql = sql.toLowerCase();
        countSql = countSql.replaceFirst("from", "_from");
        Pattern pattern = Pattern.compile("select([\\s\\S]*)_from");
        Matcher matcher = pattern.matcher(countSql);
        String select = "";
        while (matcher.find()) {
            select = matcher.group(1);
            break;
        }
        if(StringUtils.isEmpty(select)) {
            throw new BizException("sql 有错误");
        }
        countSql = countSql.replace(select, " count(1) ");
        countSql = countSql.replaceFirst("_from", "from");
        Query query = entityManager.createNativeQuery(countSql);
        setParameters(query, params);
        BigInteger count = (BigInteger)query.getSingleResult();
        return count.intValue();
    }


    /**
     * 给hql参数设置值
     *
     * @param query  查询
     * @param params 参数
     */
    public void setParameters(Query query, Map<String, Object> params) {
        if(params == null) {
            return;
        }
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
    }


    public <R> Page<R> getShowPage(List<R> result, int count, int pageIndex, int pageSize){
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<R> pager = new PageImpl(result, pageable, count);
        return pager;
    }


    /**
     *
     * @param baseSql
     * @param params
     * @param clazz
     * @param <R>
     * @return
     */
    public <R> List<R> getList(String baseSql, Map<String, Object> params, Class<R> clazz){
        Query query = entityManager.createNativeQuery(baseSql.toString(), clazz);
        setParameters(query, params);
        List<R> list = query.getResultList();
        return list;
    }

}
