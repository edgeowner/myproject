package com.huboot.user.role.dto.wycshop;

import com.huboot.commons.page.AbstractQueryReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *用户服务-角色表
 */
@Data
public class WycShopRoleQueryReqDTO extends AbstractQueryReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	@ApiModelProperty("名称")
	private String name ;

}

