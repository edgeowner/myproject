package com.huboot.share.user_service.data;


import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.filter.RequestInfo;
import com.huboot.commons.utils.AppAssert;
import com.huboot.share.user_service.api.dto.UserDetailInfo;
import com.huboot.share.user_service.data.proxy.CacheDataProxy;
import com.huboot.share.user_service.enums.OrganizationSystemEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Component
public class UserCacheData {

    @Autowired
    private CacheDataProxy proxy;

    /**
     * 获取当前登陆用户，非匿名用户
     *
     * @return
     */
    public UserDetailInfo getCurrentUser() {
        Long userId = RequestInfo.getJwtUser().getUserId();
        if (StringUtils.isEmpty(userId)) {
            throw new BizException("用户id不存在");
        }
        UserDetailInfo info = this.getUserDetailInfo(userId);
        AppAssert.notNull(info, "当前用户不存在");
        return info;
    }

    public Long getCurrentUserId() {
        Long userId = getCurrentUser().getUser().getUserId();
        AppAssert.notNull(userId, "当前用户id不存在");
        return userId;
    }

    public Long getCurrentUserOrgId() {
        Long organizationId = getCurrentUser().getUser().getOrganizationId();
        AppAssert.notNull(organizationId, "当前用户所属组织id不存在");
        return organizationId;
    }

    public Long getCurrentUserCompanyId() {
        Long companyId = getCurrentUser().getUser().getCompanyId();
        AppAssert.notNull(companyId, "当前用户所属公司id不存在");
        return companyId;
    }

    //2018版本wyc
    public Long getCurrentUserWycShopId() {
        Long shopId = getCurrentUser().getUser().getWycShopId();
        AppAssert.notNull(shopId, "当前用户所属店铺id不存在");
        return shopId;
    }

    public Long getCurrentUserEmployeeZkShopId() {
        Long shopId = getCurrentUser().getUserEmployee().getZkShopId();
        AppAssert.notNull(shopId, "当前员工所属直客店铺id不存在");
        return shopId;
    }

    public UserDetailInfo.ThirdOpenId getCurrentUserWycThirdOpenId() {
        List<UserDetailInfo.ThirdOpenId> thirdOpenIds = getCurrentUser().getUser().getThirdOpenId();
        if (CollectionUtils.isEmpty(thirdOpenIds)) {
            return new UserDetailInfo.ThirdOpenId();
        } else {
            Optional<UserDetailInfo.ThirdOpenId> thirdOpenIdOptional = thirdOpenIds.stream()
                    .filter(thirdOpenId -> thirdOpenId.getSystem().equals(OrganizationSystemEnum.wyc_driver_miniapp))
                    .findFirst();
            if (thirdOpenIdOptional.isPresent()) {
                return thirdOpenIdOptional.get();
            }
        }
        return null;
    }

    public UserDetailInfo.ThirdOpenId getCurrentUserZkShopThirdOpenId() {
        List<UserDetailInfo.ThirdOpenId> thirdOpenIds = getCurrentUser().getUser().getThirdOpenId();
        if (CollectionUtils.isEmpty(thirdOpenIds)) {
            return new UserDetailInfo.ThirdOpenId();
        } else {
            Optional<UserDetailInfo.ThirdOpenId> thirdOpenIdOptional = thirdOpenIds.stream()
                    .filter(thirdOpenId -> thirdOpenId.getSystem().equals(OrganizationSystemEnum.zk_shop))
                    .findFirst();
            if (thirdOpenIdOptional.isPresent()) {
                return thirdOpenIdOptional.get();
            }
        }
        return null;
    }

    public UserDetailInfo.ThirdOpenId getCurrentUserZkUserThirdOpenId() {
        List<UserDetailInfo.ThirdOpenId> thirdOpenIds = getCurrentUser().getUser().getThirdOpenId();
        if (CollectionUtils.isEmpty(thirdOpenIds)) {
            return new UserDetailInfo.ThirdOpenId();
        } else {
            Optional<UserDetailInfo.ThirdOpenId> thirdOpenIdOptional = thirdOpenIds.stream()
                    .filter(thirdOpenId -> thirdOpenId.getSystem().equals(OrganizationSystemEnum.zk_user))
                    .findFirst();
            if (thirdOpenIdOptional.isPresent()) {
                return thirdOpenIdOptional.get();
            }
        }
        return null;
    }

    public Long getCurrentUserEmployeeOrganizationId() {
        Long organizationId = getCurrentUser().getUserEmployee().getOrganizationId();
        AppAssert.notNull(organizationId, "当前员工所属根组织id不存在");
        return organizationId;
    }

    /**
     * 根据userid获取用户数据信息
     * <p>
     *
     * @param userId
     * @return
     */
    public UserDetailInfo getUserDetailInfo(Long userId) {
        return proxy.getUserDetailInfo(userId);
    }

}
