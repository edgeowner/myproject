package com.huboot.business.base_model.weixin_service.dto.weixin_center.dto;

import lombok.Data;

import java.io.File;

/**
 * Created by Administrator on 2018/6/15 0015.
 */
@Data
public class QrCodeDTO {

    private String path;

    private File file;
}
