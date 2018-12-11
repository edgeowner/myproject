package com.huboot.business.base_model.weixin_service.dto;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *微信模板信息表
 */
@Data
public class WeixinTempalteQueryDTO implements Serializable {

	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("微信节点")
	private Integer node ;
	@ApiModelProperty("模板库中模板的编号，有“TM**”和“OPENTMTM**”等形式")
	private String templateIdShort ;
	@ApiModelProperty("标题")
	private String title ;
	@ApiModelProperty("点击模板跳转页面")
	private String clickUrl ;
	@ApiModelProperty("状态（0-无，1-模板保存，2-公众号模板添加完成）")
	private Integer status ;
	@ApiModelProperty("应用系统")
	private Integer system ;
	@ApiModelProperty("页码")
	private Integer page = 0;
	@ApiModelProperty("条数")
	private Integer size = 10;

}

