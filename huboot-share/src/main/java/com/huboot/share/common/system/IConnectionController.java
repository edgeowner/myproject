package com.huboot.share.common.system;

import feign.Param;
import feign.RequestLine;



public interface IConnectionController {

	String URL = "/inner/system/connection/{fromService}";

	@RequestLine("GET " + URL)
	public String connection(@Param("fromService")String fromService);

}
