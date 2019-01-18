package com.huboot.user.user.dto.wycshop;

import com.huboot.share.user_service.enums.DriverSourceOneLevelEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Data
@ToString
public class DriverHirePromotionDTO {

    @ApiModelProperty("一级渠道值")
    private DriverSourceOneLevelEnum oneLoveEnum;

   /* @ApiModelProperty("推荐人实体")
    private RecommenderEntity recommenderEntity;
*/
}
