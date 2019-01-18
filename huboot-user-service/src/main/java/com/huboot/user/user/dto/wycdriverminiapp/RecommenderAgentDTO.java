package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 推荐人-经纪人活动页面统计数据
 */
@Data
public class RecommenderAgentDTO implements Serializable {


    @ApiModelProperty("报名人数")
    private Integer driverHireCount = 0;

    @ApiModelProperty("签约人数")
    private Integer driverCount = 0;

    @ApiModelProperty("获得佣金")
    private Integer commission = 0;

    @ApiModelProperty("渠道编号(生成分享链接需要)")
    private String agentCode;

}

