package com.huboot.commons.jpa;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

/**
 * 基础服务-系统中心-区域表
 */
@Entity
@Table(name = "bs_system_data_recycle")
@DynamicInsert
@DynamicUpdate
@Data
public class SystemDataRecycleEntity extends AbstractEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    //表名
    private String tableName;
    //内容
    @Convert(converter = JpaConverterJson.class)
    private List<?> content;

}

