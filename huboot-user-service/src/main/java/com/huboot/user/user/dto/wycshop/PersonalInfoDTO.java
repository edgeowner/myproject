package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *用户服务-个人信息表
 */
@Data
public class PersonalInfoDTO implements Serializable {

	@ApiModelProperty("身份证正面路径")
	private String idcardFacePath ;
	@ApiModelProperty("身份证反面路径")
	private String idcardBackPath ;
	@ApiModelProperty("名称")
	private String name ;
	@ApiModelProperty("身份证号")
	private String num ;
	@ApiModelProperty("驾驶证图片")
	private String driverLicensePath ;
	@ApiModelProperty("驾驶证姓名")
	private String licName ;
	@ApiModelProperty("驾驶证号")
	private String licNum ;
	@ApiModelProperty("准驾车型")
	private String licCarModel ;
	@ApiModelProperty("初次领证日期")
	private String licGetDate ;
	@ApiModelProperty("有效期")
	private String licValidity ;
	@ApiModelProperty("性别")
	private String sex ;
	@ApiModelProperty("性别枚举值")
	private String sexEnum;
	@ApiModelProperty("籍贯CityId")
	private Long birthplaceCityId;
	@ApiModelProperty("籍贯")
	private String birthplace ;
	@ApiModelProperty("居住地区域地址AreaId")
	private Long liveAreaId;
	@ApiModelProperty("居住地区域地址")
	private String liveAddr ;
	@ApiModelProperty("紧急联系人关系")
	private String contactRelation ;
	@ApiModelProperty("紧急联系人")
	private String contacter ;
	@ApiModelProperty("紧急联系人电话")
	private String contactPhone ;
	@ApiModelProperty("风控编号")
	private String riskSn ;

}

