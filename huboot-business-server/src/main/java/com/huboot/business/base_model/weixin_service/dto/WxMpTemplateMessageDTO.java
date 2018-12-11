package com.huboot.business.base_model.weixin_service.dto;

import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;

import java.beans.ConstructorProperties;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hqr on 2018/11/26.
 */
public class WxMpTemplateMessageDTO implements Serializable{

    private static final long serialVersionUID = 5063374783759519418L;
    private String toUser;
    private String templateId;
    private String url;
    private WxMpTemplateMessageDTO.MiniProgram miniProgram;
    private List<WxMpTemplateData> data = new ArrayList();

    public WxMpTemplateMessageDTO addData(WxMpTemplateData datum) {
        if(this.data == null) {
            this.data = new ArrayList();
        }

        this.data.add(datum);
        return this;
    }

    public String toJson() {
        return WxMpGsonBuilder.INSTANCE.create().toJson(this);
    }

    public static WxMpTemplateMessageDTO.WxMpTemplateMessageBuilder builder() {
        return new WxMpTemplateMessageDTO.WxMpTemplateMessageBuilder();
    }

    public String getToUser() {
        return this.toUser;
    }

    public String getTemplateId() {
        return this.templateId;
    }

    public String getUrl() {
        return this.url;
    }

    public WxMpTemplateMessageDTO.MiniProgram getMiniProgram() {
        return this.miniProgram;
    }

    public List<WxMpTemplateData> getData() {
        return this.data;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMiniProgram(WxMpTemplateMessageDTO.MiniProgram miniProgram) {
        this.miniProgram = miniProgram;
    }

    public void setData(List<WxMpTemplateData> data) {
        this.data = data;
    }

    public WxMpTemplateMessageDTO() {
    }

    @ConstructorProperties({"toUser", "templateId", "url", "miniProgram", "data"})
    public WxMpTemplateMessageDTO(String toUser, String templateId, String url, WxMpTemplateMessageDTO.MiniProgram miniProgram, List<WxMpTemplateData> data) {
        this.toUser = toUser;
        this.templateId = templateId;
        this.url = url;
        this.miniProgram = miniProgram;
        this.data = data;
    }

    public static class WxMpTemplateMessageBuilder {
        private String toUser;
        private String templateId;
        private String url;
        private WxMpTemplateMessageDTO.MiniProgram miniProgram;
        private List<WxMpTemplateData> data;

        WxMpTemplateMessageBuilder() {
        }

        public WxMpTemplateMessageDTO.WxMpTemplateMessageBuilder toUser(String toUser) {
            this.toUser = toUser;
            return this;
        }

        public WxMpTemplateMessageDTO.WxMpTemplateMessageBuilder templateId(String templateId) {
            this.templateId = templateId;
            return this;
        }

        public WxMpTemplateMessageDTO.WxMpTemplateMessageBuilder url(String url) {
            this.url = url;
            return this;
        }

        public WxMpTemplateMessageDTO.WxMpTemplateMessageBuilder miniProgram(WxMpTemplateMessageDTO.MiniProgram miniProgram) {
            this.miniProgram = miniProgram;
            return this;
        }

        public WxMpTemplateMessageDTO.WxMpTemplateMessageBuilder data(List<WxMpTemplateData> data) {
            this.data = data;
            return this;
        }

        public WxMpTemplateMessageDTO build() {
            return new WxMpTemplateMessageDTO(this.toUser, this.templateId, this.url, this.miniProgram, this.data);
        }

        public String toString() {
            return "WxMpTemplateMessageDTO.WxMpTemplateMessageBuilder(toUser=" + this.toUser + ", templateId=" + this.templateId + ", url=" + this.url + ", miniProgram=" + this.miniProgram + ", data=" + this.data + ")";
        }
    }

    public static class MiniProgram implements Serializable {
        private static final long serialVersionUID = -7945254706501974849L;
        private String appid;
        private String page;

        public String getAppid() {
            return this.appid;
        }

        public String getPage() {
            return this.page;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public boolean equals(Object o) {
            if(o == this) {
                return true;
            } else if(!(o instanceof WxMpTemplateMessageDTO.MiniProgram)) {
                return false;
            } else {
                WxMpTemplateMessageDTO.MiniProgram other = (WxMpTemplateMessageDTO.MiniProgram)o;
                if(!other.canEqual(this)) {
                    return false;
                } else {
                    Object this$appid = this.getAppid();
                    Object other$appid = other.getAppid();
                    if(this$appid == null) {
                        if(other$appid != null) {
                            return false;
                        }
                    } else if(!this$appid.equals(other$appid)) {
                        return false;
                    }

                    Object this$pagePath = this.getPage();
                    Object other$pagePath = other.getPage();
                    if(this$pagePath == null) {
                        if(other$pagePath != null) {
                            return false;
                        }
                    } else if(!this$pagePath.equals(other$pagePath)) {
                        return false;
                    }

                    return true;
                }
            }
        }

        protected boolean canEqual(Object other) {
            return other instanceof WxMpTemplateMessageDTO.MiniProgram;
        }

        public int hashCode() {
            Object $appid = this.getAppid();
            int result = 1 * 59 + ($appid == null?43:$appid.hashCode());
            Object $pagePath = this.getPage();
            result = result * 59 + ($pagePath == null?43:$pagePath.hashCode());
            return result;
        }

        public String toString() {
            return "WxMpTemplateMessageDTO.MiniProgram(appid=" + this.getAppid() + ", pagePath=" + this.getPage() + ")";
        }

        public MiniProgram() {
        }

        @ConstructorProperties({"appid", "pagePath"})
        public MiniProgram(String appid, String page) {
            this.appid = appid;
            this.page = page;
        }
    }
}
