package com.huboot.business.base_model.login.sso.client.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huboot.business.base_model.login.sso.client.dto.api_security.ApiSecurityDTO;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "security.api")
public class ApiAuthenticationConfig {
	
	private List<ApiSecurityDTO> authentication;
	
	private String path;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<ApiSecurityDTO> getAuthentication() {
		return authentication;
	}

	public void setAuthentication(List<ApiSecurityDTO> authentication) {
		this.authentication = authentication;
	}

	public static void main(String[] args) throws JsonProcessingException {
		List<String> internalRole = Arrays.asList("internal_user");
		List<String> outRole = Arrays.asList("out_user");
		List<ApiSecurityDTO> list = new ArrayList<>();
		ApiSecurityDTO dto1 = new ApiSecurityDTO();
		dto1.setKey("abc123");
		dto1.setSysName("app");
		dto1.setRoles(internalRole);
		ApiSecurityDTO dto2 = new ApiSecurityDTO();
		dto2.setKey("abc456");
		dto2.setSysName("weixin");
		dto2.setRoles(outRole);
		list.add(dto1);
		list.add(dto2);
		System.out.println(new ObjectMapper().writeValueAsString(list));
	}
	

}
