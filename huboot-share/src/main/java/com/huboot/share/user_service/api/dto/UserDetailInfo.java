package com.huboot.share.user_service.api.dto;

import com.huboot.share.user_service.enums.ThirdPlatformEnum;
import com.huboot.share.user_service.enums.OrganizationSystemEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Data
public class UserDetailInfo implements Serializable {

    private User user = new User();
    private UserPersonal userPersonal = new UserPersonal();
    private UserEmployee userEmployee = new UserEmployee();

    //C端用户信息,来源于UserEntity
    @Data
    public static class User implements Serializable {
        @ApiModelProperty("用户ID--固定")
        private Long userId;
        @ApiModelProperty("用户名")
        private String username;
        @ApiModelProperty("用户的根组织id-固定")
        private Long organizationId;
        //第三方openid-json数组
        private List<ThirdOpenId> thirdOpenId = new ArrayList<>();
        @ApiModelProperty("用户姓名(非实名认证)--动态刷新")
        private String name;
        @ApiModelProperty("用户手机--动态刷新")
        private String phone;
        //产品坑:2018版网约车--c端user属于公司的organizationId:
        //2019版网约车--c端user属于平台的organizationId;
        @ApiModelProperty("公司id--动态刷新")
        private Long companyId;
        @ApiModelProperty("公司sn,动态刷新")
        private String companySn;
        @ApiModelProperty("2018版本网约车店铺id--动态刷新")
        private Long wycShopId;
        @ApiModelProperty("2018版本网约车店铺sn--动态刷新")
        private String wycShopSn;
    }

    //个人信息,来源于UserPersonalEntity
    @Data
    public static class UserPersonal implements Serializable {
        @ApiModelProperty("用户姓名--动态刷新")
        private String name;
        @ApiModelProperty("用户身份证号--动态刷新")
        private String idCard;
    }

    //B端员工信息,来源于UserEmployeeEntity
    //2019版网约车--;
    //2019版直客--;
    @Data
    public static class UserEmployee implements Serializable {
        @ApiModelProperty("公司组织id--动态刷新")
        private Long organizationId;
        @ApiModelProperty("公司id--动态刷新")
        private Long companyId;
        @ApiModelProperty("公司sn,动态刷新")
        private String companySn;
        @ApiModelProperty("网约车店铺id--动态刷新")
        private Long wycShopId;
        @ApiModelProperty("网约车店铺sn--动态刷新")
        private String wycShopSn;
        @ApiModelProperty("版直客商家端店铺id---动态刷新")
        private Long zkShopId;
        @ApiModelProperty("版直客商家端店铺sn---动态刷新")
        private String zkShopSn;
        @ApiModelProperty("员工小程序openID--动态刷新")
        private String miniappOpenId;
    }

    @Data
    public static class ThirdOpenId implements Serializable {
        @ApiModelProperty("平台类型--动态刷新")
        private ThirdPlatformEnum thirdName;
        @ApiModelProperty("appId--动态刷新")
        private String appId;
        @ApiModelProperty("值--动态刷新")
        private String value;
        @ApiModelProperty("所属系统--动态刷新")
        private OrganizationSystemEnum system;
    }
}
