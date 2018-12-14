package com.huboot.business.base_model.ali_service.dto;


import com.huboot.business.base_model.weixin_service.dto.common.xenum.SystemEnum;
import com.huboot.business.common.enums.SMSNodeEnum;
import lombok.Data;

import java.util.List;

/**
 * Created by Administrator on 2018/1/17 0017.
 */
@Data
public class SMSDTO {

    //手机号码，多个用逗号隔开
    private String phones;
    //发送节点
    private SMSNodeEnum node;
    //所属系统
    private SystemEnum system;
    //发送参数
    private List<String> params;

}
