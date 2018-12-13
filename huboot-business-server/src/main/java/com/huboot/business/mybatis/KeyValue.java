package com.huboot.business.mybatis;

import java.io.Serializable;

public class KeyValue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6999144358097268637L;
	private Integer key;
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	private String value;
	
}
