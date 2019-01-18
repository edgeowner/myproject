package com.huboot.user.weixin.dto;

import com.google.gson.annotations.SerializedName;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hqr on 2018/11/26.
 */
public class WxMenuButtonDTO implements Serializable {

    private static final long serialVersionUID = -1070939403109776555L;
    private String type;
    private String name;
    private String key;
    private String url;
    @SerializedName("media_id")
    private String mediaId;
    @SerializedName("appid")
    private String appId;
    @SerializedName("pagepath")
    private String pagePath;
    @SerializedName("sub_button")
    private List<WxMenuButtonDTO> subButton = new ArrayList();

    public String toString() {
        return WxGsonBuilder.create().toJson(this);
    }

    public WxMenuButtonDTO() {
    }

    public String getType() {
        return this.type;
    }

    public String getName() {
        return this.name;
    }

    public String getKey() {
        return this.key;
    }

    public String getUrl() {
        return this.url;
    }

    public String getMediaId() {
        return this.mediaId;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getPagePath() {
        return this.pagePath;
    }

    public List<WxMenuButtonDTO> getSubButton() {
        return this.subButton;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setPagePath(String pagePath) {
        this.pagePath = pagePath;
    }

    public void setSubButton(List<WxMenuButtonDTO> subButton) {
        this.subButton = subButton;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof WxMenuButtonDTO)) {
            return false;
        } else {
            WxMenuButtonDTO other = (WxMenuButtonDTO)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                label107: {
                    Object this$type = this.getType();
                    Object other$type = other.getType();
                    if(this$type == null) {
                        if(other$type == null) {
                            break label107;
                        }
                    } else if(this$type.equals(other$type)) {
                        break label107;
                    }

                    return false;
                }

                Object this$name = this.getName();
                Object other$name = other.getName();
                if(this$name == null) {
                    if(other$name != null) {
                        return false;
                    }
                } else if(!this$name.equals(other$name)) {
                    return false;
                }

                Object this$key = this.getKey();
                Object other$key = other.getKey();
                if(this$key == null) {
                    if(other$key != null) {
                        return false;
                    }
                } else if(!this$key.equals(other$key)) {
                    return false;
                }

                label86: {
                    Object this$url = this.getUrl();
                    Object other$url = other.getUrl();
                    if(this$url == null) {
                        if(other$url == null) {
                            break label86;
                        }
                    } else if(this$url.equals(other$url)) {
                        break label86;
                    }

                    return false;
                }

                label79: {
                    Object this$mediaId = this.getMediaId();
                    Object other$mediaId = other.getMediaId();
                    if(this$mediaId == null) {
                        if(other$mediaId == null) {
                            break label79;
                        }
                    } else if(this$mediaId.equals(other$mediaId)) {
                        break label79;
                    }

                    return false;
                }

                label72: {
                    Object this$appId = this.getAppId();
                    Object other$appId = other.getAppId();
                    if(this$appId == null) {
                        if(other$appId == null) {
                            break label72;
                        }
                    } else if(this$appId.equals(other$appId)) {
                        break label72;
                    }

                    return false;
                }

                Object this$pagePath = this.getPagePath();
                Object other$pagePath = other.getPagePath();
                if(this$pagePath == null) {
                    if(other$pagePath != null) {
                        return false;
                    }
                } else if(!this$pagePath.equals(other$pagePath)) {
                    return false;
                }

                Object this$subButtons = this.getSubButton();
                Object other$subButtons = other.getSubButton();
                if(this$subButtons == null) {
                    if(other$subButtons != null) {
                        return false;
                    }
                } else if(!this$subButtons.equals(other$subButtons)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof WxMenuButtonDTO;
    }

    public int hashCode() {
        Object $type = this.getType();
        int result = 1 * 59 + ($type == null?43:$type.hashCode());
        Object $name = this.getName();
        result = result * 59 + ($name == null?43:$name.hashCode());
        Object $key = this.getKey();
        result = result * 59 + ($key == null?43:$key.hashCode());
        Object $url = this.getUrl();
        result = result * 59 + ($url == null?43:$url.hashCode());
        Object $mediaId = this.getMediaId();
        result = result * 59 + ($mediaId == null?43:$mediaId.hashCode());
        Object $appId = this.getAppId();
        result = result * 59 + ($appId == null?43:$appId.hashCode());
        Object $pagePath = this.getPagePath();
        result = result * 59 + ($pagePath == null?43:$pagePath.hashCode());
        Object $subButtons = this.getSubButton();
        result = result * 59 + ($subButtons == null?43:$subButtons.hashCode());
        return result;
    }
}
