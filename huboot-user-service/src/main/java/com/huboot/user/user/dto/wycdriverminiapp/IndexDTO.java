package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@Data
public class IndexDTO {

    @ApiModelProperty("推荐状态：yes-已是推荐人，no-还不是推荐人")
    private String recommenderStatus;

    @ApiModelProperty("头像地址：sign-已签约，fail-未通过，wait-待签约，no-未申请")
    private String driverStatus;

}
