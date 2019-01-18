package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Data
@ToString
public class DriverHireApplyDTO {

    @ApiModelProperty("照片信息id")
    private Long hireId;
    /*@ApiModelProperty("姓名")
    private String hireUserName;*/
    @ApiModelProperty("手机")
    private String hireUserPhone;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("进度")
    private String followStatusName;
    @ApiModelProperty("是否联系")
    private Integer isContact = 0;
}
