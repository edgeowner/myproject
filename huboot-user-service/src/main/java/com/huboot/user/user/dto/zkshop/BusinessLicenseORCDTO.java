package com.huboot.user.user.dto.zkshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/9/12 0012.
 */
@Data
public class BusinessLicenseORCDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("单位名称")
    private String name;
    @ApiModelProperty("社会信用代码")
    private String regNum;
    @ApiModelProperty("法人")
    private String person;
    @ApiModelProperty("证件编号")
    private String personIdcard;

    @ApiModelProperty("照片路径")
    private String businessLicensePath ;
}
