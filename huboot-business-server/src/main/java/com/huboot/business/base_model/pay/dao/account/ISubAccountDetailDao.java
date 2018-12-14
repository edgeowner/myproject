package com.huboot.business.base_model.pay.dao.account;


import com.huboot.business.base_model.pay.domain.account.SubAccountDetailDomain;
import com.huboot.business.base_model.pay.dto.account.ZKLedgerSyncDTO;
import com.huboot.business.mybatis.IBaseDao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
*账户中心-子账户明细Dao
*/
public interface ISubAccountDetailDao extends IBaseDao<SubAccountDetailDomain,Long> {

    /**
     * 根据条件统计红包子账户
     *
     * @param map
     * @return
     */
    BigDecimal sumRedPacketSubAccount(Map<String, Object> map);

    List<ZKLedgerSyncDTO> syncZKLedgerData(Map<String, Object> map);
}
