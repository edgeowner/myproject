package com.huboot.business.base_model.login.sso.client.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@ApiModel(value = "RESTFul基础响应信息", description = "RESTFul基础响应信息")
public class BaseUser implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String gid = UUID.randomUUID().toString().replace("-","");

	@ApiModelProperty("响应数据实体")
	public String account;

	private String password;
	
	private String fromSystem;//注册系统
    
	private UserStatus status;
    
	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime = LocalDateTime.now();

    public enum UserStatus{
    	init,normal,frozen,cancellation;//初始化，正常，冻结，注销
    }

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFromSystem() {
		return fromSystem;
	}

	public void setFromSystem(String fromSystem) {
		this.fromSystem = fromSystem;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "BaseUser{" +
				"gid='" + gid + '\'' +
				", account='" + account + '\'' +
				", password='" + password + '\'' +
				", fromSystem='" + fromSystem + '\'' +
				", status=" + status +
				", createTime=" + createTime +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		BaseUser baseUser = (BaseUser) o;

		if (!gid.equals(baseUser.gid)) return false;
		if (!account.equals(baseUser.account)) return false;
		if (!password.equals(baseUser.password)) return false;
		if (!fromSystem.equals(baseUser.fromSystem)) return false;
		if (status != baseUser.status) return false;
		return createTime.equals(baseUser.createTime);
	}

	@Override
	public int hashCode() {
		int result = gid.hashCode();
		result = 31 * result + account.hashCode();
		result = 31 * result + password.hashCode();
		result = 31 * result + fromSystem.hashCode();
		result = 31 * result + status.hashCode();
		result = 31 * result + createTime.hashCode();
		return result;
	}
}
