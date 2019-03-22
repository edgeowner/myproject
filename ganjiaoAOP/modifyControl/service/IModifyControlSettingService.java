package com.hpe.modifyControl.service;


import com.hpe.modifyControl.entity.ModifyControlSetting;

import java.util.List;

/**
 * Description:
 * Date: 2016/07/04
 *
 * @author hqr
 * @version 1.0
 */
public interface IModifyControlSettingService {

    void add(ModifyControlSetting modifyControlSetting);

    void update(ModifyControlSetting modifyControlSetting);

    void remove(Integer id);

    ModifyControlSetting findById(Integer id);

    List<ModifyControlSetting> findAll();
}
