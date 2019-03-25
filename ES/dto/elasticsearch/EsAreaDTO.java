package com.zchz.business.dto.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

//区域-搜索
@Document(indexName = "#{indexConfig.prefix}zchz-area", type = "area")
public class EsAreaDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    //唯一标识
    @Id
    private Long id;
    //地区名
    private String name;
    //全称
    private String fullName;
    //路径
    private String path;
    //父地区
    @Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
    private Long parentId;
    //车牌简称
    private String licensePlateShort;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return this.parentId;
    }

    public String getLicensePlateShort() {
        return licensePlateShort;
    }

    public void setLicensePlateShort(String licensePlateShort) {
        this.licensePlateShort = licensePlateShort;
    }
}

