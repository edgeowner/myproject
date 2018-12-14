package com.huboot.business.base_model.pay.service.account.impl;


import com.huboot.business.base_model.pay.dao.account.ISubAccountDao;
import com.huboot.business.base_model.pay.domain.account.SubAccountBaseDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountDomain;
import com.huboot.business.base_model.pay.dto.account.AccountDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountDTO;
import com.huboot.business.base_model.pay.enums.AccountStatusEnum;
import com.huboot.business.base_model.pay.enums.SubAccountTypeEnum;
import com.huboot.business.base_model.pay.service.account.IAccountService;
import com.huboot.business.base_model.pay.service.account.ISubAccountBaseService;
import com.huboot.business.base_model.pay.service.account.ISubAccountDetailService;
import com.huboot.business.base_model.pay.service.account.ISubAccountService;
import com.huboot.business.mybatis.AbstractBaseService;
import com.huboot.business.mybatis.AssertUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 账户中心-子账户ServiceImpl
 */
@Service("subAccountServiceImpl")
public class SubAccountServiceImpl extends AbstractBaseService<SubAccountDomain, Long> implements ISubAccountService {

    @Autowired
    public SubAccountServiceImpl(ISubAccountDao subAccountDao) {
        super(subAccountDao);
    }

    @Autowired
    private ISubAccountBaseService subAccountBaseService;
    @Autowired
    private IAccountService accountService;
    @Autowired
    private ISubAccountDetailService subAccountDetailService;

    @Override
    public SubAccountDTO initBalanceAccount(Long accountId) {
        SubAccountDTO subAccountDTO = new SubAccountDTO();
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        subAccountBaseDomain.setAccountId(accountId);
        subAccountBaseDomain.setType(SubAccountTypeEnum.BalanceAccount.getValue());
        subAccountBaseDomain = subAccountBaseService.getSingleByBeanProp(subAccountBaseDomain);
        if (subAccountBaseDomain == null) {
            subAccountBaseDomain = new SubAccountBaseDomain();
            SubAccountDomain subAccountDomain = new SubAccountDomain();
            subAccountDomain.setAccountId(accountId);
            this.create(subAccountDomain);
            subAccountBaseDomain.setAccountId(accountId);
            subAccountBaseDomain.setSubAccountId(subAccountDomain.getId());
            subAccountBaseDomain.setBalance(BigDecimal.ZERO);
            subAccountBaseDomain.setType(SubAccountTypeEnum.BalanceAccount.getValue());
            subAccountBaseDomain.setStatus(AccountStatusEnum.Valid.getValue());
            subAccountBaseService.create(subAccountBaseDomain);
        }
        subAccountDTO.setSubAccountBaseDomain(subAccountBaseDomain);
        return subAccountDTO;
    }

    @Override
    public SubAccountDTO initRedPacketAccount(Long accountId) {
        SubAccountDTO subAccountDTO = new SubAccountDTO();
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        subAccountBaseDomain.setAccountId(accountId);
        subAccountBaseDomain.setType(SubAccountTypeEnum.RedPacketAccount.getValue());
        subAccountBaseDomain = subAccountBaseService.getSingleByBeanProp(subAccountBaseDomain);
        if (subAccountBaseDomain == null) {
            subAccountBaseDomain = new SubAccountBaseDomain();
            SubAccountDomain subAccountDomain = new SubAccountDomain();
            subAccountDomain.setAccountId(accountId);
            this.create(subAccountDomain);
            subAccountBaseDomain.setAccountId(accountId);
            subAccountBaseDomain.setSubAccountId(subAccountDomain.getId());
            subAccountBaseDomain.setBalance(BigDecimal.ZERO);
            subAccountBaseDomain.setType(SubAccountTypeEnum.RedPacketAccount.getValue());
            subAccountBaseDomain.setStatus(AccountStatusEnum.Valid.getValue());
//            subAccountBaseDomain.setExpireDate(AccountConstant.RED_PACKET_EXPIRE_DATE);
            subAccountBaseService.create(subAccountBaseDomain);
        }
        subAccountDTO.setSubAccountBaseDomain(subAccountBaseDomain);
        return subAccountDTO;
    }

    @Override
    public SubAccountDTO initThirdPartyAccount(Long accountId) {
        SubAccountDTO subAccountDTO = new SubAccountDTO();
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        subAccountBaseDomain.setAccountId(accountId);
        subAccountBaseDomain.setType(SubAccountTypeEnum.ThirdPartyAccount.getValue());
        subAccountBaseDomain = subAccountBaseService.getSingleByBeanProp(subAccountBaseDomain);
        if (subAccountBaseDomain == null) {
            subAccountBaseDomain = new SubAccountBaseDomain();
            SubAccountDomain subAccountDomain = new SubAccountDomain();
            subAccountDomain.setAccountId(accountId);
            this.create(subAccountDomain);
            subAccountBaseDomain.setAccountId(accountId);
            subAccountBaseDomain.setSubAccountId(subAccountDomain.getId());
            subAccountBaseDomain.setBalance(BigDecimal.ZERO);
            subAccountBaseDomain.setType(SubAccountTypeEnum.ThirdPartyAccount.getValue());
            subAccountBaseDomain.setStatus(AccountStatusEnum.Valid.getValue());
            subAccountBaseService.create(subAccountBaseDomain);
        }
        subAccountDTO.setSubAccountBaseDomain(subAccountBaseDomain);
        return subAccountDTO;
    }

