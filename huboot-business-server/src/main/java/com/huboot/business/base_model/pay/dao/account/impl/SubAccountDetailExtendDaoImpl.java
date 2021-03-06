package com.huboot.business.base_model.pay.dao.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailExtendDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailExtendDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
*账户中心-子账户明细扩展DaoImpl
*/
@Repository
public class SubAccountDetailExtendDaoImpl extends AbstractBaseDao<SubAccountDetailExtendDomain, Long> implements ISubAccountDetailExtendDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public SubAccountDetailExtendDaoImpl() {
        super(SubAccountDetailExtendDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

}