package com.huboot.user.organization.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 推广页完整内容
 */
@Data
public class OrganizationShopMicropagePromotionDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty("模块一")
    private List<FirstBlock> firstBlockList = new ArrayList<>();
    @ApiModelProperty("模块二")
    private List<SecondBlock> secondBlockList = new ArrayList<>();
    @ApiModelProperty("模块三")
    private List<ThirdBlock> thirdBlockList = new ArrayList<>();
    @ApiModelProperty("模块四")
    private FourthBlock fourthBlock;

    @Data
    public static class FirstBlock implements Serializable {

        private static final long serialVersionUID = 1L;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("内容")
        private String content;
    }

    @Data
    public static class SecondBlock implements Serializable {

        private static final long serialVersionUID = 1L;
        @ApiModelProperty("标题")
        private String model;
        @ApiModelProperty("描述")
        private String description;
        @ApiModelProperty("押金")
        private String deposit;
        @ApiModelProperty("租金")
        private String rent;
        @ApiModelProperty("排量")
        private String displacement;
        @ApiModelProperty("油耗")
        private String fuel;
        @ApiModelProperty("图片")
        private List<String> imageList;
    }

    @Data
    public static class ThirdBlock implements Serializable {

        private static final long serialVersionUID = 1L;
        @ApiModelProperty("标题")
        private String title;
        @ApiModelProperty("内容")
        private String content;
    }

    @Data
    public static class FourthBlock implements Serializable {

        private static final long serialVersionUID = 1L;
        @ApiModelProperty("联系电话")
        private List<Contact> contactList = new ArrayList<>();
        @ApiModelProperty("公司地址")
        private String address;

        @Data
        public static class Contact implements Serializable {

            private static final long serialVersionUID = 1L;
            @ApiModelProperty("联系人")
            private String name;
            @ApiModelProperty("电话")
            private String phone;
        }
    }
}

