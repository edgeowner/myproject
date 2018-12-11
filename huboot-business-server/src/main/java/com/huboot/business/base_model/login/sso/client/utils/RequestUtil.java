package com.huboot.business.base_model.login.sso.client.utils;


import com.huboot.business.base_model.login.sso.client.dto.login.RequestHeader;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class RequestUtil {
	
	public static RequestHeader getRequestHeader(HttpServletRequest request){
		Enumeration<String> enumeration = request.getHeaderNames();
		Map<String,String> map = new HashMap<>();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			String value = request.getHeader(name);
			if(name.contains("-")){
				map.put(formatHttpHeadKey(name), value);
			}else{
				map.put(name, value);
			}
		}
		RequestHeader header = new RequestHeader();
		BeanRefUtil.setFieldValue(header, map);
		return header;
	}
	
	public static RequestHeader getJwtSignRequestHeader(HttpServletRequest request){
		RequestHeader header = getRequestHeader(request);
		RequestHeader requestHeader = new RequestHeader();
		requestHeader.setAcceptLanguage(header.getAcceptLanguage());
		requestHeader.setAcceptEncoding(header.getAcceptEncoding());
		return requestHeader;
	}
	
	public static Map<String,String> getRequestMap(HttpServletRequest request){
		//RequestHeader.class.getMethod(arg0, arg1)
		Enumeration<String> enumeration = request.getHeaderNames();
		Map<String,String> map = new HashMap<>();
		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();
			String value = request.getHeader(name);
			map.put(name, value);
		}
		return map;
	}

	/**
	 * 字符串首字母大写
	 * **/
	public static String upperCase(String str) {  
	    char[] ch = str.toCharArray();  
	    if (ch[0] >= 'a' && ch[0] <= 'z') {  
	        ch[0] = (char) (ch[0] - 32);  
	    }  
	    return new String(ch);  
	}
	
	/**
	 * 格式化http请求参数，（accept-language  -> acceptLanguage）
	 * **/
	public static String formatHttpHeadKey(String name){
		String[] temp = name.split("-");
		StringBuffer bf = new StringBuffer();
		for(int i = 0; i < temp.length; i++){
			if(i == 0){
				bf.append(temp[i]);
			}else{
				bf.append(upperCase(temp[i]));
			}
		}
		return bf.toString();
	}
	


}
