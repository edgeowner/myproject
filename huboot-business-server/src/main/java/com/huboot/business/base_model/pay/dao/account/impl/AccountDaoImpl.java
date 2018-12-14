package com.huboot.business.base_model.pay.dao.account.impl;

import com.huboot.business.base_model.pay.dao.account.IAccountDao;
import com.huboot.business.base_model.pay.domain.account.AccountDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
*账户中心-账户DaoImpl
*/
@Repository
public class AccountDaoImpl extends AbstractBaseDao<AccountDomain, Long> implements IAccountDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public AccountDaoImpl() {
        super(AccountDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

}