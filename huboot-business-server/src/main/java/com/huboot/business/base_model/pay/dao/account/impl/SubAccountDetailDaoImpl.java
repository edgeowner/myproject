package com.huboot.business.base_model.pay.dao.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountDetailDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountDetailDomain;
import com.huboot.business.base_model.pay.dto.account.ZKLedgerSyncDTO;
import com.huboot.business.mybatis.AbstractBaseDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
*账户中心-子账户明细DaoImpl
*/
@Repository
public class SubAccountDetailDaoImpl extends AbstractBaseDao<SubAccountDetailDomain, Long> implements ISubAccountDetailDao {

    @Qualifier("sqlSessionTemplate")
    @Autowired
    private SqlSessionTemplate sqlSession = null;

    public SubAccountDetailDaoImpl() {
        super(SubAccountDetailDaoImpl.class.getName());
    }

    public SqlSessionTemplate getSqlSession(){
        return sqlSession;
    }

    @Override
    public BigDecimal sumRedPacketSubAccount(Map<String, Object> map) {
        return this.getSqlSession().selectOne(packageName + ".sumRedPacketSubAccount", map);
    }

    @Override
    public List<ZKLedgerSyncDTO> syncZKLedgerData(Map<String, Object> map) {
        return this.getSqlSession().selectList(packageName + ".syncZKLedgerData", map);
    }
}