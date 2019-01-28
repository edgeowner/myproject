package com.huboot.account.account.dao;

import com.huboot.account.account.entity.SubAccountEntity;
import com.huboot.commons.page.ShowPageImpl;

import java.util.List;

/**
*子账户Dao
*/
public interface ISubAccountDao {

    ShowPageImpl<SubAccountEntity> getWycBrokerageSubAccountPager(List<String> userIdList, String orgId, Integer page, Integer size);
}
