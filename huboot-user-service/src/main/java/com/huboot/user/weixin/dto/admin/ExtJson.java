package com.huboot.user.weixin.dto.admin;

import com.huboot.commons.utils.JsonUtil;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/12 0012.
 */
@Data
public class ExtJson {

    private String extAppid;

    private Ext ext;

    @Data
    public static class Ext {
        private String shopSn;
        private String requestDomain;

        public Ext() {
        }

        public Ext(String shopSn, String requestDomain) {
            this.shopSn = shopSn;
            this.requestDomain = requestDomain;
        }
    }

    public String toJson() {
        return JsonUtil.buildNormalMapper().toJson(this);
    }
}
