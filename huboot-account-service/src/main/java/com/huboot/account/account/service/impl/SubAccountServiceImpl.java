package com.huboot.account.account.service.impl;

import com.huboot.account.account.dto.SubAccountAmountChangeResultDTO;
import com.huboot.account.account.dto.wycshop.BrokerageAccountDetailDTO;
import com.huboot.account.account.dto.wycshop.SubAccountPagerDTO;
import com.huboot.account.account.entity.AccountEntity;
import com.huboot.account.account.entity.SubAccountEntity;
import com.huboot.account.account.repository.IAccountRepository;
import com.huboot.account.account.repository.ISubAccountBillRepository;
import com.huboot.account.account.repository.ISubAccountRepository;
import com.huboot.account.account.service.ISubAccountService;
import com.huboot.account.support.yibao.YiBaoService;
import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.ConditionMap;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.BigDecimalUtil;
import com.huboot.commons.utils.LocalDateTimeUtils;
import com.huboot.share.account_service.api.dto.BrokerageDetail;
import com.huboot.share.account_service.api.dto.SubAccountCreateReqDTO;
import com.huboot.share.account_service.api.dto.SubAccountDTO;
import com.huboot.share.account_service.api.dto.yibao.CreateYiBaoAccountReqDTO;
import com.huboot.share.account_service.api.dto.yibao.reg.YibaoRegCompanyRespDTO;
import com.huboot.share.account_service.api.dto.yibao.reg.YibaoRegPersionRespDTO;
import com.huboot.share.account_service.api.dto.yibao.reg.YibaoRegStatusRespDTO;
import com.huboot.share.account_service.enums.AccountTypeEnum;
import com.huboot.share.account_service.enums.SubAccountStatusEnum;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import com.huboot.share.common.constant.SignConstant;
import com.huboot.share.common.enums.AbleEnum;
import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.api.feign.UserFeignClient;
import com.huboot.share.user_service.data.UserCacheData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *子账户ServiceImpl
 */
@Service("subAccountServiceImpl")
public class SubAccountServiceImpl implements ISubAccountService {

    private Logger logger = LoggerFactory.getLogger(SubAccountServiceImpl.class);

    @Autowired
    private IAccountRepository accountRepository;
    @Autowired
    private ISubAccountRepository subAccountRepository;
    @Autowired
    private ISubAccountBillRepository billRepository;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private UserCacheData userCacheData;

    @Autowired
    private YiBaoService yiBaoService;

    @Transactional
    @Override
    public void exchengeRelaId() {
        List<AccountEntity> list = accountRepository.findByType(AccountTypeEnum.company);
        for(AccountEntity accountEntity : list) {
            UserDetailInfo detailInfo = userCacheData.getUserDetailInfo(Long.valueOf(accountEntity.getRelaId()));
            accountEntity.setRelaId(detailInfo.getUser().getCompanyId().toString());
            accountRepository.modify(accountEntity);
        }
    }

    @Override
    public BrokerageDetail getBrokerageDetail(String relaId) {
        BrokerageDetail detail = new BrokerageDetail();
        SubAccountDTO subAccountDTO = this.getSubAccount(relaId, SubAccountTypeEnum.brokerage);
        detail.setUseableAmount(subAccountDTO.getUsableBalance());
        BigDecimal totalAmount = billRepository.getTotalAmount(subAccountDTO.getId(), SubAccountStatusEnum.has_received, SignConstant.POSITIVE);
        if(totalAmount != null) {
            detail.setTotalAmount(totalAmount);
        }
        BigDecimal usedAmount = billRepository.getTotalAmount(subAccountDTO.getId(), SubAccountStatusEnum.has_writeoff, SignConstant.NEGATIVE);
        if(usedAmount != null) {
            detail.setUsedAmount(usedAmount);
        }
        return detail;
    }

    @Override
    public SubAccountDTO getSubAccount(String relaId, SubAccountTypeEnum subAccountType) {
        SubAccountDTO dto = new SubAccountDTO();
        AccountEntity account = accountRepository.findByRelaId(relaId);
        AppAssert.notNull(account, "主账户不存在");
        SubAccountEntity subAccount = subAccountRepository.findByAccountIdAndType(account.getId(), subAccountType);
        AppAssert.notNull(subAccount, "子账户不存在");
        BeanUtils.copyProperties(subAccount, dto);
        return dto;
    }

