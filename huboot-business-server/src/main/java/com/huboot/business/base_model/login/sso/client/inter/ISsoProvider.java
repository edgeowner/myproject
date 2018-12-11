package com.huboot.business.base_model.login.sso.client.inter;

import java.util.List;

public interface ISsoProvider {
	
	/**
	 * 获取业务系统id
	 * **/
	String getBussiessId(String gid);
	
	/**
	 * 获取用户角色
	 * **/
	List<String> getUserRolse(String gid);

}
