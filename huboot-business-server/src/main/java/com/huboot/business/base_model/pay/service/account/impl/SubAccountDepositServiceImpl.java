package com.huboot.business.base_model.pay.service.account.impl;


import com.huboot.business.base_model.pay.PagerUtils;
import com.huboot.business.base_model.pay.dao.account.ISubAccountDepositDao;
import com.huboot.business.base_model.pay.domain.account.AccountBaseDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountBaseDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountDepositDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountDomain;
import com.huboot.business.base_model.pay.dto.account.BuyerBaseDTO;
import com.huboot.business.base_model.pay.enums.AccountStatusEnum;
import com.huboot.business.base_model.pay.enums.SubAccountTypeEnum;
import com.huboot.business.base_model.pay.service.account.IAccountBaseService;
import com.huboot.business.base_model.pay.service.account.ISubAccountBaseService;
import com.huboot.business.base_model.pay.service.account.ISubAccountDepositService;
import com.huboot.business.base_model.pay.service.account.ISubAccountService;
import com.huboot.business.common.utils.JsonUtils;
import com.huboot.business.mybatis.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *账户中心-押金账户扩展ServiceImpl
 */
@Service("subAccountDepositServiceImpl")
public class SubAccountDepositServiceImpl extends AbstractBaseService <SubAccountDepositDomain, Long> implements ISubAccountDepositService {
    @Autowired
    private ISubAccountBaseService subAccountBaseService;

    @Autowired
    private IAccountBaseService accountBaseService;
    @Autowired
    private ISubAccountService subAccountService;
    @Autowired
    private ISubAccountDepositDao subAccountDepositDao;
    @Autowired
    public SubAccountDepositServiceImpl(ISubAccountDepositDao subAccountDepositDao ) {
        super(subAccountDepositDao);
    }

    /*@Override
    @Transactional
    public Long create(SubAccountBaseSaveVO vo) throws ApiException {

        ShopDomain sellershop  = shopService.find(SecurityContextHelper.getShopIdIfExits());
        if(!CustomerShopBussinessTypeEnum.SelfEmployed.getValue().equals(sellershop.getBusinessType())){
            throw new ApiException("非自营店铺不可添加商户");
        }

        if(!StringUtils.hasText(vo.getShopname())){
            throw new ApiException("店铺名称不能为空");
        }

        ShopDomain shopDomain = new ShopDomain();
        shopDomain.setName(vo.getShopname());
        ShopDomain buyershop  = shopService.getSingleByBeanProp(shopDomain);
        if(null == buyershop){
            throw new ApiException("店铺不存在,请确认后再提交");
        }
        //判断子账户类型作不同处理
        if(vo.getType().equals(SubAccountTypeEnum.DepositAccount.getValue())){
            //创建押金账户
            //启用表明通过3种认证
            if(buyershop.getStatus().equals(CustomerShopStatusEnum.Disable.getValue())){
                //认证未通过的店铺也视为不存在,即使后台已生成了待认证的店铺
                throw new ApiException("店铺不存在,请确认后再提交");
            }
            CompanyDomain companyDomain = companyService.findByShopId(buyershop.getId());
            if(companyDomain != null) {
                if(!CustomerCompanyInfoAuditStatusEnum.Audited.getValue().equals(companyDomain.getInfoAuditStatus())
                        || !CustomerCompanyMissiveAuditStatusEnum.Audited.getValue().equals(companyDomain.getMissiveAuditStatus())) {
                    throw new ApiException("店铺不存在,请确认后再提交");
                }
            }
        }

        if(buyershop.getId().equals(SecurityContextHelper.getShopIdIfExits())){
            throw new ApiException("不可添加自己为商户");
        }
        if(CustomerShopBussinessTypeEnum.SelfEmployed.getValue().equals(buyershop.getBusinessType())){
            throw new ApiException("不可添加自营店铺为商户");
        }


        //默认联系人
        ShopContactDomain shopContactDomain = shopContactService.findDefaultContactByShopId(buyershop.getId());
        if(null == shopContactDomain){
            throw new ApiException("店铺不存在默认联系人");
        }


        QueryCondition queryCondition = new QueryCondition();
        //queryCondition.addConditionByColName("sad.seller_shop_id", QueryOperatorEnum.eq, SecurityContextHelper.getShopIdIfExits());
        queryCondition.addConditionByColName("ab.shop_id", QueryOperatorEnum.eq, buyershop.getId());
        Pager<BuyerBaseDTO> dtoPager = this.findBuyerDepositBySeller(queryCondition);
        List<BuyerBaseDTO> dtolist = dtoPager.getPageItems();
        if(!CollectionUtils.isEmpty(dtolist)){
            throw new ApiException("此商户已被添加");
        }

        //创建zac_sub_account
        AccountBaseDomain accountBaseDomain = new AccountBaseDomain();
        accountBaseDomain.setShopId(buyershop.getId());
        AccountBaseDomain account = accountBaseService.getSingleByBeanProp(accountBaseDomain);
        if(account.getAccountId() == null){
            throw new ApiException("此店铺未初始化主账户");
        }
        SubAccountDomain subAccountDomain = new SubAccountDomain();
        subAccountDomain.setAccountId(account.getAccountId());
        Long subAccountId = subAccountService.create(subAccountDomain);

        //创建zac_sub_account_base
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        AssertUtil.notNull(subAccountId, "subAccountId为空");
        subAccountBaseDomain.setAccountId(account.getAccountId());
        subAccountBaseDomain.setSubAccountId(subAccountId);
        subAccountBaseDomain.setStatus(AccountStatusEnum.Invalid.getValue());
        subAccountBaseDomain.setType(vo.getType());
        Long subAccountBaseId = subAccountBaseService.create(subAccountBaseDomain);

        //创建zac_sub_account_deposit
        SubAccountDepositDomain subAccountDepositDomain = new SubAccountDepositDomain();
        subAccountDepositDomain.setSubAccountId(subAccountId);
        subAccountDepositDomain.setSellerShopId(SecurityContextHelper.getShopIdIfExits());
        //查询卖家店铺名称
        subAccountDepositDomain.setSellerShopName(sellershop.getName());

        return this.create(subAccountDepositDomain);
    }*/

