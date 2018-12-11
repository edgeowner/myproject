package com.huboot.business.base_model.weixin_service.service;

import com.huboot.business.base_model.weixin_service.entity.WeixinPublicSettingEntity;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicSettingDTO;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicSettingQueryDTO;
import org.springframework.data.domain.Page;

import java.util.Map;

/**
 *商家微信公众号配置信息表Service
 */
public interface IWeixinPublicSettingService {

    /**
    * 创建
    * @param dto
    * @throws BizException
    */
    void create(WeixinPublicSettingDTO dto) throws BizException;

    /**
    * 查询
    * @param id
    * @return
    * @throws BizException
    */
    WeixinPublicSettingDTO find(Integer id) throws BizException;

    /**
    * 更新
    * @param dto
    * @throws BizException
    */
    void update(WeixinPublicSettingDTO dto) throws BizException;

    /**
    * 删除
    * @param id
    * @throws BizException
    */
    void delete(Integer id) throws BizException;

    /**
    * 分页查询
    * @param queryDTO
    * @return
    * @throws BizException
    */
    Page<WeixinPublicSettingDTO> findPage(WeixinPublicSettingQueryDTO queryDTO) throws BizException;


    /**
     * 根据具体参数创建
     * @param
     * @throws BizException
     */
    void createByParam(String weixinUid,WeixinPublicSettingEntity.SetTypeEnum setType,String setParameter,String setResult,WeixinPublicSettingEntity.StatusEnum status) throws BizException;


    /**
     * 检查小程序各个域名和版本库是否设置成功
     * @param weixinUid
     * @throws BizException
     */
    Boolean checkPublicSetting(String weixinUid) throws BizException;

    Map<String, String> getSetInfo(String weixinUid);
}
