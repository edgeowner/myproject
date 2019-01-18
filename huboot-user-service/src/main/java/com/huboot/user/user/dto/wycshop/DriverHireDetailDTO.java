package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/7 0007.
 */
@Data
public class DriverHireDetailDTO {

    @ApiModelProperty("招聘信息id")
    private Long hireId;
    @ApiModelProperty("招聘用户id")
    private Long hireUserId ;
    @ApiModelProperty("招聘用户电话")
    private String hirePhone ;
    @ApiModelProperty("一级渠道")
    private String oneLevelSource ;
    @ApiModelProperty("一级渠道名称")
    private String oneLevelSourceName ;
    @ApiModelProperty("二级渠道")
    private String secondLevelSource ;
    @ApiModelProperty("二级渠道名称")
    private String secondLevelSourceName ;
    @ApiModelProperty("跟进状态")
    private String followStatus ;
    @ApiModelProperty("跟进状态")
    private String followStatusName ;

    @ApiModelProperty("跟进日志")
    private List<DriverFollowLogInfoDTO> followLogList = new ArrayList<>();

    @ApiModelProperty("个人信息")
    private PersonalInfoDTO personalInfo = new PersonalInfoDTO();

    @ApiModelProperty("推荐信息")
    private RecommendInfoDTO recommendInfo = new RecommendInfoDTO();

    @ApiModelProperty("司机信息")
    private DriverInfoDTO driverInfo = new DriverInfoDTO();

    /*@ApiModelProperty("签约规则信息")
    private RebateRuleExtendDetailInnerDTO rebateRuleInfo = new RebateRuleExtendDetailInnerDTO();
*/



}
