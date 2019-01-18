package com.huboot.user.user.dto.wycdriverminiapp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *用户服务-个人信息表
 */
@Data
public class PersonalSettingInfo implements Serializable {

	@ApiModelProperty("头像地址")
	private String imagePath ;
	@ApiModelProperty("姓名")
	private String  userId;
	@ApiModelProperty("姓名")
	private String name ;
	@ApiModelProperty("实名认证状态")
	private String authStatus ;
	@ApiModelProperty("手机")
	private String phone ;
	@ApiModelProperty("性别")
	private String sex ;
	@ApiModelProperty("性别")
	private String sexName ;
	@ApiModelProperty("籍贯CityId")
	private Long birthplaceCityId;
	@ApiModelProperty("籍贯城市")
	private String birthplaceCity ;
	@ApiModelProperty("籍贯城市")
	private String birthplace ;
	@ApiModelProperty("居住地区域地址AreaId")
	private Long liveAreaId;
	@ApiModelProperty("居住地区域地址Area")
	private String liveArea;
	@ApiModelProperty("居住地区域地址")
	private String liveAddr ;
	@ApiModelProperty("紧急联系人关系")
	private String contactRela ;
	@ApiModelProperty("紧急联系人")
	private String contactName ;
	@ApiModelProperty("紧急联系人")
	private String contactPhone ;

}

