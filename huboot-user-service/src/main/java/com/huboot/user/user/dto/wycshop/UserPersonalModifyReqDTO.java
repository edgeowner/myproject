package com.huboot.user.user.dto.wycshop;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 用户服务-个人信息表
 */
@Data
public class UserPersonalModifyReqDTO implements Serializable {

    @ApiModelProperty("ID")
    private Long id;
    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;
    @ApiModelProperty("身份证号")
    @NotBlank(message = "身份证号不能为空")
    private String num;
    @ApiModelProperty("驾驶证号")
    @NotBlank(message = "驾驶证号不能为空")
    private String licNum;
    @ApiModelProperty("准驾车型")
    @NotBlank(message = "准驾车型不能为空")
    private String licCarModel;
    @ApiModelProperty("驾照有效期限")
    @NotBlank(message = "驾照有效期限不能为空")
    private String licValidity;
    @ApiModelProperty("初次领证日期")
    @NotBlank(message = "初次领证日期不能为空")
    private String licGetDate;
    @ApiModelProperty("性别")
    private String sex;
    @ApiModelProperty("籍贯CityId")
    private Long birthplaceCityId;
    @ApiModelProperty("籍贯")
    private String birthplace;
    @ApiModelProperty("居住地区域地址AreaId")
    private Long liveAreaId;
    @ApiModelProperty("居住地区域地址")
    private String liveAddr;
    @ApiModelProperty("身份证正面路径")
    private String idcardFacePath;
    @ApiModelProperty("身份证反面路径")
    private String idcardBackPath;
    @ApiModelProperty("驾驶证图片")
    private String driverLicensePath;
    @ApiModelProperty("紧急联系人关系")
    private String contactRelation;
    @ApiModelProperty("紧急联系人")
    private String contacter;
    @ApiModelProperty("紧急联系人电话")
    private String contactPhone;

}

