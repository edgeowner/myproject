package com.hpe.modifyControl.entity;

import com.hpe.log.entity.LogSettingProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * Date: 2016/06/29
 *
 * @author hqr
 * @version 1.0
 */
@Entity
@Table(name = "t_modify_control_setting")
public class ModifyControlSetting implements Serializable {

    private static final long serialVersionUID = -4305137716988481458L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //主键ID

    private String tableName; //表名
    private String displayName; //显示名

    private Date createDate; //创建时间
    private String createUser; //创建用户

    public ModifyControlSetting() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {

        this.createUser = createUser;
    }
}
