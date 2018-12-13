package com.huboot.business.mybatis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ApiModel(value = "RESTFul 分页元数据信息", description = "RESTFul 分页元数据信息")
public class PagerResultInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8148088945672856945L;
	@ApiModelProperty("数据库中符合GET条件的记录总数")
	private Integer total_count;
	@ApiModelProperty("每页的个数")
	private Integer per_page;
	@ApiModelProperty("当前页数")
	private Integer page;
	@ApiModelProperty("总页数")
	private Integer page_count;
	@ApiModelProperty("返回记录详细内容的聚合结果")
	private Map<String, Object> panorama = new HashMap();
	
	public Integer getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Integer total_count) {
		this.total_count = total_count;
	}
	public Integer getPer_page() {
		return per_page;
	}
	public void setPer_page(Integer per_page) {
		this.per_page = per_page;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getPage_count() {
		return page_count;
	}
	public void setPage_count(Integer page_count) {
		this.page_count = page_count;
	}

	public Map<String, Object> getPanorama() {
		return panorama;
	}

	public void setPanorama(Map<String, Object> panorama) {
		this.panorama = panorama;
	}
}
