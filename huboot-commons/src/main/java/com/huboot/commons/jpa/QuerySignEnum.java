package com.huboot.commons.jpa;

/**
 * Created by xlaoy on 2017/12/23 0023.
 */
public enum QuerySignEnum {

	eq("eq","=","等于"),
	lt("lt","<","小于"),
	lte("lte","<=","小于等于"),
	gt("gt",">","大于"),
	gte("gte",">=","大于等于"),
	ne("ne","!=","不等于"),
	in("in","in","在xx之间"),
	like("like","like","模糊匹配"),
	or("or","or","或");//仔细看用法

	private String name;
	private String dbOp;
	private String desc;

	private QuerySignEnum(String name, String dbOp, String desc) {
		this.name = name;
		this.desc = desc;
		this.dbOp = dbOp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDbOp() {
		return dbOp;
	}

	public void setDbOp(String dbOp) {
		this.dbOp = dbOp;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static QuerySignEnum dbOpOf(String dbOp) {
		for (QuerySignEnum s : QuerySignEnum.values()) {
			if (s.dbOp.equals(dbOp))
				return s;
		}
		throw new IllegalArgumentException(String.format("值%s不能转换成数据库操作符", dbOp));
	}

}
