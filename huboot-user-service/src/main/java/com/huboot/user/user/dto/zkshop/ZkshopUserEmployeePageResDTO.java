package com.huboot.user.user.dto.zkshop;

import java.io.Serializable;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 *直客商家端-用户服务-企业员工表
 */
@Data
public class ZkshopUserEmployeePageResDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("唯一标识")
	private Long id ;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime ;
	@ApiModelProperty("更新时间")
	private LocalDateTime modifyTime ;
	@ApiModelProperty("用户ID")
	private Long userId ;
	@ApiModelProperty("名称(非实名认证)")
	private String name;
	@ApiModelProperty("角色名称")
	private String roleName;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("菜单数据：前端控制")
	private List<String> permission;
	@ApiModelProperty("颜色:例如:#00a2ff")
	private String colour;
}

