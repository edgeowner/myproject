package com.huboot.user.user.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.AppAssert;
import com.huboot.commons.utils.CommonTools;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.user_service.api.dto.AreaDTO;
import com.huboot.share.user_service.data.AreaCacheData;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.share.user_service.enums.PersonalSexEnum;
import com.huboot.user.user.dto.wycdriverminiapp.*;
import com.huboot.user.user.dto.wycshop.PersonalEditDTO;
import com.huboot.user.user.entity.UserEntity;
import com.huboot.user.user.repository.IUserPersonalRepository;
import com.huboot.user.common.config.RefreshValue;

import com.huboot.user.user.dto.wycshop.PersonalInfoDTO;
import com.huboot.user.user.entity.UserPersonalEntity;
import com.huboot.user.user.repository.IUserRepository;
import com.huboot.user.user.service.IUserPersonalService;
import com.huboot.user.user.support.BaiduOCR;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *用户服务-个人信息表ServiceImpl
 */
@Service("userPersonalServiceImpl")
public class UserPersonalServiceImpl implements IUserPersonalService {

    private Logger logger = LoggerFactory.getLogger(UserPersonalServiceImpl.class);

    @Autowired
    private IUserPersonalRepository userPersonalRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private UserCacheData userCacheData;/*
    @Autowired
    private ReportFeignClient reportFeignClient;*/
    @Autowired
    private AreaCacheData areaCacheData;
    @Autowired
    private BaiduOCR baiduOCR;/*
    @Autowired
    private FileFeignClient fileFeignClient;*/
    @Autowired
    private RefreshValue refreshValue;

    @Override
    @Transactional
    public void submitAuth(Long userId, PersonalSubmitAuthDTO authDTO) {
        UserPersonalEntity entity = userPersonalRepository.findByUserId(userId);
        if(entity == null) {
            entity = new UserPersonalEntity();
            entity.setUserId(userId);
        }
        if(refreshValue.isIdCardAndDriverLic()) {
            if(!authDTO.getName().equals(authDTO.getLicName())) {
                throw new BizException("身份证名称和驾驶证名称不一致，请重新上传");
            }
            if(!authDTO.getNum().equals(authDTO.getLicNum())) {
                throw new BizException("身份证号和驾驶证号不一致，请重新上传");
            }
        }
        if(!StringUtils.isEmpty(entity.getNum())) {
            throw new BizException("认证信息已经提交，无需重复提交");
        }
        if(refreshValue.isIdCardCertificationOpen()) {
            /*String response = reportFeignClient.certification(authDTO.getName(), authDTO.getNum());
            Map<String, String> map = JsonUtil.buildNormalMapper().fromJson(response, HashMap.class);
            String code = map.get("code");
            if("-1".equals(code)) {
                throw new BizException("身份证验证异常");
            }
            if("0".equals(code)) {
                throw new BizException("无效身份证");
            }*/
        }
        entity.setIdcardFacePath(authDTO.getIdcardFacePath());
        entity.setIdcardBackPath(authDTO.getIdcardBackPath());
        entity.setName(authDTO.getName());
        entity.setNum(authDTO.getNum());
        entity.setDriverLicensePath(authDTO.getDriverLicensePath());
        entity.setLicName(authDTO.getLicName());
        entity.setLicNum(authDTO.getLicNum());
        entity.setLicGetDate(authDTO.getLicGetDate());
        entity.setLicCarModel(authDTO.getLicCarModel());
        entity.setLicValidity(authDTO.getLicValidity());
        //查询风控信息
        try {
            /*String response = reportFeignClient.v4(entity.getName(), entity.getNum(), userCacheData.getCurrentUser().getUser().getPhone());
            Map<String, String> map = JsonUtil.buildNormalMapper().fromJson(response, HashMap.class);
            entity.setRiskSn(map.get("sn"));*/
        } catch (Exception e) {
            logger.error("获取风控报告异常", e);
        }
        if(entity.getId() == null) {
            userPersonalRepository.create(entity);
        } else {
            userPersonalRepository.modify(entity);
        }
        //修改用户名称
        UserEntity userEntity = userRepository.find(userId);
        AppAssert.notNull(userEntity, "用户不存在");
        userEntity.setName(authDTO.getName());
        userRepository.modify(userEntity);
    }

