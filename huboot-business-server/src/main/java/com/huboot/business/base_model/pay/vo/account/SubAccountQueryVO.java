package com.huboot.business.base_model.pay.vo.account;


import com.huboot.business.mybatis.AbstractQueryVO;
import com.huboot.business.mybatis.QueryConfig;
import com.huboot.business.mybatis.QueryOperatorEnum;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

//@ApiModel(value = "账户中心-子账户", description = "账户中心-子账户")
public class SubAccountQueryVO extends AbstractQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收支：0-全部，1-收入，2-支出",hidden = true)
    @QueryConfig(colname = "in_out", supportOps = {QueryOperatorEnum.eq})
    private Integer inOut;

    public Integer getInOut() {
        return inOut;
    }

    public void setInOut(Integer inOut) {
        this.inOut = inOut;
    }
}

