package com.huboot.business.base_model.pay.dao.account.impl;


import com.huboot.business.base_model.pay.dao.account.IAccountBaseDao;
import com.huboot.business.base_model.pay.domain.account.AccountBaseDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
*账户中心-账户基础信息DaoImpl
*/
@Repository
public class AccountBaseDaoImpl extends AbstractBaseDao<AccountBaseDomain, Long> implements IAccountBaseDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public AccountBaseDaoImpl() {
        super(AccountBaseDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

}