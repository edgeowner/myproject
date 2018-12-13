package com.huboot.business.mybatis;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

@ApiModel(value="id列表", description="id列表")
public class IdList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1288517865885964522L;
	@ApiModelProperty(value="id列表")
	private List<Long> ids;

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}
}
