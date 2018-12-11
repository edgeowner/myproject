package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
*小程序模板使用关系表
*/
@Entity
@Table(name = "xiehua_weixin_nimiapp_template_map")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinMimiappTemplateMapEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //微信uid
    private String weixinUid ;
    //代码模板表id
    private Integer codeTempalteId ;
    //小程序提交代码扩展配置参数
    private String commitCodeParameter ;
    //提交代码结果
    private String commitCodeResult ;
    //提交审核结果
    private String commitCheckResult ;
    //审核编号
    private String auditId ;
    //审核结果
    private String auditResult ;
    //状态
    private StatusEnum status ;
    //审核通过之后是否自动发布
    private Integer releaseAfterAudit ;
    //发布结果
    private String releaseResult ;


    public enum StatusEnum {
        none("--"),
        codeCommitSuccess("代码提交成功"),//1提交代码成功
        codeCommitFailure("代码提交失败"),//2提交代码失败
        codeCheckWait("审核中"),//3待审核（提交审核成功）
        codeChecCommitkFailure("提交审核失败"),//4提交审核失败
        codeCheckSuccess("审核成功"),//5审核成功
        codeCheckFailure("审核失败"),//6审核失败
        codeReleaseSuccess("发布成功"),//7发布成功
        codeReleaseFailure("发布失败");//8发布失败

        private String showName;

        StatusEnum(String showName) {
            this.showName = showName;
        }

        public String getShowName() {
            return showName;
        }

        public static StatusEnum valueOf(int value) {
            for (StatusEnum s : StatusEnum.values()) {
                if (s.ordinal() == value)
                    return s;
            }
            throw new IllegalArgumentException(String.format("值%s不能转换成枚举", value));
        }
    }

}

