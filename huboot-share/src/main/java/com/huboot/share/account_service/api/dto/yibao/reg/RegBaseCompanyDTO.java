package com.huboot.share.account_service.api.dto.yibao.reg;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class RegBaseCompanyDTO implements Serializable {

    //商户全称
    @NotBlank(message = "商户全称不能为空")
    private String merFullName;

    //商户简称
    @NotBlank(message = "商户简称不能为空")
    private String merShortName;

    //证件号
    @NotBlank(message = "证件号不能为空")
    private String merCertNo;

    //证件类型 枚举值 企业：“营业执照”或者“统一社会信用代码证”
    //UNI_CREDIT_CODE=统一社会信用代码证；
    //CORP_CODE＝营业执照
    @NotBlank(message = "证件类型不能为空")
    private String merCertType;

    //请按照证件上的真实姓名准确填写
    @NotBlank(message = "法人姓名不能为空")
    private String legalName;

    //法人归属地为“中国大陆” 则必填；法人归属地为“其他” 则不填；
    @NotBlank(message = "法人身份证号不能为空")
    private String legalIdCard;

    //商户联系人姓名
    @NotBlank(message = "商户联系人姓名不能为空")
    private String merContactName;

    //商户联系人手机号
    @NotBlank(message = "商户联系人手机号不能为空")
    private String merContactPhone;

    //商户联系人邮箱
    private String merContactEmail;

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

    //税务登记证编号
    private String taxRegistCert;

    //开户许可证编号
    @NotBlank(message = "开户许可证编号不能为空")
    private String accountLicense;

    //组织机构代码证
    private String orgCode;

    //组织机构代理证有效期
    private String orgCodeExpiry;

    //组织机构代码证是否长期有效
    private String isOrgCodeLong;

    //结算银行账户
    private String cardNo;

    private String headBankCode;

    private String bankCode;

    private String bankProvince;

    private String bankCity;

    private String productInfo;

    private String fileInfo;

    private String notifyUrl;

    private String merAuthorizeType;

    private String businessFunction;
}
