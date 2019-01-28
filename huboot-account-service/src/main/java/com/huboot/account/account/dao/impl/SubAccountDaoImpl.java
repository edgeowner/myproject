package com.huboot.account.account.dao.impl;

import com.huboot.account.account.entity.SubAccountEntity;
import com.huboot.commons.jpa.DefaultBaseDao;
import com.huboot.commons.page.ShowPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import com.huboot.account.account.dao.ISubAccountDao;
import com.huboot.account.account.repository.ISubAccountRepository;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*子账户DaoImpl
*/
@Repository("subAccountDaoImpl")
public class SubAccountDaoImpl extends DefaultBaseDao implements ISubAccountDao {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ISubAccountRepository repository;

    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    public ShowPageImpl<SubAccountEntity> getWycBrokerageSubAccountPager(List<String> userIdList, String orgId, Integer page, Integer size) {
        StringBuilder sql = new StringBuilder("select sub.* from ac_sub_account sub, ac_account acc where 1 = 1 ");
        Map<String, Object> params = new HashMap<>();
        sql.append("and acc.rela_org_id = :orgId");
        params.put("orgId", orgId);
        if(!CollectionUtils.isEmpty(userIdList)) {
            sql.append("and acc.rela_id in :userIdList");
            params.put("userIdList", userIdList);
        }
        sql.append(" order by sub.create_time desc ");
        return queryForPager(sql, SubAccountEntity.class, params, page, size);
    }

}