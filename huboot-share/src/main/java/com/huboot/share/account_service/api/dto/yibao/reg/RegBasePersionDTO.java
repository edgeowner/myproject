package com.huboot.share.account_service.api.dto.yibao.reg;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RegBasePersionDTO implements Serializable {

    //请按照证件上的真实姓名准确填写
    @NotBlank(message = "法人姓名不能为空")
    private String legalName;

    //法人归属地为“中国大陆” 则必填；法人归属地为“其他” 则不填；
    @NotBlank(message = "法人身份证号不能为空")
    private String legalIdCard;

    //商户法人手机
    @NotBlank(message = "商户法人手机不能为空")
    private String merLegalPhone;

    //法人邮箱
    private String merLegalEmail;

    //商户一级分类
    private String merLevel1No;

    //商户二级分类
    private String merLevel2No;

    //商户所在省
    @NotBlank(message = "商户所在省不能为空")
    private String merProvince;

    //商户所在市
    @NotBlank(message = "商户所在市不能为空")
    private String merCity;

    //商户所在区
    @NotBlank(message = "商户所在区不能为空")
    private String merDistrict;

    //商户所在详细地址
    @NotBlank(message = "商户所在详细地址不能为空")
    private String merAddress;

    //商户经营范围
    private String merScope;

    //结算银行卡号
    @NotBlank(message = "结算银行卡号不能为空")
    private String cardNo;

    //开户总行编码
    @NotBlank(message = "开户总行编码不能为空")
    private String headBankCode;

    //开户市
    private String bankCity;

    //开户支行编码
    private String bankCode;

    //开户省
    private String bankProvince;

    //开通产品信息
    private String productInfo;

    //资质影印件信息
    @NotBlank(message = "资质影印件信息不能为空")
    private String fileInfo;

    //业务功能
    private String businessFunction;

    //回调地址
    private String notifyUrl;

    //授权类型
    private String merAuthorizeType;

    //商户简称
    private String merShortName;

}
