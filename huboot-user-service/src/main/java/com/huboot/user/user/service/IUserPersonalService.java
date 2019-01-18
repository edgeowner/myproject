package com.huboot.user.user.service;


import com.huboot.user.user.dto.wycdriverminiapp.*;
import com.huboot.user.user.dto.wycshop.PersonalEditDTO;
import com.huboot.user.user.dto.wycshop.PersonalInfoDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 *用户服务-个人信息表Service
 */
public interface IUserPersonalService {

    //个人认证
    void submitAuth(Long userId, PersonalSubmitAuthDTO authDTO);

    //个人设置
    void personalSetting(Long userId, PersonalSettingDTO settingDTO);

    //个人信息编辑
    void personalEdit(Long userId, PersonalEditDTO settingDTO);

    PersonalInfoDTO getPersonalInfo(Long userId);

    PersonalSettingInfo getSettingInfo(Long userId);

    IDCardORCDTO idCardOrc(MultipartFile file) throws Exception;

    DriverLiscenseORCDTO driverLiscenseOrc(MultipartFile file) throws Exception;

    //检验个人认证是否通过
    boolean authd(Long userId);
}
