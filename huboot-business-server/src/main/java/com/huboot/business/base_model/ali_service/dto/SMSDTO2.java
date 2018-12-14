package com.huboot.business.base_model.ali_service.dto;


import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;
import com.huboot.business.common.enums.SMSNodeEnum;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
@Data
public class SMSDTO2 {

    private static final long serialVersionUID = 1L;
    //手机号码（逗号分隔）
    private List<String> mobiles ;
    //定时发送时间，不填：及时发送
    private Date sendTime ;
    //占位符的内容-详情参考表的内容
    private Map<String,String> contentMap;

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Map<String, String> getContentMap() {
        return contentMap;
    }

    public void setContentMap(Map<String, String> contentMap) {
        this.contentMap = contentMap;
    }

}
