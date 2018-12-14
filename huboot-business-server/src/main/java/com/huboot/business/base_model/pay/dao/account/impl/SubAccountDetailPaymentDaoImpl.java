package com.huboot.business.base_model.pay.dao.account.impl;

import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailPaymentDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
*账户中心-子账户明细支付基础信息DaoImpl
*/
@Repository
public class SubAccountDetailPaymentDaoImpl extends AbstractBaseDao<SubAccountDetailPaymentDomain, Long> implements ISubAccountDetailPaymentDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public SubAccountDetailPaymentDaoImpl() {
        super(SubAccountDetailPaymentDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

}