package com.huboot.account.account.service.impl;

import com.huboot.account.account.dao.ISubAccountBillDao;
import com.huboot.account.account.dto.SubAccountBillPagerDTO;
import com.huboot.account.account.dto.wycminiapp.SubAccountBillDetailDTO;
import com.huboot.account.account.dto.wycshop.SubAccountBillDetailPagerDTO;
import com.huboot.account.account.repository.ISubAccountBillRepository;
import com.huboot.account.account.service.ISubAccountService;
import com.huboot.account.account.entity.SubAccountBillEntity;
import com.huboot.account.account.service.ISubAccountBillQueryService;
import com.huboot.account.payment.entity.PaymentOrderEntity;
import com.huboot.account.payment.entity.PaymentSequenceEntity;
import com.huboot.account.payment.repository.IPaymentOrderRepository;
import com.huboot.account.payment.repository.IPaymentSequenceRepository;
import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.BigDecimalUtil;
import com.huboot.commons.utils.LocalDateTimeUtils;
import com.huboot.share.account_service.api.dto.SubAccountDTO;
import com.huboot.share.account_service.enums.PaymentOrderSourceEnum;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.data.UserCacheData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by Administrator on 2018/9/3 0003.
 */
@Service("subAccountBillQueryServiceImpl")
public class SubAccountBillQueryServiceImpl implements ISubAccountBillQueryService {

    @Autowired
    private ISubAccountService subAccountService;
    @Autowired
    private ISubAccountBillRepository subAccountBillRepository;
    @Autowired
    private IPaymentSequenceRepository sequenceRepository;
    @Autowired
    private ISubAccountBillDao subAccountBillDao;
    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private IPaymentOrderRepository orderRepository;

    /**
     * 网约车司机端佣金账单列表查询
     * @param relaId
     * @param type
     * @param page
     * @param size
     * @return
     */
    @Override
    public ShowPageImpl<SubAccountBillPagerDTO> getWycBrokerageSubAccountBillPager(String relaId, String type, Integer page, Integer size) {
        SubAccountDTO accountDTO = subAccountService.getSubAccount(relaId, SubAccountTypeEnum.brokerage);
        AppAssert.notNull(accountDTO, "佣金账户不存在");
        String sign = "";
        if("income".equals(type)) {
            sign = "+";
        }
        if("outcome".equals(type)) {
            sign = "-";
        }
        final String fsign = sign;
        Page<SubAccountBillEntity> entityPage = subAccountBillRepository.findPage(QueryCondition.from(SubAccountBillEntity.class).where(list -> {
            list.add(ConditionMap.eq("subAccountId", accountDTO.getId()));
            if(!StringUtils.isEmpty(fsign)) {
                list.add(ConditionMap.eq("sign", fsign));
            }
        }).sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size));

        Page<SubAccountBillPagerDTO> dtoPager = entityPage.map(entity -> {
            SubAccountBillPagerDTO dto = new SubAccountBillPagerDTO();
            dto.setId(entity.getId());
            dto.setOrderSource(entity.getOrderSource().name());
            dto.setOrderSourceName(entity.getOrderSource().getShowName());
            dto.setStatus(entity.getStatus().name());
            dto.setStatusName(entity.getStatus().getShowName());
            dto.setSign(entity.getSign());
            dto.setPreAmount(BigDecimalUtil.amountShow(entity.getPreAmount()));
            dto.setAmount(BigDecimalUtil.amountShow(entity.getAmount()));
            dto.setAfterAmount(BigDecimalUtil.amountShow(entity.getAfterAmount()));
            dto.setRemark(entity.getRemark());
            dto.setOperateDate(LocalDateTimeUtils.formatTime(entity.getCreateTime(), LocalDateTimeUtils.NORMAL_DATE));
            dto.setOperateTime(LocalDateTimeUtils.formatTime(entity.getCreateTime(), LocalDateTimeUtils.NORMAL_DATETIME));
            return dto;
        });

