package com.huboot.business.common.jpa.datarecycle.repository;

import com.huboot.business.common.jpa.IBaseRepository;
import com.huboot.business.common.jpa.datarecycle.entity.SystemDataRecycleEntity;
import org.springframework.stereotype.Repository;

/**
*保存删除数据Repository
*/
@Repository("systemRemoveDataRepository")
public interface ISystemDataRecycleRepository extends IBaseRepository<SystemDataRecycleEntity> {

}
