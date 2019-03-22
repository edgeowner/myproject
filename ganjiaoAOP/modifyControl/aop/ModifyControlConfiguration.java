package com.hpe.modifyControl.aop;

import com.hpe.modifyControl.aop.common.ModifyControlContext;
import com.hpe.modifyControl.aop.impl.DefaultModifyControlContext;
import com.hpe.modifyControl.entity.ModifyControlSetting;
import com.hpe.modifyControl.service.IModifyControlSettingService;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Description: 修改监控配置类
 * Date: 2016/06/29
 *
 * @author hqr
 * @version 1.0
 */
@Component
public class ModifyControlConfiguration {

    List<ModifyControlSetting> modifyControlSettings;
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public ModifyControlConfiguration(IModifyControlSettingService modifyControlSettingService) {
        modifyControlSettings = modifyControlSettingService.findAll();
    }

    public ModifyControlContext getModifyControlContext(JoinPoint joinPoint) {
        return new DefaultModifyControlContext(modifyControlSettings, entityManager, joinPoint);
    }
}
