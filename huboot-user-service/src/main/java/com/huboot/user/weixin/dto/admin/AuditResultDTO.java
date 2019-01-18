package com.huboot.user.weixin.dto.admin;

import lombok.Data;

import java.io.Serializable;

/**
 *小程序
 */
@Data
public class AuditResultDTO implements Serializable {

	private String errcode ;
	private String errmsg ;
	private String auditid ;
	private String status ;
	private String reason ;

}

