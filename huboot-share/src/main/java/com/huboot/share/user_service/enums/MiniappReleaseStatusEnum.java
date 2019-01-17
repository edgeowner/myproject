package com.huboot.share.user_service.enums;

import com.huboot.commons.enums.BaseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MiniappReleaseStatusEnum implements BaseEnum {

    code_commit_success("代码提交成功"),
    code_commit_failure("代码提交失败"),
    check_commit_success("审核中"),//审核提交成功
    check_commit_failure("审核提交失败"),
    auth_success("审核通过"),
    auth_failure("审核不通过"),
    release_success("发布成功"),
    release_failure("发布失败"),
    ;

    private String showName;
}
