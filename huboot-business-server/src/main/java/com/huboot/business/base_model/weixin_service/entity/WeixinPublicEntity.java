package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
*商家微信公众号配置信息表
*/
@Entity
@Table(name = "xiehua_weixin_public")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinPublicEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //微信公众号审核通过后生成的唯一标识,对外暴露
    private String weixinUid ;
    //公众号appid
    private String appId ;
    //公众号secret
    private String secret ;
    //公众号token
    private String token ;
    //公众号aes_key
    private String aesKey ;
    //原始id
    private String originalId ;
    //refresh_token
    private String refreshToken;
    //状态（0-待验证，1-服务器验证通过，2-账号验证通过，3-服务器验证失败）
    private StatusEnum status;
    //所属系统
    private Integer system;
    //备注
    private String remark;
    //二维码图片路径
    private String qrcodeUrl;
    //授权方昵称
    private String nickName;
    //授权方头像
    private String headImg;
    //公众号的主体名称
    private String principalName;
    //授权方公众号所设置的微信号，可能为空
    private String alias;
    //账号介绍
    private String signature;
    //功能
    private String funcInfo;
    //类型
    private TypeEnum  type;
    //长按保存二维码
    private String saveQrcodeUrl;
    //绑定类型
    private BindTypeEnum bindType;

    public enum StatusEnum {
        waitValid, //0
        validServerSuccess, //1
        //三方平台配置状态
        authorizedSuccess, //2
        unAuthorized,//3
    }

    public enum BindTypeEnum {
        none,
        developer, //1 -开发者模式
        weixin3open//2 -授权模式
    }

    public enum TypeEnum {
        none,
        pubapp, //1-公众号
        miniapp; // 2- 小程序

        public static TypeEnum get(Integer value) {
            for(TypeEnum typeEnum : TypeEnum.values()) {
                if(typeEnum.ordinal() == value) {
                    return typeEnum;
                }
            }
            return none;
        }
    }
}

