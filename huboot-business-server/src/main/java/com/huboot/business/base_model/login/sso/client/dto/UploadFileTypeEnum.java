package com.huboot.business.base_model.login.sso.client.dto;

/**
* 上传文件类型
 */
public enum UploadFileTypeEnum {

  	Img(1, "图片", 1),
	;

	//值
	private Integer value;
	//显示名称
	private String showName;
	//序列号
	private Integer seqNo;

	UploadFileTypeEnum(Integer value, String showName,Integer seqNo) {
		this.value = value;
		this.showName = showName;
		this.seqNo = seqNo;
	}

	public static UploadFileTypeEnum valueOf(int value) {
		for (UploadFileTypeEnum s : UploadFileTypeEnum.values()) {
			if (s.value.equals(value))
				return s;
		}
		throw new IllegalArgumentException(String.format("值%s不能转换成枚举", value));
	}

	public String getShowName() {
		return showName;
	}

	public Integer getValue() {
		return value;
	}

	public Integer getSeqNo() {
		return seqNo;
	}
}
