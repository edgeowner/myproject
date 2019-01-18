package com.huboot.user.user.dto.wycshop;

import com.huboot.commons.page.AbstractQueryReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Data
@ToString
public class DriverHirePagerReqDTO extends AbstractQueryReqDTO {

    @ApiModelProperty("姓名")
    private String hireUserName;

    @ApiModelProperty("手机")
    private String hireUserPhone;

    @ApiModelProperty("推荐人")
    private String recommendUserName;

    @ApiModelProperty("推荐人手机")
    private String recommendUserPhone;

    @ApiModelProperty("一级渠道")
    private String oneLevelSource ;

    @ApiModelProperty("二级渠道")
    private String secondLevelSource ;

    @ApiModelProperty("进度")
    private String followStatus;

    @ApiModelProperty("创建开始时间")
    private String startCreateTime;

    @ApiModelProperty("创建结束时间")
    private String endCreateTime;

}
