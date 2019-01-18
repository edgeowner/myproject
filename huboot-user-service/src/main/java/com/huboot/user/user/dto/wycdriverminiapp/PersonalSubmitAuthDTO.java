package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 *用户服务-个人信息表
 */
@Data
public class PersonalSubmitAuthDTO implements Serializable {

	@ApiModelProperty("身份证正面路径")
	private String idcardFacePath ;

	@ApiModelProperty("身份证反面路径")
	private String idcardBackPath ;

	@ApiModelProperty("名称")
	@NotBlank(message = "名称不能为空")
	private String name ;

	@ApiModelProperty("身份证号")
	@NotBlank(message = "身份证号不能为空")
	private String num ;

	@ApiModelProperty("驾驶证图片")
	private String driverLicensePath ;

	@ApiModelProperty("驾驶证姓名")
	@NotBlank(message = "驾驶证姓名不能为空")
	private String licName ;

	@ApiModelProperty("驾驶证号")
	@NotBlank(message = "驾驶证号不能为空")
	private String licNum ;

	@ApiModelProperty("初次领证日期")
	@NotBlank(message = "初次领证日期不能为空")
	private String licGetDate ;

	@ApiModelProperty("准驾车型")
	@NotBlank(message = "准驾车型不能为空")
	private String licCarModel ;

	@ApiModelProperty("有效期")
	@NotBlank(message = "有效期不能为空")
	private String licValidity ;

}

