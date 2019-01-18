package com.huboot.user.role.dto.zkshop;

import java.io.Serializable;

import com.huboot.commons.page.AbstractQueryReqDTO;
import lombok.Data;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 *直客商家端-用户服务-角色表
 */
@Data
public class ZkshopRoleQueryReqDTO extends AbstractQueryReqDTO implements Serializable {

	private static final long serialVersionUID = 1L;

}