    @Transactional
    @Override
    public void personalSetting(Long userId, PersonalSettingDTO settingDTO) {
        UserPersonalEntity entity = userPersonalRepository.findByUserId(userId);
        if(entity == null) {
            entity = new UserPersonalEntity();
            entity.setUserId(userId);
            entity = userPersonalRepository.create(entity);
        }
        //头像地址设置
        if(!StringUtils.isEmpty(settingDTO.getImagePath())) {
            UserEntity userEntity = userRepository.find(userId);
            AppAssert.notNull(userEntity, "用户不存在");
            userEntity.setImagePath(settingDTO.getImagePath());
            userRepository.modify(userEntity);
        }
        //设置名称
        if(!StringUtils.isEmpty(settingDTO.getName())) {
            if(!StringUtils.isEmpty(entity.getNum())) {
                throw new BizException("实名认证已通过，不能修改名称");
            }
            UserEntity userEntity = userRepository.find(userId);
            AppAssert.notNull(userEntity, "用户不存在");
            userEntity.setName(settingDTO.getName());
            userRepository.modify(userEntity);
            entity.setName(settingDTO.getName());
        }
        //性别
        if(!StringUtils.isEmpty(settingDTO.getSex())) {
            PersonalSexEnum.valueOf(settingDTO.getSex());
            entity.setSex(settingDTO.getSex());
        }
        //籍贯城市id
        if(!StringUtils.isEmpty(settingDTO.getBirthplace()) || settingDTO.getBirthplaceCityId() != null) {
            entity.setBirthplaceCityId(settingDTO.getBirthplaceCityId());
            entity.setBirthplace(settingDTO.getBirthplace());
        }
        //居住地区
        if(!StringUtils.isEmpty(settingDTO.getLiveAddr()) || settingDTO.getLiveAreaId() != null) {
            entity.setLiveAreaId(settingDTO.getLiveAreaId());
            entity.setLiveAreaAddr(settingDTO.getLiveAddr());
        }
        //紧急联系人关系
        if(!StringUtils.isEmpty(settingDTO.getContactRela())) {
            List<UserPersonalEntity.Contact> contactList = entity.getContacters();
            if(CollectionUtils.isEmpty(contactList)) {
                contactList = new ArrayList<>();
                UserPersonalEntity.Contact contact = new UserPersonalEntity.Contact();
                contact.setRelation(settingDTO.getContactRela());
                contactList.add(contact);
            } else {
                contactList.get(0).setRelation(settingDTO.getContactRela());
            }
            entity.setContacters(contactList);
        }
        //紧急联系人
        if(!StringUtils.isEmpty(settingDTO.getContactName())) {
            List<UserPersonalEntity.Contact> contactList = entity.getContacters();
            if(CollectionUtils.isEmpty(contactList)) {
                contactList = new ArrayList<>();
                UserPersonalEntity.Contact contact = new UserPersonalEntity.Contact();
                contact.setName(settingDTO.getContactName());
                contactList.add(contact);
            } else {
                contactList.get(0).setName(settingDTO.getContactName());
            }
            entity.setContacters(contactList);
        }
        //紧急联系人电话
        if(!StringUtils.isEmpty(settingDTO.getContactPhone())) {
            List<UserPersonalEntity.Contact> contactList = entity.getContacters();
            if(CollectionUtils.isEmpty(contactList)) {
                contactList = new ArrayList<>();
                UserPersonalEntity.Contact contact = new UserPersonalEntity.Contact();
                contact.setPhone(settingDTO.getContactPhone());
                contactList.add(contact);
            } else {
                contactList.get(0).setPhone(settingDTO.getContactPhone());
            }
            entity.setContacters(contactList);
        }
        userPersonalRepository.modify(entity);
    }

