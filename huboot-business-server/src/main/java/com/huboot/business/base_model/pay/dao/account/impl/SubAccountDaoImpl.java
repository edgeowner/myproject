package com.huboot.business.base_model.pay.dao.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
*账户中心-子账户DaoImpl
*/
@Repository
public class SubAccountDaoImpl extends AbstractBaseDao<SubAccountDomain, Long> implements ISubAccountDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public SubAccountDaoImpl() {
        super(SubAccountDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

}