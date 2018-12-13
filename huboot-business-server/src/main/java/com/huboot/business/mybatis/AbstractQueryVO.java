package com.huboot.business.mybatis;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author zhangtiebin@hn-zhixin.com
 * @ClassName: AbstactEntity
 * @Description: domain抽象类
 * @date 2015年6月24日 下午3:23:05
 */
public abstract class AbstractQueryVO implements Serializable {
    private static final long serialVersionUID = 1L;

//    @ApiParam(value = "页码", defaultValue = "1", required = true)
    @ApiModelProperty(value = "页码，默认：1",required = true)
    private Integer page = 1;// 限定传输的记录数,如page=1,显示第一页的数据
    @ApiModelProperty(value = "每页的条数，默认：10", required = true)
    private Integer per_page = 10;// 指定分页的页大小,例如每页显示10个记录,per_page=10

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPer_page() {
        return per_page;
    }

    public void setPer_page(Integer per_page) {
        this.per_page = per_page;
    }
}
