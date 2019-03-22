package com.hpe.modifyControl.service;

import com.hpe.modifyControl.entity.ModifyControlSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Description:
 * Date: 2016/06/28
 *
 * @author hqr
 * @version 1.0
 */
@Repository
public interface ModifyControlSettingRepository extends JpaRepository<ModifyControlSetting, Integer> {

   /* @Query(value = "SELECT * from t_modify_control_setting",nativeQuery = true)
    List<ModifyControlSetting> findAllSettings();*/
}
