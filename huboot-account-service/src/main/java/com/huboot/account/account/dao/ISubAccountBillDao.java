package com.huboot.account.account.dao;

import com.huboot.account.account.entity.SubAccountBillEntity;
import com.huboot.commons.page.ShowPageImpl;

/**
*Dao
*/
public interface ISubAccountBillDao {

    ShowPageImpl<SubAccountBillEntity> getWycBrokerageSubAccountBillPager(Long subAccountId, String tripartiteSeq, String orderSource,
                                                                          String startTime, String endTime, Integer page, Integer size);
}