        return new ShowPageImpl(dtoPager);
    }

    @Override
    public SubAccountBillDetailDTO getWycBrokerageSubAccountBillDetail(String relaId, Long billId) {
        SubAccountBillEntity billEntity = subAccountBillRepository.find(billId);
        AppAssert.notNull(billEntity, "账单不存在");
        subAccountService.checkAccount(billEntity.getAccountId(), relaId);
        PaymentSequenceEntity sequenceEntity = sequenceRepository.find(billEntity.getPaymentSeqId());
        AppAssert.notNull(sequenceEntity, "流水详情不存在");
        SubAccountBillDetailDTO detailDTO = new SubAccountBillDetailDTO();
        detailDTO.setId(billEntity.getId());
        detailDTO.setTripartiteSeq(sequenceEntity.getTripartiteSeq());
        detailDTO.setOrderSource(billEntity.getOrderSource().name());
        detailDTO.setOrderSourceName(billEntity.getOrderSource().getShowName());
        detailDTO.setAmount(BigDecimalUtil.amountShow(billEntity.getAmount()));
        detailDTO.setAfterAmount(BigDecimalUtil.amountShow(billEntity.getAfterAmount()));
        detailDTO.setRemark(billEntity.getRemark());
        detailDTO.setSign(billEntity.getSign());
        detailDTO.setOperateTime(LocalDateTimeUtils.formatTime(billEntity.getCreateTime(), LocalDateTimeUtils.NORMAL_DATETIME));
        PaymentOrderEntity orderEntity = orderRepository.find(sequenceEntity.getOrderId());
        detailDTO.setPaymentSn(orderEntity.getSn());
        return detailDTO;
    }

    @Override
    public ShowPageImpl<SubAccountBillDetailPagerDTO> getWycBrokerageSubAccountBillPager(String userId, String tripartiteSeq, String orderSource,
                                                                                         String startTime, String endTime, Integer page, Integer size) {
        if(!StringUtils.isEmpty(orderSource)) {
            if(!PaymentOrderSourceEnum.wyctdriver.name().equals(orderSource)
                    && !PaymentOrderSourceEnum.wycdriver_writeoff.name().equals(orderSource)) {
                throw new BizException("类型错误");
            }
        }
        SubAccountDTO accountDTO = subAccountService.getSubAccount(userId, SubAccountTypeEnum.brokerage);
        ShowPageImpl<SubAccountBillEntity> pager = subAccountBillDao.getWycBrokerageSubAccountBillPager(accountDTO.getId(), tripartiteSeq, orderSource, startTime + " 00:00:00", endTime + " 23:59:59", page, size);

        ShowPageImpl<SubAccountBillDetailPagerDTO> detailPager = pager.map(dto -> {
            SubAccountBillDetailPagerDTO detailPagerDTO = new SubAccountBillDetailPagerDTO();
            PaymentSequenceEntity sequenceEntity = sequenceRepository.find(dto.getPaymentSeqId());
            detailPagerDTO.setTripartiteSeq(sequenceEntity.getTripartiteSeq());
            detailPagerDTO.setOrderSource(dto.getOrderSource().name());
            if(PaymentOrderSourceEnum.wyctdriver.equals(dto.getOrderSource())) {
                detailPagerDTO.setOrderSourceName("返佣");
            }
            if(PaymentOrderSourceEnum.wycdriver_writeoff.equals(dto.getOrderSource())) {
                detailPagerDTO.setOrderSourceName("提现");
            }
            detailPagerDTO.setPreAmount(BigDecimalUtil.amountShow(dto.getPreAmount()));
            detailPagerDTO.setAmount(BigDecimalUtil.amountShow(dto.getAmount()));
            detailPagerDTO.setAfterAmount(BigDecimalUtil.amountShow(dto.getAfterAmount()));

            UserDetailInfo userDetailInfo = userCacheData.getUserDetailInfo(Long.valueOf(sequenceEntity.getCreatorId()));
            detailPagerDTO.setCreateTime(LocalDateTimeUtils.formatTimeNormal(sequenceEntity.getCreateTime()));
            detailPagerDTO.setCreatorId(userDetailInfo.getUser().getUserId().toString());
            detailPagerDTO.setCreatorName(userDetailInfo.getUser().getName());
            detailPagerDTO.setRemark(dto.getRemark());
            return detailPagerDTO;
        });

        return detailPager;
    }
}
