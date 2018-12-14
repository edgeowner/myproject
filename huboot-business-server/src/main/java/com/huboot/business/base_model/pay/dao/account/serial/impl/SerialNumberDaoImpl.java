package com.huboot.business.base_model.pay.dao.account.serial.impl;


import com.huboot.business.base_model.pay.dao.account.serial.ISerialNumberDao;
import com.huboot.business.base_model.pay.domain.account.serial.SerialNumberDomain;
import com.huboot.business.mybatis.AbstractBaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
*字典值DaoImpl
*/
@Repository
public class SerialNumberDaoImpl extends AbstractBaseDao<SerialNumberDomain, Long> implements ISerialNumberDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public SerialNumberDaoImpl() {
        super(SerialNumberDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

}