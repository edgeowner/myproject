package com.zchz.business.dto.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

// 基础车型搜索类
@Document(indexName = "#{indexConfig.prefix}zchz-sku", type = "sku")
public class EsSkuDTO implements Serializable {
    //ID
    @Id
    private String id;
    //车系品牌名称
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String spuBrandName;
    //车系厂商名称
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String spuSupplierName;
    //车系级别名称
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String spuClassName;
    //车系名称
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String spuName;
    //车型年份名称
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String skuYearName;
    //图片
    private String image;
    //车型名称
    @Field(type = FieldType.String, analyzer = "ik", searchAnalyzer = "ik", index = FieldIndex.analyzed)
    private String name;
    //排量
    private String displacement;
    //座位数
    private String seat;
    //变速箱
    private String gearbox;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSpuBrandName() {
        return spuBrandName;
    }

    public void setSpuBrandName(String spuBrandName) {
        this.spuBrandName = spuBrandName;
    }

    public String getSpuSupplierName() {
        return spuSupplierName;
    }

    public void setSpuSupplierName(String spuSupplierName) {
        this.spuSupplierName = spuSupplierName;
    }

    public String getSpuClassName() {
        return spuClassName;
    }

    public void setSpuClassName(String spuClassName) {
        this.spuClassName = spuClassName;
    }

    public String getSpuName() {
        return spuName;
    }

    public void setSpuName(String spuName) {
        this.spuName = spuName;
    }

    public String getSkuYearName() {
        return skuYearName;
    }

    public void setSkuYearName(String skuYearName) {
        this.skuYearName = skuYearName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }
}

