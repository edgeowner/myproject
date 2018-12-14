package com.huboot.business.base_model.pay.dao.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailPaymentPingppDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailPaymentPingppDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
*账户中心-子账户明细支付PINGPPDaoImpl
*/
@Repository
public class SubAccountDetailPaymentPingppDaoImpl extends AbstractBaseDao<SubAccountDetailPaymentPingppDomain, Long> implements ISubAccountDetailPaymentPingppDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public SubAccountDetailPaymentPingppDaoImpl() {
        super(SubAccountDetailPaymentPingppDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

}