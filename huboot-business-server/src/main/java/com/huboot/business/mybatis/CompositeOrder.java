package com.huboot.business.mybatis;

import java.io.Serializable;

/**
 * 复合排序
 * 
 * @author zhangtiebin@bwcmall.com
 * @description
 * @class CompositeOrder
 * @package com.bwcmall.core.query
 * @Date 2016年4月21日 下午7:25:33
 */
public class CompositeOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7806365875805821455L;
	// 查询字段名称映射domain的成员属性名称
	private String FieldName;
	//  指定排序方式,asc为正序 desc为倒序,默认是asc
	private String direction;
	// 查询字段名称，映射数据字段名称
	private String colName;
	
	public CompositeOrder() {
	}
	
	
	
	public CompositeOrder(String fieldName,String colName, String direction) {
		super();
		FieldName = fieldName;
		this.direction = direction;
		this.colName = colName;
	}



	public String getFieldName() {
		return FieldName;
	}

	public void setFieldName(String fieldName) {
		FieldName = fieldName;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

}
