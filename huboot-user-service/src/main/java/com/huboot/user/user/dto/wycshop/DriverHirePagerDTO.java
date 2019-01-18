package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Data
@ToString
public class DriverHirePagerDTO {

    @ApiModelProperty("照片信息id")
    private Long hireId;
    @ApiModelProperty("姓名")
    private String hireUserName;
    @ApiModelProperty("手机")
    private String hireUserPhone;
    @ApiModelProperty("注册渠道")
    private String sourceName;
    @ApiModelProperty("进度")
    private String followStatus;
    @ApiModelProperty("进度")
    private String followStatusName;
    @ApiModelProperty("推荐人")
    private String recommendUserName;
    @ApiModelProperty("推荐人手机")
    private String recommendUserPhone;
    @ApiModelProperty("创建时间")
    private String createTime;
    @ApiModelProperty("分配人员")
    private String followUserName;
    @ApiModelProperty("下次跟进时间")
    private String nextTime;
    @ApiModelProperty("反馈")
    private String feedback;

}