    @Override
    @Transactional
    public void personalEdit(Long userId, PersonalEditDTO editDTO) {
        UserPersonalEntity entity = userPersonalRepository.findByUserId(userId);
        if(entity == null) {
            entity = new UserPersonalEntity();
            entity.setUserId(userId);
            entity = userPersonalRepository.create(entity);
        }
        //设置名称
        if(!StringUtils.isEmpty(editDTO.getName())) {
            if(!StringUtils.isEmpty(entity.getNum())) {
                throw new BizException("实名认证已通过，不能修改名称");
            }
            UserEntity userEntity = userRepository.find(userId);
            AppAssert.notNull(userEntity, "用户不存在");
            userEntity.setName(editDTO.getName());
            userRepository.modify(userEntity);
            entity.setName(editDTO.getName());
        }
        //性别
        if(!StringUtils.isEmpty(editDTO.getSex())) {
            PersonalSexEnum.valueOf(editDTO.getSex());
            entity.setSex(editDTO.getSex());
        }
        //籍贯城市id
        if(!StringUtils.isEmpty(editDTO.getBirthplace()) || editDTO.getBirthplaceCityId() != null) {
            entity.setBirthplaceCityId(editDTO.getBirthplaceCityId());
            entity.setBirthplace(editDTO.getBirthplace());
        }
        //居住地区
        if(!StringUtils.isEmpty(editDTO.getLiveAddr()) || editDTO.getLiveAreaId() != null) {
            entity.setLiveAreaId(editDTO.getLiveAreaId());
            entity.setLiveAreaAddr(editDTO.getLiveAddr());
        }
        //紧急联系人关系
        if(!StringUtils.isEmpty(editDTO.getContactRelation())) {
            List<UserPersonalEntity.Contact> contactList = entity.getContacters();
            if(CollectionUtils.isEmpty(contactList)) {
                contactList = new ArrayList<>();
                UserPersonalEntity.Contact contact = new UserPersonalEntity.Contact();
                contact.setRelation(editDTO.getContactRelation());
                contactList.add(contact);
            } else {
                contactList.get(0).setRelation(editDTO.getContactRelation());
            }
            entity.setContacters(contactList);
        }
        //紧急联系人
        if(!StringUtils.isEmpty(editDTO.getContacter())) {
            List<UserPersonalEntity.Contact> contactList = entity.getContacters();
            if(CollectionUtils.isEmpty(contactList)) {
                contactList = new ArrayList<>();
                UserPersonalEntity.Contact contact = new UserPersonalEntity.Contact();
                contact.setName(editDTO.getContacter());
                contactList.add(contact);
            } else {
                contactList.get(0).setName(editDTO.getContacter());
            }
            entity.setContacters(contactList);
        }
        //紧急联系人电话
        if(!StringUtils.isEmpty(editDTO.getContactPhone())) {
            List<UserPersonalEntity.Contact> contactList = entity.getContacters();
            if(CollectionUtils.isEmpty(contactList)) {
                contactList = new ArrayList<>();
                UserPersonalEntity.Contact contact = new UserPersonalEntity.Contact();
                contact.setPhone(editDTO.getContactPhone());
                contactList.add(contact);
            } else {
                contactList.get(0).setPhone(editDTO.getContactPhone());
            }
            entity.setContacters(contactList);
        }
        userPersonalRepository.modify(entity);
    }


    @Override
    public PersonalInfoDTO getPersonalInfo(Long userId) {
        UserPersonalEntity personalEntity = userPersonalRepository.findByUserId(userId);
        if(personalEntity == null) {
            return null;
        }
        PersonalInfoDTO personalInfo = new PersonalInfoDTO();
        personalInfo.setIdcardFacePath(personalEntity.getIdcardFacePath());
        personalInfo.setIdcardBackPath(personalEntity.getIdcardBackPath());
        personalInfo.setName(personalEntity.getName());
        personalInfo.setNum(personalEntity.getNum());
        personalInfo.setDriverLicensePath(personalEntity.getDriverLicensePath());
        personalInfo.setLicName(personalEntity.getLicName());
        personalInfo.setLicNum(personalEntity.getLicNum());
        personalInfo.setLicCarModel(personalEntity.getLicCarModel());
        personalInfo.setLicGetDate(personalEntity.getLicGetDate());
        personalInfo.setLicValidity(personalEntity.getLicValidity());
        personalInfo.setSex(StringUtils.isEmpty(personalEntity.getSex()) ? "" : PersonalSexEnum.valueOf(personalEntity.getSex()).getShowName());
        personalInfo.setSexEnum(StringUtils.isEmpty(personalEntity.getSex()) ? "" : personalEntity.getSex());
        personalInfo.setBirthplaceCityId(personalEntity.getBirthplaceCityId());
        if(personalEntity.getBirthplaceCityId() != null) {
            AreaDTO city = areaCacheData.getById(personalEntity.getBirthplaceCityId());
            if(city != null) {
                personalInfo.setBirthplace(city.getFullName() + CommonTools.igem(personalEntity.getBirthplace()));
            } else {
                personalInfo.setBirthplace(CommonTools.igem(personalEntity.getBirthplace()));
            }
        } else {
            personalInfo.setBirthplace(CommonTools.igem(personalEntity.getBirthplace()));
        }
        personalInfo.setLiveAreaId(personalEntity.getLiveAreaId());
        if(personalEntity.getLiveAreaId() != null) {
            AreaDTO area = areaCacheData.getById(personalEntity.getLiveAreaId());
            if(area != null) {
                personalInfo.setLiveAddr(area.getFullName() + CommonTools.igem(personalEntity.getLiveAreaAddr()));
            } else {
                personalInfo.setLiveAddr(CommonTools.igem(personalEntity.getLiveAreaAddr()));
            }
        } else {
            personalInfo.setLiveAddr(CommonTools.igem(personalEntity.getLiveAreaAddr()));
        }
        personalInfo.setRiskSn(personalEntity.getRiskSn());
        List<UserPersonalEntity.Contact> contactList = personalEntity.getContacters();
        if(!CollectionUtils.isEmpty(contactList)) {
            UserPersonalEntity.Contact contact = contactList.get(0);
            personalInfo.setContactRelation(contact.getRelation());
            personalInfo.setContacter(contact.getName());
            personalInfo.setContactPhone(contact.getPhone());
        }
        return personalInfo;
    }

