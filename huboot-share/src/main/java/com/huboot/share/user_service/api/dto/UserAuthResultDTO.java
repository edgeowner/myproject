package com.huboot.share.user_service.api.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/8/27 0027.
 */
@Data
public class UserAuthResultDTO implements Serializable {

    //
    private String appId;

    private String openId;

    private String unionid;

    private String headImgUrl;

    private String nickname;
}
