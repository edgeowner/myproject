package com.huboot.business.base_model.weixin_service.dto;

import me.chanjar.weixin.common.bean.menu.WxMenuRule;
import me.chanjar.weixin.common.util.ToStringUtils;
import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hqr on 2018/11/26.
 */
public class WxMenuDTO implements Serializable {

    private static final long serialVersionUID = -7083914585539687746L;
    private List<WxMenuButtonDTO> button = new ArrayList();
    private WxMenuRule matchRule;

    public static WxMenuDTO fromJson(String json) {
        return (WxMenuDTO) WxGsonBuilder.create().fromJson(json, WxMenuDTO.class);
    }

    public static WxMenuDTO fromJson(InputStream is) {
        return (WxMenuDTO)WxGsonBuilder.create().fromJson(new InputStreamReader(is, StandardCharsets.UTF_8), WxMenuDTO.class);
    }

    public String toJson() {
        return WxGsonBuilder.create().toJson(this);
    }

    public String toString() {
        return ToStringUtils.toSimpleString(this);
    }

    public WxMenuDTO() {
    }

    public List<WxMenuButtonDTO> getButton() {
        return this.button;
    }

    public WxMenuRule getMatchRule() {
        return this.matchRule;
    }

    public void setButton(List<WxMenuButtonDTO> button) {
        this.button = button;
    }

    public void setMatchRule(WxMenuRule matchRule) {
        this.matchRule = matchRule;
    }

    public boolean equals(Object o) {
        if(o == this) {
            return true;
        } else if(!(o instanceof WxMenuDTO)) {
            return false;
        } else {
            WxMenuDTO other = (WxMenuDTO)o;
            if(!other.canEqual(this)) {
                return false;
            } else {
                Object this$buttons = this.getButton();
                Object other$buttons = other.getButton();
                if(this$buttons == null) {
                    if(other$buttons != null) {
                        return false;
                    }
                } else if(!this$buttons.equals(other$buttons)) {
                    return false;
                }

                Object this$matchRule = this.getMatchRule();
                Object other$matchRule = other.getMatchRule();
                if(this$matchRule == null) {
                    if(other$matchRule != null) {
                        return false;
                    }
                } else if(!this$matchRule.equals(other$matchRule)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof WxMenuDTO;
    }

    public int hashCode() {
        Object $buttons = this.getButton();
        int result = 1 * 59 + ($buttons == null?43:$buttons.hashCode());
        Object $matchRule = this.getMatchRule();
        result = result * 59 + ($matchRule == null?43:$matchRule.hashCode());
        return result;
    }
}