    @Override
    public PersonalSettingInfo getSettingInfo(Long userId) {
        PersonalSettingInfo settingInfo = new PersonalSettingInfo();
        UserEntity userEntity = userRepository.find(userId);
        AppAssert.notNull(userEntity, "用户不存在");
        settingInfo.setImagePath(userEntity.getImagePath());
        settingInfo.setUserId(userEntity.getId().toString());
        settingInfo.setPhone(userEntity.getPhone());
        UserPersonalEntity personalEntity = userPersonalRepository.findByUserId(userId);
        if(personalEntity == null) {
            settingInfo.setAuthStatus("no");
            return settingInfo;
        }
        if(!StringUtils.isEmpty(personalEntity.getNum())) {
            settingInfo.setAuthStatus("yes");
        } else {
            settingInfo.setAuthStatus("no");
        }
        settingInfo.setName(personalEntity.getName());
        settingInfo.setSex(StringUtils.isEmpty(personalEntity.getSex()) ? "" : PersonalSexEnum.valueOf(personalEntity.getSex()).name());
        settingInfo.setSexName(StringUtils.isEmpty(personalEntity.getSex()) ? "" : PersonalSexEnum.valueOf(personalEntity.getSex()).getShowName());
        settingInfo.setBirthplaceCityId(personalEntity.getBirthplaceCityId());
        if(personalEntity.getBirthplaceCityId() != null) {
            AreaDTO city = areaCacheData.getById(personalEntity.getBirthplaceCityId());
            if(city != null) {
                settingInfo.setBirthplaceCity(city.getFullName());
            }
        }
        settingInfo.setBirthplace(CommonTools.igem(personalEntity.getBirthplace()));
        settingInfo.setLiveAreaId(personalEntity.getLiveAreaId());
        if(personalEntity.getLiveAreaId() != null) {
            AreaDTO area = areaCacheData.getById(personalEntity.getLiveAreaId());
            if(area != null) {
                settingInfo.setLiveArea(area.getFullName());
            }
        }
        settingInfo.setLiveAddr(CommonTools.igem(personalEntity.getLiveAreaAddr()));
        List<UserPersonalEntity.Contact> contactList = personalEntity.getContacters();
        if(!CollectionUtils.isEmpty(contactList)) {
            UserPersonalEntity.Contact contact = contactList.get(0);
            settingInfo.setContactRela(contact.getRelation());
            settingInfo.setContactName(contact.getName());
            settingInfo.setContactPhone(contact.getPhone());
        }
        return settingInfo;
    }

    @Override
    public boolean authd(Long userId) {
        UserPersonalEntity personalEntity = userPersonalRepository.findByUserId(userId);
        if(personalEntity == null) {
            return false;
        } else {
            if(!StringUtils.isEmpty(personalEntity.getNum())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public IDCardORCDTO idCardOrc(MultipartFile file) throws Exception {
        IDCardORCDTO orcdto = baiduOCR.idCardOrc(file.getBytes(), BaiduOCR.ID_CARD_FRONT);
        /*FileDetailResDTO resDTO = fileFeignClient.create(file);
        orcdto.setIdcardFacePath(resDTO.getFullPath());*/
        return orcdto;
    }

    @Override
    public DriverLiscenseORCDTO driverLiscenseOrc(MultipartFile file) throws Exception  {
        DriverLiscenseORCDTO orcdto = baiduOCR.driverLiscenseOrc(file.getBytes());
        /*FileDetailResDTO resDTO = fileFeignClient.create(file);
        orcdto.setPath(resDTO.getFullPath());*/
        return orcdto;
    }
}
