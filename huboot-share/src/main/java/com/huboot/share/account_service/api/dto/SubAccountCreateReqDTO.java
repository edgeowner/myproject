package com.huboot.share.account_service.api.dto;

import com.huboot.share.account_service.enums.AccountTypeEnum;
import com.huboot.share.account_service.enums.SubAccountTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/8/31 0031.
 */
@Data
public class SubAccountCreateReqDTO implements Serializable {

    // AccountTypeEnum
    @NotNull(message = "主账户类型不能为空")
    private AccountTypeEnum type;

    //个人账户使用userid，公司账户使用companyid
    @NotBlank(message = "关联id不能为空")
    private String relaId;

    @NotNull(message = "关联组织id不能为空")
    private Long organizationId;
    //
    @NotEmpty(message = "子账户类型不能为空")
    private List<SubAccountTypeEnum> subAccountTypeList;

}
