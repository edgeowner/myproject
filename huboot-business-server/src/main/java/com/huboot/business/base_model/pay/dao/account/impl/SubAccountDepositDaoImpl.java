package com.huboot.business.base_model.pay.dao.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountDepositDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDepositDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import com.huboot.business.mybatis.Pager;
import com.huboot.business.mybatis.QueryCondition;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
*账户中心-押金账户扩展DaoImpl
*/
@Repository
public class SubAccountDepositDaoImpl extends AbstractBaseDao<SubAccountDepositDomain, Long> implements ISubAccountDepositDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public SubAccountDepositDaoImpl() {
        super(SubAccountDepositDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

    @Override
    public Pager<Map<String, Object>> findBuyerDepositBySeller(QueryCondition queryCondition) {
        return this.findForPagerMapBySqlId(queryCondition, "findBuyerDepositBySeller");
    }
}