    @Override
    public SubAccountDepositDomain findBySubAccountId(Long subAccountId) {
        SubAccountDepositDomain subAccountDepositDomain = new SubAccountDepositDomain();
        subAccountDepositDomain.setSubAccountId(subAccountId);
        subAccountDepositDomain = this.getSingleByBeanProp(subAccountDepositDomain);
        return subAccountDepositDomain;
    }


    @Override
    public Pager<BuyerBaseDTO> findBuyerDepositBySeller(QueryCondition queryCondition) {

        Pager<Map<String, Object>> mapPager = subAccountDepositDao.findBuyerDepositBySeller(queryCondition);
        Pager<BuyerBaseDTO> buyerDepositDTOPager = PagerUtils.toPager(mapPager, BuyerBaseDTO.class);
        buyerDepositDTOPager.setPageItems(new ArrayList<BuyerBaseDTO>());
        List<Map<String, Object>> mapList = mapPager.getPageItems();
        if (!CollectionUtils.isEmpty(mapList)) {
            for (Map<String, Object> map : mapList) {
                BuyerBaseDTO buyerBaseDTO = (BuyerBaseDTO) JsonUtils.fromMapToObject(map,BuyerBaseDTO.class);
                buyerDepositDTOPager.getPageItems().add(buyerBaseDTO);
            }
        }
        return buyerDepositDTOPager;
    }

    @Override
    public Boolean checkBuyerAndSellerRela(Long buyerSubAccountId, Long sellerShopId) {
        //检查关系
        QueryCondition queryCondition = new QueryCondition();
        queryCondition.addConditionByColName("sad.seller_shop_id", QueryOperatorEnum.eq, sellerShopId);
        queryCondition.addConditionByColName("saba.sub_account_id", QueryOperatorEnum.eq,buyerSubAccountId);
        Pager<BuyerBaseDTO> dtoPager = this.findBuyerDepositBySeller(queryCondition);
        if (CollectionUtils.isEmpty(dtoPager.getPageItems())) {
            return false;
        }
        return true;
    }

    @Override
    public SubAccountDepositDomain findByShopId(Long shopId) {
        SubAccountBaseDomain subbase = subAccountBaseService.findAccountByShopIdWithType(shopId, SubAccountTypeEnum.DepositAccount.getValue());
        if(subbase == null) {
            return null;
        }
        SubAccountDepositDomain subdeposit = new SubAccountDepositDomain();
        subdeposit.setSubAccountId(subbase.getSubAccountId());
        return this.getFirstByBeanProp(subdeposit);
    }
}
