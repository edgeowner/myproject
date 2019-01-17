package com.huboot.commons.page;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 抽象类：分页查询实体
 */
@Data
public class AbstractQueryReqDTO implements Serializable {

    @ApiModelProperty("创建时间")
    protected LocalDateTime createTime;
    @ApiModelProperty("更新时间")
    protected LocalDateTime modifyTime;
    @ApiModelProperty("页码")
    protected Integer page = 1;
    @ApiModelProperty("条数")
    protected Integer size = 10;

}
