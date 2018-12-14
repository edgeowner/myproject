package com.huboot.business.base_model.pay.service.account.impl;


import com.huboot.business.base_model.pay.PagerUtils;
import com.huboot.business.base_model.pay.dao.account.ISubAccountBaseDao;
import com.huboot.business.base_model.pay.domain.account.AccountBaseDomain;
import com.huboot.business.base_model.pay.domain.account.SubAccountBaseDomain;
import com.huboot.business.base_model.pay.dto.account.BuyerBaseDTO;
import com.huboot.business.base_model.pay.dto.account.SubAccountBaseDTO;
import com.huboot.business.base_model.pay.service.account.IAccountBaseService;
import com.huboot.business.base_model.pay.service.account.ISubAccountBaseService;
import com.huboot.business.common.utils.JsonUtils;
import com.huboot.business.mybatis.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 账户中心-子账户基础信息ServiceImpl
 */
@Service("subAccountBaseServiceImpl")
public class SubAccountBaseServiceImpl extends AbstractBaseService<SubAccountBaseDomain, Long> implements ISubAccountBaseService {

    @Autowired
    private ISubAccountBaseDao subAccountBaseDao;
    @Autowired
    private IAccountBaseService accountBaseService;

    @Autowired
    public SubAccountBaseServiceImpl(ISubAccountBaseDao subAccountBaseDao) {
        super(subAccountBaseDao);
    }

    @Override
    public Pager<SubAccountBaseDomain> findByAdminQueryForPager(QueryCondition queryCondition) {
        return subAccountBaseDao.findForPager(queryCondition, "findByAdminQueryForPager");
    }

    @Override
    public SubAccountBaseDomain findBySubAccountId(Long subAccountId) {
        SubAccountBaseDomain subAccountBaseDomain = new SubAccountBaseDomain();
        subAccountBaseDomain.setSubAccountId(subAccountId);
        subAccountBaseDomain = this.getSingleByBeanProp(subAccountBaseDomain);
        return subAccountBaseDomain;
    }

    @Override
    public void updateBalanceForDetail(SubAccountBaseDTO subAccountBaseDTO) {
        AssertUtil.notNull(subAccountBaseDTO);
        AssertUtil.notNull(subAccountBaseDTO.getSubAccountId());
        AssertUtil.notNull(subAccountBaseDTO.getBalance());
        AssertUtil.notNull(subAccountBaseDTO.getVersion());
        SubAccountBaseDomain subAccountBaseDomain = this.findBySubAccountId(subAccountBaseDTO.getSubAccountId());
        SubAccountBaseDomain newSubAccountBaseDomain = new SubAccountBaseDomain();
        newSubAccountBaseDomain.setId(subAccountBaseDomain.getId());
        newSubAccountBaseDomain.setBalance(subAccountBaseDTO.getBalance());
        newSubAccountBaseDomain.setVersion(subAccountBaseDTO.getVersion());
        Integer updateCount = this.merge(newSubAccountBaseDomain);
        if (updateCount == 0) {
            throw new ApiException(ErrorCodeEnum.ParamsError, "账户余额更新失败");
        }
    }

    /**
     * @param accountId
     * @param type
     * @return
     * @throws ApiException
     */
    @Override
    public SubAccountBaseDomain findAccountByAccountIdWithType(Long accountId, Integer type) throws ApiException {
        AssertUtil.notNull(accountId, "主账户id不能为空");
        AssertUtil.notNull(type, "子账户类型不能为空");
        SubAccountBaseDomain subbase = new SubAccountBaseDomain();
        subbase.setAccountId(accountId);
        subbase.setType(type);
        return this.getFirstByBeanProp(subbase);
    }

    /**
     * 根据店铺id和类型查询子账户信息
     *
     * @param shopId
     * @param type
     * @return
     * @throws ApiException
     */
    @Override
    public SubAccountBaseDomain findAccountByShopIdWithType(Long shopId, Integer type) throws ApiException {
        AssertUtil.notNull(shopId, "店铺id不能为空");
        AssertUtil.notNull(type, "账户类型不能为空");
        AccountBaseDomain account = new AccountBaseDomain();
        account.setShopId(shopId);
        account = accountBaseService.getFirstByBeanProp(account);
        if (account == null) {
            return null;
        }
        return this.findAccountByAccountIdWithType(account.getAccountId(), type);
    }

    @Override
    public Pager<BuyerBaseDTO> findSubAccountBaseByShopIdandType(QueryCondition queryCondition) {

        Pager<Map<String, Object>> mapPager = subAccountBaseDao.findSubAccountBaseByShopIdandType(queryCondition);
        Pager<BuyerBaseDTO> buyerDepositDTOPager = PagerUtils.toPager(mapPager, BuyerBaseDTO.class);
        buyerDepositDTOPager.setPageItems(new ArrayList<BuyerBaseDTO>());
        List<Map<String, Object>> mapList = mapPager.getPageItems();
        if (!CollectionUtils.isEmpty(mapList)) {
            for (Map<String, Object> map : mapList) {
                BuyerBaseDTO buyerBaseDTO = (BuyerBaseDTO) JsonUtils.fromMapToObject(map, BuyerBaseDTO.class);
                buyerDepositDTOPager.getPageItems().add(buyerBaseDTO);
            }
        }
        return buyerDepositDTOPager;
    }

    @Override
    public Pager<SubAccountBaseDomain> findBalanceOrRedPacketAccountForPager(QueryCondition queryCondition) throws ApiException {
        return subAccountBaseDao.findForPager(queryCondition, "findBalanceOrRedPacketAccountForPager");
    }

    @Override
    public void updateStatus(Long subAccountId, Integer status) {
        SubAccountBaseDomain subAccountBaseDomain = this.findBySubAccountId(subAccountId);
        SubAccountBaseDomain newSubAccountBaseDomain = new SubAccountBaseDomain();
        newSubAccountBaseDomain.setId(subAccountBaseDomain.getId());
        newSubAccountBaseDomain.setStatus(status);
        this.merge(newSubAccountBaseDomain);
    }
}
