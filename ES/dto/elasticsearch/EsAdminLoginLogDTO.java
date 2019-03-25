package com.zchz.business.dto.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

// 管理登录日志
@Document(indexName = "#{indexConfig.prefix}zchz-log", type = "adminLoginLog")
public class EsAdminLoginLogDTO implements Serializable {
    //ID
    @Id
    private String id;
    //用户名Id
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String userId;
    //用户名
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String userName;
    //手机号
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String phone;
    //姓名
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String name;
    //类型
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String type;
    //日志描述
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String logDescribe;
    //登录IP
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String loginIp;
    //操作时间
    @Field(type = FieldType.Date, index = FieldIndex.not_analyzed)
    private Date handleTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogDescribe() {
        return logDescribe;
    }

    public void setLogDescribe(String logDescribe) {
        this.logDescribe = logDescribe;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

