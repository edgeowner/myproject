package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
*微信公众号发送模板消息日志表
*/
@Entity
@Table(name = "xiehua_weixin_public_template_log")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinPublicTempalteLogEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //所属系统
    private Integer system ;
    //微信公众号审核通过后生成的唯一标识,对外暴露
    private String weixinUid ;
    //用户openid
    private String openId ;
    //微信节点
    private Integer node ;
    //公众号消息模板id
    private String templateId ;
    //消息内容更
    private String messageContent ;
    //返回的消息id
    private String messageId ;
    //发送状态（1-成功，2-失败）
    private SendStatusEnum sendStatus ;
    //错误原因
    private String errorReason ;
    //备注
    private String remark ;

    public enum SendStatusEnum {
        none,
        success,
        failure
    }

}

