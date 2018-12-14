package com.huboot.business.base_model.pay.dao.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountBaseDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountBaseDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import com.huboot.business.mybatis.Pager;
import com.huboot.business.mybatis.QueryCondition;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
*账户中心-子账户基础信息DaoImpl
*/
@Repository
public class SubAccountBaseDaoImpl extends AbstractBaseDao<SubAccountBaseDomain, Long> implements ISubAccountBaseDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public SubAccountBaseDaoImpl() {
        super(SubAccountBaseDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

    @Override
    public Pager<Map<String, Object>> findSubAccountBaseByShopIdandType(QueryCondition queryCondition) {
        return this.findForPagerMapBySqlId(queryCondition, "findSubAccountBaseByShopIdandType");
    }
}