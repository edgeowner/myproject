package com.huboot.business.common.jpa;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Created by xlaoy on 2017/12/23 0023.
 */
@MappedSuperclass
@Data
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    protected String creatorId;

    protected String modifierId;

    protected LocalDateTime createTime;

    protected LocalDateTime updateTime;

}
