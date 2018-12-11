package com.huboot.business.common.jpa.datarecycle.entity;

import com.huboot.business.common.jpa.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
*保存删除数据
*/
@Entity
@Table(name = "zb_system_data_recycle")
@DynamicInsert
@DynamicUpdate
@Data
public class SystemDataRecycleEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //实体
    private String entity;
    //内容
    private String content ;

}

