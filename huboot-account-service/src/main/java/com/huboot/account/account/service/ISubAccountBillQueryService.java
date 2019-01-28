package com.huboot.account.account.service;


import com.huboot.account.account.dto.SubAccountBillPagerDTO;
import com.huboot.account.account.dto.wycminiapp.SubAccountBillDetailDTO;
import com.huboot.account.account.dto.wycshop.SubAccountBillDetailPagerDTO;
import com.huboot.commons.page.ShowPageImpl;

/**
 *Service
 */
public interface ISubAccountBillQueryService {

    /**
     * 网约车司机端佣金账单列表查询
     * @return
     */
    ShowPageImpl<SubAccountBillPagerDTO> getWycBrokerageSubAccountBillPager(String relaId, String type, Integer page, Integer size);

    /**
     *
     * @return
     */
    SubAccountBillDetailDTO getWycBrokerageSubAccountBillDetail(String relaId, Long billId);


    /**
     * 网约车商户端佣金账单列表查询
     * @return
     */
    ShowPageImpl<SubAccountBillDetailPagerDTO> getWycBrokerageSubAccountBillPager(String userId, String tripartiteSeq, String orderSource,
                                                                                  String startTime, String endTime, Integer page, Integer size);

}
