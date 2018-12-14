package com.huboot.business.base_model.pay.dao.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailPaymentBaseDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentBaseDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
*账户中心-子账户明细支付基础信息DaoImpl
*/
@Repository
public class SubAccountDetailPaymentBaseDaoImpl extends AbstractBaseDao<SubAccountDetailPaymentBaseDomain, Long> implements ISubAccountDetailPaymentBaseDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public SubAccountDetailPaymentBaseDaoImpl() {
        super(SubAccountDetailPaymentBaseDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

}