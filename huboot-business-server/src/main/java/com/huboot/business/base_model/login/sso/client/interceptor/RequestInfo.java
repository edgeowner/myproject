package com.huboot.business.base_model.login.sso.client.interceptor;

import java.util.HashMap;
import java.util.Map;

public class RequestInfo {
	
	private static ThreadLocal<Map<String,Object>> request = new ThreadLocal<Map<String,Object>>();
	
	public static void put(String key,Object value){
		Map<String,Object> requestContext = request.get();
		if(requestContext==null){
			requestContext = new HashMap<String,Object>();	
			request.set(requestContext);
		}
		requestContext.put(key, value);
	}
	
	public static Object get(String key){
		Map<String,Object> requestContext = request.get();
		return requestContext==null?null:requestContext.get(key);
	}
	
	public static Map<String,Object> getAll(){
		return request.get();
	}
	
	public static void clear(){
		Map<String,Object> requestContext = request.get();
		requestContext.clear();
	}
}
