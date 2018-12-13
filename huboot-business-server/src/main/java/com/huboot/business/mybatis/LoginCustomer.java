package com.huboot.business.mybatis;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 内管登录信息
 *
 * @author Tory.li
 * @create 2017-02-07 17:20
 **/
public class LoginCustomer implements Serializable {
    private static final long serialVersionUID = 1L;

    // 客户ID
    private Long customerId;
    // 店铺id--提交公司信息后生成，要登陆后更新
    private Long shopId;
    // 店铺名称--提交公司信息后生成，要登陆后更新
    private String shopName;
    //会员店铺状态,--提交公司信息后生成，要登陆后更新
    private Integer shopStatus;
    //个人认证状态
    private Integer auditStatus;
    //公司ID--提交公司信息后生成，要登陆后更新
    private Long companyId;
    //购物车id
    private Long cartId;
    // 区域ID（二级)主要用于同城约束--提交公司信息后生成，要登陆后更新
    private Long cityAreaId;
    //区域名称（二级)主要用于同城约束--提交公司信息后生成，要登陆后更新
    private String cityName;
    //省份名称（一级)
    private String provinceName;
    //首页待办事项展开状态，1：是，0：否
    private Integer indexUnfoldStatus;
    //会员店铺职位类型：1-店长
    private Integer positionType;
    //头像地址
    private String photoPath;
    @ApiModelProperty(value = "")
    private Integer invite;
    @ApiModelProperty("公司认证状态")
    private Integer compayAndMissiveStatus;
    @ApiModelProperty("公司注册类型")
    private Integer regType;

    private Long accountId;

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public Integer getRegType() {
        return regType;
    }

    public void setRegType(Integer regType) {
        this.regType = regType;
    }

    public Integer getCompayAndMissiveStatus() {
        return compayAndMissiveStatus;
    }

    public void setCompayAndMissiveStatus(Integer compayAndMissiveStatus) {
        this.compayAndMissiveStatus = compayAndMissiveStatus;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Integer getInvite() {
        return invite;
    }

    public void setInvite(Integer invite) {
        this.invite = invite;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public Long getCityAreaId() {
        return cityAreaId;
    }

    public void setCityAreaId(Long cityAreaId) {
        this.cityAreaId = cityAreaId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getShopStatus() {
        return shopStatus;
    }

    public void setShopStatus(Integer shopStatus) {
        this.shopStatus = shopStatus;
    }

    public Integer getIndexUnfoldStatus() {
        return indexUnfoldStatus;
    }

    public void setIndexUnfoldStatus(Integer indexUnfoldStatus) {
        this.indexUnfoldStatus = indexUnfoldStatus;
    }

    public Integer getPositionType() {
        return positionType;
    }

    public void setPositionType(Integer positionType) {
        this.positionType = positionType;
    }
}
