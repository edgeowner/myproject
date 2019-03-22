package com.hpe.modifyControl.service;

import com.hpe.modifyControl.entity.ModifyControlSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:
 * Date: 2016/07/04
 *
 * @author hqr
 * @version 1.0
 */
@Service
public class ModifyControlSettingService implements IModifyControlSettingService {

    @Autowired
    private ModifyControlSettingRepository modifyControlSettingRepository;

    @Override
    public void add(ModifyControlSetting modifyControlSetting) {
        modifyControlSettingRepository.save(modifyControlSetting);
    }

    @Override
    public void update(ModifyControlSetting modifyControlSetting) {
        modifyControlSettingRepository.save(modifyControlSetting);
    }

    @Override
    public void remove(Integer id) {
        modifyControlSettingRepository.delete(id);
    }

    @Override
    public ModifyControlSetting findById(Integer id) {
        return modifyControlSettingRepository.findOne(id);
    }

    @Override
    public List<ModifyControlSetting> findAll() {
        List<ModifyControlSetting> controlSettingList = modifyControlSettingRepository.findAll();
        return controlSettingList;
    }
}