    @Override
    public Long createSubAccount(SubAccountCreateReqDTO reqDTO) {
        AccountEntity accountEntity = accountRepository.findByRelaId(reqDTO.getRelaId());
        if(accountEntity == null) {
            accountEntity = new AccountEntity();
            accountEntity.setType(reqDTO.getType());
            accountEntity.setRelaId(reqDTO.getRelaId());
            accountEntity.setRelaOrgId(reqDTO.getOrganizationId().toString());
            accountEntity.setStatus(AbleEnum.enable);
            accountRepository.create(accountEntity);
        } else {
            if(!accountEntity.getType().equals(reqDTO.getType())) {
                logger.error(reqDTO.getRelaId() + "已经创建过" + accountEntity.getType().name() + "类型的主账户，不能再创建" + reqDTO.getType().name() + "类型账户");
                throw new BizException("创建账户错误");
            }
        }
        if(!CollectionUtils.isEmpty(reqDTO.getSubAccountTypeList())) {
            for(SubAccountTypeEnum subType : reqDTO.getSubAccountTypeList()) {
                SubAccountEntity subAccountEntity = subAccountRepository.findByAccountIdAndType(accountEntity.getId(), subType);
                if(subAccountEntity == null) {
                    subAccountEntity = new SubAccountEntity();
                    subAccountEntity.setAccountId(accountEntity.getId());
                    subAccountEntity.setType(subType);
                    subAccountEntity.setStatus(AbleEnum.enable);
                    subAccountEntity.setTotalBalance(BigDecimal.ZERO);
                    subAccountEntity.setUsableBalance(BigDecimal.ZERO);
                    subAccountEntity.setUnusableBalance(BigDecimal.ZERO);
                    subAccountRepository.create(subAccountEntity);
                } else {
                    logger.warn("子账户已存在,subAccountId=" + subAccountEntity.getId());
                }
            }
        }
        return accountEntity.getId();
    }

    @Override
    public SubAccountEntity getEnableSubAccount(String relaId, SubAccountTypeEnum subAccountType) {
        AccountEntity account = accountRepository.findByRelaId(relaId);
        AppAssert.notNull(account, "主账户不存在");
        if(!AbleEnum.enable.equals(account.getStatus())) {
            throw new BizException("主账户不可用");
        }
        SubAccountEntity subAccount = subAccountRepository.findByAccountIdAndType(account.getId(), subAccountType);
        AppAssert.notNull(subAccount, "子账户不存在");
        if(!AbleEnum.enable.equals(subAccount.getStatus())) {
            throw new BizException("子账户不可用");
        }
        return subAccount;
    }

    @Override
    public void checkSubAccountEnableForAll(Long fromSubAccountId, Long toSubAccountId) {
        SubAccountEntity fromSubAccount = subAccountRepository.find(fromSubAccountId);
        AppAssert.notNull(fromSubAccount, "付款方子账户不存在");
        if(!AbleEnum.enable.equals(fromSubAccount.getStatus())) {
            throw new BizException("付款方子账户不可用");
        }
        SubAccountEntity toSubAccount = subAccountRepository.find(toSubAccountId);
        AppAssert.notNull(toSubAccount, "收款方子账户不存在");
        if(!AbleEnum.enable.equals(toSubAccount.getStatus())) {
            throw new BizException("收款方子账户不可用");
        }
    }

    @Override
    public void checkAccount(Long accountId, String relaId) {
        AccountEntity account = accountRepository.find(accountId);
        AppAssert.notNull(account, "主账户不存在");
        if(!account.getRelaId().equals(relaId)) {
            throw new BizException("不是本人账户，不能查看");
        }
    }

    @Transactional
    @Override
    public SubAccountAmountChangeResultDTO changeAmount(Long subAccountId, BigDecimal amount, String sign, Boolean isFrozen) {
        if(!SignConstant.POSITIVE.equals(sign) && !SignConstant.NEGATIVE.equals(sign)) throw new BizException("更新账户操作符错误,sign=" + sign);
        int result = 0;
        if(SignConstant.POSITIVE.equals(sign) && isFrozen) {//冻结 +
            result = subAccountRepository.addUnusableBalance(subAccountId, amount);
        }
        if(SignConstant.POSITIVE.equals(sign) && !isFrozen) {//可用 +
            result = subAccountRepository.addUsableBalance(subAccountId, amount);
        }

        if(SignConstant.NEGATIVE.equals(sign) && isFrozen) {//冻结-
            result = subAccountRepository.reduceUnusableBalance(subAccountId, amount);
        }
        if(SignConstant.NEGATIVE.equals(sign) && !isFrozen) {//可用-
            result = subAccountRepository.reduceUsableBalance(subAccountId, amount);
        }
        if(result == 0) throw new BizException("账户更新金额失败");

        SubAccountEntity subAccount = subAccountRepository.find(subAccountId);
        SubAccountAmountChangeResultDTO resultDTO = new SubAccountAmountChangeResultDTO();
        if(isFrozen) {
            resultDTO.setAfterAmount(subAccount.getUnusableBalance());
        } else {
            resultDTO.setAfterAmount(subAccount.getUsableBalance());
        }

        resultDTO.setAmount(amount);
        if(SignConstant.POSITIVE.equals(sign)) {
            resultDTO.setPreAmount(resultDTO.getAfterAmount().subtract(resultDTO.getAmount()));
        }
        if(SignConstant.NEGATIVE.equals(sign)) {
            resultDTO.setPreAmount(resultDTO.getAfterAmount().add(resultDTO.getAmount()));
        }
        return resultDTO;
    }


