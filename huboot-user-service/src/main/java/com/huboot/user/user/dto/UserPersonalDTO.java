package com.huboot.user.user.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *用户服务-个人信息表
 */
@Data
public class UserPersonalDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("用户ID")
	private Long userId ;
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
	@ApiModelProperty("有效期")
	private String licValidity ;
	@ApiModelProperty("性别")
	private String sex ;
	@ApiModelProperty("籍贯城市id")
	private Integer birthplaceCityId ;
	@ApiModelProperty("居住地区域id")
	private Integer liveAreaId ;
	@ApiModelProperty("居住地区域地址")
	private String liveAreaAddr ;
	@ApiModelProperty("紧急联系人")
	private String contacters ;

}

