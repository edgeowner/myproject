package com.huboot.user.organization.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 经纪人推广
 */
@Data
public class OrganizationShopMicropageAgentDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("成为推广者页面配置")
    private Agent agent;
    @ApiModelProperty("成为司机页面配置")
    private Driver driver;

    @Data
    public static class Agent implements Serializable {

        private static final long serialVersionUID = 1L;
        @ApiModelProperty("宣传图")
        private String imagePath;
        @ApiModelProperty("活动细则")
        private String content;
    }

    @Data
    public static class Driver implements Serializable {

        private static final long serialVersionUID = 1L;
        @ApiModelProperty("宣传图")
        private String imagePath;
        @ApiModelProperty("公司名称")
        private String company;
        @ApiModelProperty("公司联系人")
        private String contact;
        @ApiModelProperty("公司联系方式")
        private String phone;
        @ApiModelProperty("公司联系地址")
        private String address;



    }


}

