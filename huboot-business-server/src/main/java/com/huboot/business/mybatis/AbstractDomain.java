package com.huboot.business.mybatis;

import java.sql.Timestamp;

/**
 * @author zhangtiebin@hn-zhixin.com
 * @ClassName: AbstractDomain
 * @Description: domain抽象类
 * @date 2015年6月24日 下午3:31:39
 */
public abstract class AbstractDomain<PK> {
    // 创建时间
    protected Timestamp createTime;
    // 创建人
    protected Long creatorId;
    // 修改时间
    protected Timestamp modifyTime;
    // 修改人
    protected Long modifierId;
    // 记录状态，默认：0 表示 删除 1 表示有效
    protected Integer recordStatus;
    // 分组时使用
    protected Integer groupCount;
    // 主健
    private Long id;

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
    }

    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Long getModifierId() {
        return modifierId;
    }

    public void setModifierId(Long modifierId) {
        this.modifierId = modifierId;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }


    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getGroupCount() {
        return groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }
}