    @Override
    public ShowPageImpl<SubAccountPagerDTO> getWycBrokerageSubAccountPager(String name, String phone, String idcar, Integer page, Integer size) {
        final List<String> userIdList = new ArrayList<>();
        if(!StringUtils.isEmpty(name)
                || !StringUtils.isEmpty(phone) || !StringUtils.isEmpty(idcar)) {
            List<Long> list = userFeignClient.getUserIdListCondition(name, phone, idcar);
            if(CollectionUtils.isEmpty(list)) {
                return ShowPageImpl.emptyPager(size);
            } else {
                for(Long id : list) {
                    userIdList.add(id.toString());
                }
            }
        }

        Page<SubAccountEntity> entityPage = subAccountRepository.findPage(QueryCondition.from(SubAccountEntity.class).innerJoin("account").where(l -> {
            l.add(ConditionMap.eq("account.relaOrgId", userCacheData.getCurrentUserOrgId().toString()));
            l.add(ConditionMap.eq("type", SubAccountTypeEnum.brokerage));
            if(!CollectionUtils.isEmpty(userIdList)) {
                l.add(ConditionMap.in("account.relaId", userIdList));
            }
        }).sort(Sort.by(Sort.Direction.DESC, "createTime")).limit(page, size));


        Page<SubAccountPagerDTO> dtoShowPage = entityPage.map(entity -> {
            SubAccountPagerDTO pagerDTO = new SubAccountPagerDTO();
            pagerDTO.setUserId(entity.getAccount().getRelaId());
            UserDetailInfo userDetailInfo = userCacheData.getUserDetailInfo(Long.valueOf(entity.getAccount().getRelaId()));
            pagerDTO.setName(userDetailInfo.getUser().getName());
            pagerDTO.setPhone(userDetailInfo.getUser().getPhone());
            pagerDTO.setIdcard(userDetailInfo.getUserPersonal().getIdCard());
            pagerDTO.setTotalBalance(BigDecimalUtil.amountShow(entity.getTotalBalance()));
            pagerDTO.setUpdateTime(LocalDateTimeUtils.formatTimeNormal(entity.getModifyTime()));
            return pagerDTO;
        });
        return new ShowPageImpl(dtoShowPage);
    }

    @Override
    public BrokerageAccountDetailDTO getBrokerageAccountDetail(Long userId) {
        SubAccountDTO subAccountDTO = this.getSubAccount(userId.toString(), SubAccountTypeEnum.brokerage);
        BrokerageAccountDetailDTO detailDTO = new BrokerageAccountDetailDTO();
        UserDetailInfo info = userCacheData.getUserDetailInfo(userId);
        detailDTO.setName(info.getUser().getName());
        detailDTO.setPhone(info.getUser().getPhone());
        detailDTO.setIdcard(info.getUserPersonal().getIdCard());
        detailDTO.setTotalBalance(BigDecimalUtil.amountShow(subAccountDTO.getTotalBalance()));
        return detailDTO;
    }

    /**
     * 创建易宝资金账户（商户在易宝平台分润结算账户）
     *
     * @param reqDTO
     *
     * @return merchantNo
     */
    @Override
    public String createSettlementAccount(Map<String, String> reqDTO) throws IOException {
        if(!reqDTO.containsKey("accountId")) throw new BizException("账户accountId不能为空");
        if(!reqDTO.containsKey("openType")) throw new BizException("开户类型不能为空");
        logger.info("开通易宝支付账户:{}",reqDTO);
        //TODO reqDTO需要持久化
        AccountEntity accountEntity = accountRepository.find(Long.valueOf(reqDTO.get("accountId")));
        if(accountEntity == null || AccountTypeEnum.company.equals(accountEntity.getType())) throw new BizException("系统结算账户异常");
        if(CreateYiBaoAccountReqDTO.REG_TYPE_PERSION.equals(reqDTO.get("openType"))){
            reqDTO.remove("accountId");
            reqDTO.remove("openType");
            YibaoRegPersionRespDTO yibaoRegPersionRespDTO = yiBaoService.regPersionMerchant(reqDTO);
            accountEntity.setYibaoMerchantNo(yibaoRegPersionRespDTO.getMerchantNo());
            accountRepository.modify(accountEntity);
            return yibaoRegPersionRespDTO.getAgreementContent();
        }
        if(CreateYiBaoAccountReqDTO.REG_TYPE_COMPANY.equals(reqDTO.get("openType"))){
            reqDTO.remove("accountId");
            reqDTO.remove("openType");
            YibaoRegCompanyRespDTO yibaoRegCompanyRespDTO = yiBaoService.regCompanyMerchantReg(reqDTO);
            accountEntity.setYibaoMerchantNo(yibaoRegCompanyRespDTO.getMerchantNo());
            accountRepository.modify(accountEntity);
            return null;
        }
        return null;
    }

    /**
     * 入网状态查询
     *
     * @param accountId
     */
    @Override
    public YibaoRegStatusRespDTO querySettlementAccount(Long accountId) throws IOException {
        AccountEntity accountEntity = accountRepository.find(accountId);
        if(accountEntity == null || StringUtils.isEmpty(accountEntity.getYibaoMerchantNo())) throw new BizException("系统结算账户异常");
        return yiBaoService.reqInfoQuery(accountEntity.getYibaoMerchantNo());
    }
}
