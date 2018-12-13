package com.huboot.business.mybatis;

import java.io.Serializable;

/**
 * 内管登录信息
 *
 * @author Tory.li
 * @create 2017-02-07 17:20
 **/
public class LoginAdmin implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long adminId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

}
