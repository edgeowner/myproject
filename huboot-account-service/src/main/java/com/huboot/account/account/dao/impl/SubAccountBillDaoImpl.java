package com.huboot.account.account.dao.impl;

import com.huboot.account.account.dao.ISubAccountBillDao;
import com.huboot.account.account.entity.SubAccountBillEntity;
import com.huboot.commons.jpa.DefaultBaseDao;
import com.huboot.commons.page.ShowPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
*DaoImpl
*/
@Repository("subAccountBillDaoImpl")
public class SubAccountBillDaoImpl extends DefaultBaseDao implements ISubAccountBillDao {

    @Override
    @Autowired
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }

    @Override
    public ShowPageImpl<SubAccountBillEntity> getWycBrokerageSubAccountBillPager(Long subAccountId, String tripartiteSeq, String orderSource,
                                                                                 String startTime, String endTime, Integer page, Integer size) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select bill.* from ac_sub_account_bill bill, ac_payment_sequence seq where bill.payment_seq_id = seq.id and bill.sub_account_id = " + subAccountId);
        if(!StringUtils.isEmpty(tripartiteSeq)) {
            sql.append(" and seq.tripartite_seq like :tripartiteSeq ");
            params.put("tripartiteSeq", "%" + tripartiteSeq + "%");
        }
        if(!StringUtils.isEmpty(orderSource)) {
            sql.append(" and bill.order_source = :orderSource ");
            params.put("orderSource", orderSource);
        }
        if(!StringUtils.isEmpty(startTime)) {
            sql.append(" and seq.create_time >= :startTime ");
            params.put("startTime", startTime + " 00:00:00");
        }
        if(!StringUtils.isEmpty(endTime)) {
            sql.append(" and seq.create_time <= :endTime ");
            params.put("endTime", endTime + " 23:59:59");
        }
        sql.append(" order by seq.create_time desc ");
        return this.queryForPager(sql, SubAccountBillEntity.class, params, page, size);
    }
}