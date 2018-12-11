package com.huboot.business.base_model.weixin_service.entity;

import java.io.Serializable;

import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.jpa.AbstractEntity;
import com.huboot.business.common.utils.JsonUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;

/**
*微信用户关注公众号关系表
*/
@Entity
@Table(name = "xiehua_weixin_user_public_map")
@DynamicInsert
@DynamicUpdate
@Data
public class WeixinUserPublicMapEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //微信openId
    private String openId ;
    //微信公众号审核通过后生成的唯一标识,对外暴露
    private String weixinUid ;
    //只有在用户将公众号绑定到微信开放平台帐号后
    private String unionid ;
    //小程序sessionkey
    private String sessionKey;
    //扩展
    private String extendJson;

    @Data
    public static class ExtendEntity {
        //扩展
        private String eventKey;

        public String toJson() {
            try {
                return JsonUtils.toJsonString(this);
            } catch (Exception e) {
                throw new BizException("json转换异常");
            }
        }
    }

    public ExtendEntity getExtendEntity() {
        if (null == extendJson || "".equals(extendJson)) {
            return new ExtendEntity();
        } else {
            try {
                return JsonUtils.fromJson(extendJson, ExtendEntity.class);
            } catch (Exception e) {
                throw new BizException("json解析异常");
            }
        }

    }

}

