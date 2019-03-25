package com.zchz.business.dto.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

// 管理操作日志
@Document(indexName = "#{indexConfig.prefix}zchz-log", type = "manageLog")
public class EsManageLogDTO implements Serializable {
    //ID
    @Id
    private String id;
    //操作模块
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String handleModule;
    //操作内容
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String handleContent;
    //操作人手机号
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String handlerPhone;
    //操作人姓名
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String handlerName;
    //操作时间
    @Field(type = FieldType.Date, index = FieldIndex.not_analyzed)
    private Date handleTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHandleModule() {
        return handleModule;
    }

    public void setHandleModule(String handleModule) {
        this.handleModule = handleModule;
    }

    public String getHandleContent() {
        return handleContent;
    }

    public void setHandleContent(String handleContent) {
        this.handleContent = handleContent;
    }

    public String getHandlerPhone() {
        return handlerPhone;
    }

    public void setHandlerPhone(String handlerPhone) {
        this.handlerPhone = handlerPhone;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public Date getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(Date handleTime) {
        this.handleTime = handleTime;
    }
}

