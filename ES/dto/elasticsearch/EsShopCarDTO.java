package com.zchz.business.dto.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

//店铺车辆-搜索
@Document(indexName = "#{indexConfig.prefix}zchz-shop-car", type = "shopCar")
public class EsShopCarDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //唯一标识
    @Id
    private Long id;
    //会员id
    private Long customerId;
    //店铺id
    private Long shopId;
    //车系id
    @Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
    private Long carSeriesId;
    //车型sku id
    @Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
    private Long carId;
    //二级区域ID
    @Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
    private Long cityAreaId;
    //图片路径1
    private String imagePath1;
    //图片路径2
    private String imagePath2;
    //图片路径3
    private String imagePath3;
    //图片路径4
    private String imagePath4;
    //图片路径5
    private String imagePath5;
    //产权证图片路径
    private String propertyCertificate;
    //会员店铺车辆可租状态,enum=customerShopCarRentStatus
    @Field(type = FieldType.Integer, index = FieldIndex.not_analyzed)
    private Integer rentStatus;
    @Field(type = FieldType.Integer, index = FieldIndex.not_analyzed)
    private Integer allotStatus;
    //租用时间开始
    @Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
    private Long startTime;
    //租用时间结束
    @Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
    private Long endTime;
    //价格
    @Field(type = FieldType.Double, index = FieldIndex.not_analyzed)
    private BigDecimal price;
    //店铺门店id
    private Long shopStoreId;
    //发动机号
    private String vehicleIdentificationNumber;
    //车牌号
    private String licensePlateNumber;
    //里程数
    private String mileage;
    //会员店铺车辆颜色类型,enum=customerShopCarColor
    private Integer color;
    //会员店铺车辆是否有倒车雷达,enum=customerShopCarHasPdc
    private Integer hasPdc;
    //会员店铺车辆是否有gps导航,enum=customerShopCarHasGps
    private Integer hasGps;
    //会员店铺车辆保险类型,enum=customerShopCarInsurance
    private String insurance;
    //会员店铺车辆上架状态,enum=customerShopCarMarketStatus
    @Field(type = FieldType.Integer, index = FieldIndex.not_analyzed)
    private Integer marketStatus;
    //上牌时间
    private Date registTime;
    //组id
    private Long groupId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getCarSeriesId() {
        return carSeriesId;
    }

    public void setCarSeriesId(Long carSeriesId) {
        this.carSeriesId = carSeriesId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getImagePath1() {
        return imagePath1;
    }

    public void setImagePath1(String imagePath1) {
        this.imagePath1 = imagePath1;
    }

    public String getImagePath2() {
        return imagePath2;
    }

    public void setImagePath2(String imagePath2) {
        this.imagePath2 = imagePath2;
    }

    public String getImagePath3() {
        return imagePath3;
    }

    public void setImagePath3(String imagePath3) {
        this.imagePath3 = imagePath3;
    }

    public String getImagePath4() {
        return imagePath4;
    }

    public void setImagePath4(String imagePath4) {
        this.imagePath4 = imagePath4;
    }

    public String getImagePath5() {
        return imagePath5;
    }

    public void setImagePath5(String imagePath5) {
        this.imagePath5 = imagePath5;
    }

    public String getPropertyCertificate() {
        return propertyCertificate;
    }

    public void setPropertyCertificate(String propertyCertificate) {
        this.propertyCertificate = propertyCertificate;
    }

    public Integer getRentStatus() {
        return rentStatus;
    }

    public void setRentStatus(Integer rentStatus) {
        this.rentStatus = rentStatus;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getShopStoreId() {
        return shopStoreId;
    }

    public void setShopStoreId(Long shopStoreId) {
        this.shopStoreId = shopStoreId;
    }

    public String getVehicleIdentificationNumber() {
        return vehicleIdentificationNumber;
    }

    public void setVehicleIdentificationNumber(String vehicleIdentificationNumber) {
        this.vehicleIdentificationNumber = vehicleIdentificationNumber;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public void setLicensePlateNumber(String licensePlateNumber) {
        this.licensePlateNumber = licensePlateNumber;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public Integer getHasPdc() {
        return hasPdc;
    }

    public void setHasPdc(Integer hasPdc) {
        this.hasPdc = hasPdc;
    }

    public Integer getHasGps() {
        return hasGps;
    }

    public void setHasGps(Integer hasGps) {
        this.hasGps = hasGps;
    }

    public String getInsurance() {
        return insurance;
    }

    public void setInsurance(String insurance) {
        this.insurance = insurance;
    }

    public Integer getMarketStatus() {
        return marketStatus;
    }

    public void setMarketStatus(Integer marketStatus) {
        this.marketStatus = marketStatus;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getCityAreaId() {
        return cityAreaId;
    }

    public void setCityAreaId(Long cityAreaId) {
        this.cityAreaId = cityAreaId;
    }

    public Integer getAllotStatus() {
        return allotStatus;
    }

    public void setAllotStatus(Integer allotStatus) {
        this.allotStatus = allotStatus;
    }
}

