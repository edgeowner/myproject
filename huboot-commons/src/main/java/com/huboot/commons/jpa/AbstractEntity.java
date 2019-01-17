package com.huboot.commons.jpa;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * Created by xlaoy on 2017/12/23 0023.
 */
@MappedSuperclass
@Getter
@Setter
public abstract class AbstractEntity implements Serializable {
    /**
     * https://blog.csdn.net/u011781521/article/details/72210980
     * https://blog.csdn.net/linan0930/article/details/21108411
     */
    @Id
    protected Long id;

    protected Long creatorId;

    protected Long modifierId;

    protected LocalDateTime createTime;

    protected LocalDateTime modifyTime;

}
