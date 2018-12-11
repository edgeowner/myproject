package com.huboot.business.base_model.weixin_service.service.impl;

import com.huboot.business.base_model.weixin_service.service.IWeixinPublicSettingService;
import com.huboot.business.common.component.exception.BizException;
import org.springframework.data.domain.Sort;
import com.huboot.business.common.jpa.QueryCondition;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicSettingRepository;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicSettingDTO;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicSettingQueryDTO;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicSettingEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *商家微信公众号配置信息表ServiceImpl
 */
@Service("weixinPublicSettingServiceImpl")
public class WeixinPublicSettingServiceImpl implements IWeixinPublicSettingService {

    private Logger logger = LoggerFactory.getLogger(WeixinPublicSettingServiceImpl.class);

    @Autowired
    private IWeixinPublicSettingRepository weixinPublicSettingRepository;

    @Transactional
    @Override
    public void create(WeixinPublicSettingDTO dto) throws BizException {
        WeixinPublicSettingEntity entity = new WeixinPublicSettingEntity();
        BeanUtils.copyProperties(dto, entity);
        weixinPublicSettingRepository.create(entity);
    }

    @Transactional
    @Override
    public void update(WeixinPublicSettingDTO dto) throws BizException {
        WeixinPublicSettingEntity entity = new WeixinPublicSettingEntity();
        BeanUtils.copyProperties(dto, entity);
        weixinPublicSettingRepository.update(entity);
    }

    @Transactional
    @Override
    public void delete(Integer id) throws BizException {
        try {
            weixinPublicSettingRepository.remove(id);
        } catch (IOException e) {
            throw new BizException("数据删除错误！");
        }
    }

    @Override
    public WeixinPublicSettingDTO find(Integer id) throws BizException {
        WeixinPublicSettingEntity entity = weixinPublicSettingRepository.find(id);
        WeixinPublicSettingDTO dto = new WeixinPublicSettingDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public Page<WeixinPublicSettingDTO> findPage(WeixinPublicSettingQueryDTO queryDTO) throws BizException {

        Page<WeixinPublicSettingEntity> page = weixinPublicSettingRepository.findPage(QueryCondition.from(WeixinPublicSettingEntity.class).where(list -> {

        }).sort(Sort.by("createTime")).limit(queryDTO.getPage(), queryDTO.getSize()));

        return page.map(entity -> {
            WeixinPublicSettingDTO dto = new WeixinPublicSettingDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        });
    }


    @Override
    public void createByParam(String weixinUid,WeixinPublicSettingEntity.SetTypeEnum setType, String setParameter, String setResult, WeixinPublicSettingEntity.StatusEnum status) throws BizException {
        WeixinPublicSettingEntity entity = new WeixinPublicSettingEntity();
        entity.setSetParameter(setParameter);
        entity.setSetResult(setResult);
        entity.setSetType(setType);
        entity.setStatus(status);
        entity.setWeixinUid(weixinUid);
        weixinPublicSettingRepository.create(entity);
    }


    @Override
    public Boolean checkPublicSetting(String weixinUid) throws BizException {
        //校验最近的一次设置结果，以最后一次的设置结果为准
        List<WeixinPublicSettingEntity> serverSettingEntityList = weixinPublicSettingRepository.findByWeixinUidAndSetTypeOrderByUpdateTimeDesc(weixinUid,WeixinPublicSettingEntity.SetTypeEnum.serverDomainName);
        if(CollectionUtils.isEmpty(serverSettingEntityList) || WeixinPublicSettingEntity.StatusEnum.domainSetFailure.equals(serverSettingEntityList.get(0).getStatus())) {
            throw new BizException("小程序服务器域名没有设置成功，请先设置！");
        }
        List<WeixinPublicSettingEntity> businessSettingEntityList = weixinPublicSettingRepository.findByWeixinUidAndSetTypeOrderByUpdateTimeDesc(weixinUid,WeixinPublicSettingEntity.SetTypeEnum.businessDomainName);
        if(CollectionUtils.isEmpty(businessSettingEntityList) || WeixinPublicSettingEntity.StatusEnum.domainSetFailure.equals(businessSettingEntityList.get(0).getStatus())) {
            throw new BizException("小程序业务域名没有设置成功，请先设置！");
        }
        List<WeixinPublicSettingEntity> weappSettingEntityList = weixinPublicSettingRepository.findByWeixinUidAndSetTypeOrderByUpdateTimeDesc(weixinUid,WeixinPublicSettingEntity.SetTypeEnum.weappSupportVersion);
        if(CollectionUtils.isEmpty(weappSettingEntityList) || WeixinPublicSettingEntity.StatusEnum.domainSetFailure.equals(weappSettingEntityList.get(0).getStatus())) {
            throw new BizException("小程序版本库没有设置成功，请先设置！");
        }
        return true;
    }


    @Override
    public Map<String, String> getSetInfo(String weixinUid) {
        Map<String, String> map = new HashMap<>();
        String requestDomain = "";
        String viewDomain = "";
        String weappVersion = "";

        List<WeixinPublicSettingEntity> serverSettingEntityList = weixinPublicSettingRepository.findByWeixinUidAndSetTypeOrderByUpdateTimeDesc(weixinUid,WeixinPublicSettingEntity.SetTypeEnum.serverDomainName);
        if(!CollectionUtils.isEmpty(serverSettingEntityList)) {
            requestDomain = serverSettingEntityList.get(0).getSetParameter();
        }

        List<WeixinPublicSettingEntity> businessSettingEntityList = weixinPublicSettingRepository.findByWeixinUidAndSetTypeOrderByUpdateTimeDesc(weixinUid,WeixinPublicSettingEntity.SetTypeEnum.businessDomainName);
        if(!CollectionUtils.isEmpty(businessSettingEntityList)) {
            viewDomain = businessSettingEntityList.get(0).getSetParameter();
        }

        List<WeixinPublicSettingEntity> weappSettingEntityList = weixinPublicSettingRepository.findByWeixinUidAndSetTypeOrderByUpdateTimeDesc(weixinUid,WeixinPublicSettingEntity.SetTypeEnum.weappSupportVersion);
        if(!CollectionUtils.isEmpty(weappSettingEntityList)) {
            weappVersion = weappSettingEntityList.get(0).getSetParameter();
        }

        map.put("requestDomain", requestDomain);
        map.put("viewDomain", viewDomain);
        map.put("weappVersion", weappVersion);
        return map;
    }
}
