package com.huboot.business.base_model.weixin_service.dto;

import com.huboot.business.common.component.exception.BizException;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 *商家微信公众号配置信息表
 */
public class WeixinPublicCreateDTO implements Serializable {

	@ApiModelProperty("公众号appid")
	private String appId ;
	@ApiModelProperty("公众号secret")
	private String secret ;
	@ApiModelProperty("公众号token")
	private String token ;
	@ApiModelProperty("公众号aes_key")
	private String aesKey ;
	@ApiModelProperty("原始id")
	private String originalId ;
	@ApiModelProperty("所属系统")
	private Integer system;
	@ApiModelProperty("类型")
	private Integer type;
	@ApiModelProperty("备注")
	private String remark;

	public String getAppId() {
		if(StringUtils.isEmpty(appId)) {
			throw new BizException("appId 不能为空");
		}
		return appId;
	}

	public String getSecret() {
		if(StringUtils.isEmpty(secret)) {
			throw new BizException("secret 不能为空");
		}
		return secret;
	}

	public String getToken() {
		if(StringUtils.isEmpty(token)) {
			throw new BizException("token 不能为空");
		}
		return token;
	}

	public String getAesKey() {
		if(StringUtils.isEmpty(aesKey)) {
			throw new BizException("aesKey 不能为空");
		}
		return aesKey;
	}

	public String getOriginalId() {
		if(StringUtils.isEmpty(originalId)) {
			throw new BizException("originalId 不能为空");
		}
		return originalId;
	}

	public Integer getSystem() {
		if(system == null) {
			throw new BizException("system 不能为空");
		}
		return system;
	}

	public String getRemark() {
		return remark;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public void setSystem(Integer system) {
		this.system = system;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}

