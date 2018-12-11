package com.huboot.business.base_model.login.sso.client.dto.login;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ClientInfo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/** 登录ip */
	private String ip;
	/** 登录位置 */
	private String address;
	/** 经度 */
	private String longitude;
	/** 维度 */
	private String latitude;
	/** 手机OS信息 */
	private String os;
	/** IMEI */
	private String imei;
	/** 手机型号 */
	private String device;
	/** 消息推送客户端CLIENT ID */
	private String pushClientId;
	/**app 版本**/
	private String version;

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createTime = LocalDateTime.now();

	@JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime updateTime = LocalDateTime.now();

	private LocalDateTime loginTime;

	private LocalDateTime loginOutTime;


	public LocalDateTime getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(LocalDateTime loginTime) {
		this.loginTime = loginTime;
	}

	public LocalDateTime getLoginOutTime() {
		return loginOutTime;
	}

	public void setLoginOutTime(LocalDateTime loginOutTime) {
		this.loginOutTime = loginOutTime;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(LocalDateTime updateTime) {
		this.updateTime = updateTime;
	}

	public ClientInfo() {
		// TODO Auto-generated constructor stub
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getPushClientId() {
		return pushClientId;
	}

	public void setPushClientId(String pushClientId) {
		this.pushClientId = pushClientId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "ClientInfo [ip=" + ip + ", address=" + address + ", longitude=" + longitude + ", latitude=" + latitude
				+ ", os=" + os + ", imei=" + imei + ", device=" + device + ", pushClientId=" + pushClientId
				+ ", version=" + version + ", createTime=" + createTime + ", updateTime=" + updateTime + ", loginTime="
				+ loginTime + ", loginOutTime=" + loginOutTime + "]";
	}



}
