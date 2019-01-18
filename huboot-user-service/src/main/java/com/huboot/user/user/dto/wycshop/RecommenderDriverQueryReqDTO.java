package com.huboot.user.user.dto.wycshop;

import com.huboot.commons.page.AbstractQueryReqDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 *推荐人
 */
@Data
public class RecommenderDriverQueryReqDTO extends AbstractQueryReqDTO implements Serializable {

	@ApiModelProperty("推荐人userId")
	private Long recommenderUserId ;

}

