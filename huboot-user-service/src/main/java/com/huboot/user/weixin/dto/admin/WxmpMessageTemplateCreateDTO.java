package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 *公众号
 */
@Data
public class WxmpMessageTemplateCreateDTO implements Serializable {

	@NotEmpty(message = "微信通知节点不能为空")
	@ApiModelProperty("微信通知节点")
	private String node ;

	@NotEmpty(message = "模板库中模板的编号不能为空")
	@ApiModelProperty("模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式")
	private String templateIdShort ;

	@NotEmpty(message = "打开方式不能为空")
	@ApiModelProperty("打开方式（h5或者小程序）")
	private String openType ;

	@ApiModelProperty("url")
	private String url ;

	@ApiModelProperty("所需跳转到小程序的具体页面路径")
	private String miniPagepath ;

	@NotEmpty(message = "H5跳转url是否需要认证授权不能为空")
	@ApiModelProperty("H5跳转url是否需要认证授权")
	private String urlNeedAuth ;

	@ApiModelProperty("备注")
	private String remark ;

	@ApiModelProperty("备注")
	private String config ;

}

