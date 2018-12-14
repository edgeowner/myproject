package com.huboot.business.base_model.pay.dao.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountDepositDomain;
import com.huboot.business.mybatis.IBaseDao;
import com.huboot.business.mybatis.Pager;
import com.huboot.business.mybatis.QueryCondition;

import java.util.Map;

/**
*账户中心-押金账户扩展Dao
*/
public interface ISubAccountDepositDao extends IBaseDao<SubAccountDepositDomain,Long> {

    /**
     * 根据卖家查询买家押金账户
     *
     * @param queryCondition
     * @return
     * @throws
     */
    Pager<Map<String, Object>> findBuyerDepositBySeller(QueryCondition queryCondition);
}
