package com.huboot.user.weixin.dto.admin;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *公众号
 */
@Data
public class WxmpMessageTemplatePagerDTO implements Serializable {

	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("微信通知节点")
	private String node ;
	@ApiModelProperty("微信通知节点")
	private String nodeName ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式")
	private String templateIdShort ;
	@ApiModelProperty("打开方式（h5或者小程序）")
	private String openTypeName ;
	@ApiModelProperty("url")
	private String url ;
	@ApiModelProperty("所需跳转到小程序的具体页面路径")
	private String miniPagepath ;
	@ApiModelProperty("H5跳转url是否需要认证授权")
	private String urlNeedAuth ;
	@ApiModelProperty("备注")
	private String remark ;

}

