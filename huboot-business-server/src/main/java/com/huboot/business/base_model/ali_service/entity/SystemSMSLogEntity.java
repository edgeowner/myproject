package com.huboot.business.base_model.ali_service.entity;

import com.huboot.business.common.jpa.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
*短信log-
*/
@Entity
@Table(name = "zb_system_sms_log")
@DynamicInsert
@DynamicUpdate
@Data
public class SystemSMSLogEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //短信节点
    private Integer node ;
    //手机号
    private String phone ;
    //发送体
    private String smsRequest ;
    //响应体
    private String smsResponse ;
    //所属系统
    private Integer system ;
    //状态:0初始化,1,成功,2失败
    private SystemSMSLogStatusEnum status;
    //店铺标识
    private String shopUid;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public enum SystemSMSLogStatusEnum {

        init(0,"初始化"),success(1,"成功"),fail(2,"失败");

        private Integer value;

        private String showName;

    }

}

