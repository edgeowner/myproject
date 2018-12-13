package com.huboot.business.mybatis;

import com.huboot.business.common.utils.JsonUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 
 * @ClassName: BaseResponse
 * @Description: 基础响应
 * @author zhangtiebin@hn-zhixin.com
 * @date 2015年7月2日 上午10:11:22
 *
 */
@ApiModel(value = "RESTFul基础响应信息", description = "RESTFul基础响应信息")
public class BaseResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1518484851893182089L;
	@ApiModelProperty("成功失败标志")
	private Boolean success = true; 
	@ApiModelProperty("错误信息")
	private ResourceError error; 
	@ApiModelProperty("成功提示信息")
	private String message; 
	@ApiModelProperty("响应数据实体")
	private Object result; 
	@ApiModelProperty("分页元数据信息")
	private PagerResultInfo result_info;
	@ApiModelProperty("分页元数据信息")
	private String bizData;

	public String getBizData() {
		return bizData;
	}

	public void setBizData(String bizData) {
		this.bizData = bizData;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public ResourceError getError() {
		return error;
	}

	public void setError(ResourceError error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public PagerResultInfo getResult_info() {
		return result_info;
	}

	public void setResult_info(PagerResultInfo result_info) {
		this.result_info = result_info;
	}

	public <T> T getResult(Class<T> clazz) {
		if(!success) {
			throw new ApiException(message);
		}
		if(result == null) {
			return null;
		}
		String json = JsonUtils.toJsonString(result);
		return JsonUtils.fromJson(json, clazz);
	}

	public <T> List<T> getResultList(Class<T> clazz) {
		if(!success) {
			throw new ApiException(message);
		}
		if(result instanceof Collection) {
			Collection collection = (Collection)result;
			if(CollectionUtils.isEmpty(collection)) {
				return new ArrayList<T>();
			}
		}
		String json = JsonUtils.toJsonString(result);
		return JsonUtils.fromJsonToList(json, clazz);
	}

}