    @Override
    public SubAccountDTO initBillAccount(Long accountId) {
        SubAccountDTO subAccountDTO = new SubAccountDTO();
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        subAccountBaseDomain.setAccountId(accountId);
        subAccountBaseDomain.setType(SubAccountTypeEnum.BillAccount.getValue());
        subAccountBaseDomain = subAccountBaseService.getSingleByBeanProp(subAccountBaseDomain);
        if (subAccountBaseDomain == null) {
            subAccountBaseDomain = new SubAccountBaseDomain();
            SubAccountDomain subAccountDomain = new SubAccountDomain();
            subAccountDomain.setAccountId(accountId);
            this.create(subAccountDomain);
            subAccountBaseDomain.setAccountId(accountId);
            subAccountBaseDomain.setSubAccountId(subAccountDomain.getId());
            subAccountBaseDomain.setBalance(BigDecimal.ZERO);
            subAccountBaseDomain.setType(SubAccountTypeEnum.BillAccount.getValue());
            subAccountBaseDomain.setStatus(AccountStatusEnum.Valid.getValue());
            subAccountBaseService.create(subAccountBaseDomain);
        }
        subAccountDTO.setSubAccountBaseDomain(subAccountBaseDomain);
        return subAccountDTO;
    }

    @Override
    public SubAccountDTO findBalanceByAccountId(Long accountId) {
        AssertUtil.notNull(accountId, "账户不能为空");
        SubAccountDTO subAccountDTO = new SubAccountDTO();
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        subAccountBaseDomain.setAccountId(accountId);
        subAccountBaseDomain.setType(SubAccountTypeEnum.BalanceAccount.getValue());
        subAccountBaseDomain = subAccountBaseService.getSingleByBeanProp(subAccountBaseDomain);
        subAccountDTO.setSubAccountBaseDomain(subAccountBaseDomain);
        return subAccountDTO;
    }

    @Override
    public SubAccountDTO findBalanceByShopId(Long shopId) {
        AccountDTO accountDTO = accountService.findByShopId(shopId);
        return this.findBalanceByAccountId(accountDTO.getAccountBaseDomain().getAccountId());
    }

    @Override
    public SubAccountDTO findRedPacketByAccountId(Long accountId) {
        AssertUtil.notNull(accountId, "账户不能为空");
        SubAccountDTO subAccountDTO = new SubAccountDTO();
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        subAccountBaseDomain.setAccountId(accountId);
        subAccountBaseDomain.setType(SubAccountTypeEnum.RedPacketAccount.getValue());
        subAccountBaseDomain = subAccountBaseService.getSingleByBeanProp(subAccountBaseDomain);
        subAccountDTO.setSubAccountBaseDomain(subAccountBaseDomain);
        return subAccountDTO;
    }

    @Override
    public SubAccountDTO findRedPacketByShopId(Long shopId) {
        AccountDTO accountDTO = accountService.findByShopId(shopId);
        return this.findRedPacketByAccountId(accountDTO.getAccountBaseDomain().getAccountId());
    }

    @Override
    public SubAccountDTO findDepositByAccountId(Long accountId) {
        AssertUtil.notNull(accountId, "账户不能为空");
        SubAccountDTO subAccountDTO = new SubAccountDTO();
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        subAccountBaseDomain.setAccountId(accountId);
        subAccountBaseDomain.setType(SubAccountTypeEnum.DepositAccount.getValue());
        subAccountBaseDomain = subAccountBaseService.getSingleByBeanProp(subAccountBaseDomain);
        subAccountDTO.setSubAccountBaseDomain(subAccountBaseDomain);
        return subAccountDTO;
    }

    @Override
    public SubAccountDTO findDepositByShopId(Long shopId) {
        AssertUtil.notNull(shopId, "店铺不能为空");
        AccountDTO accountDTO = accountService.findByShopId(shopId);
        return this.findDepositByAccountId(accountDTO.getAccountBaseDomain().getAccountId());
    }

    @Override
    public SubAccountDTO findBillByAccountId(Long accountId) {
        AssertUtil.notNull(accountId, "账户不能为空");
        SubAccountDTO subAccountDTO = new SubAccountDTO();
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        subAccountBaseDomain.setAccountId(accountId);
        subAccountBaseDomain.setType(SubAccountTypeEnum.BillAccount.getValue());
        subAccountBaseDomain = subAccountBaseService.getSingleByBeanProp(subAccountBaseDomain);
        subAccountDTO.setSubAccountBaseDomain(subAccountBaseDomain);
        return subAccountDTO;
    }

    @Override
    public SubAccountDTO findBillByShopId(Long shopId) {
        AssertUtil.notNull(shopId, "店铺不能为空");
        AccountDTO accountDTO = accountService.findByShopId(shopId);
        return this.findBillByAccountId(accountDTO.getAccountBaseDomain().getAccountId());
    }
}
