package com.huboot.share.user_service.api.dto;

import lombok.Data;

/**
 * Created by Administrator on 2018/9/21 0021.
 */
@Data
public class QrcodeCreateDTO {

    public static final String DRIVER_HIRE = "pages/recruit/recruit";

    public static final String ACTIVITY_FRIENDS = "pages/marketing/friends/friends";

    public static final String INDEX = "pages/index/index";

    public static final String AGENT = "pages/marketing/driver/driver";

    public static final String ZK_PAY = "pages/pay/pay?settleBillId=";

    private Long shopId;

    private String scene;

    private String page;
}
