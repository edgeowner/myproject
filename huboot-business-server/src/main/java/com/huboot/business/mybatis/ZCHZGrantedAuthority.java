package com.huboot.business.mybatis;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

public class ZCHZGrantedAuthority implements GrantedAuthority {
	private static final long serialVersionUID = -7997833252852735306L;
	private String authority;

	public ZCHZGrantedAuthority() {

	}

	public ZCHZGrantedAuthority(String authority) {
		Assert.hasText(authority, "A granted authority textual representation is required");
		this.authority = authority;
	}

	  

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof ZCHZGrantedAuthority) {
			return authority.equals(((ZCHZGrantedAuthority) obj).authority);
		}

		return false;
	}

	public String toString() {
		return this.authority;
	}

}
