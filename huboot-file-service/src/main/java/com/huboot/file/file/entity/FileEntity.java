package com.huboot.file.file.entity;

import java.io.Serializable;

import com.huboot.commons.jpa.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
*文件服务-文件信息表
*/
@Entity
@Table(name = "fs_file")
@DynamicInsert
@DynamicUpdate
@Data
public class FileEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;
    //名称
    private String name ;
    //路径
    private String path ;
    //扩展名
    private String ext ;
    //大小
    private String fileSize ;
    //全路径
    private String fullPath ;

}

