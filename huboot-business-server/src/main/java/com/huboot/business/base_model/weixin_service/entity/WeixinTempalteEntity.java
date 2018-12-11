package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
*微信模板信息表
*/
@Entity
@Table(name = "xiehua_weixin_tempalte")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinTempalteEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //微信节点
    private Integer node ;
    //模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式
    private String templateIdShort ;
    //标题
    private String title ;
    //点击模板跳转页面
    private String clickUrl ;
    //状态（0-无，1-模板保存，2-公众号模板添加完成）
    private StatusEnum status ;
    //应用系统 com.xiehua.framework.common.xenum.SystemEnum
    private Integer system ;
    //是否需要微信授权认证url
    private Integer needAuth;

    public enum StatusEnum {
        none,
        tempalte_save, //1-模板保存
        public_template_save //2-公众号模板添加完成
    }

}

