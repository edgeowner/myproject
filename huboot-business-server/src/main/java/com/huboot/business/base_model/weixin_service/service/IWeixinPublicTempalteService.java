package com.huboot.business.base_model.weixin_service.service;


import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.ZKWeixinMessageDTO;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinTempalteEntity;
import com.huboot.business.common.component.exception.BizException;

/**
 *微信模板信息表Service
 */
public interface IWeixinPublicTempalteService {

    /**
     * 初始化公众号信息
     * @param weixinUid
     */
    void initPublicTemplate(String weixinUid, Integer system);

    /**
     *
     * @param weixinUid
     */
    void deletePublicTemplate(String weixinUid);

    /**
     * 直客系统发送微信通知
     * @param dto
     * @throws BizException
     */
    void sendZKWeixinMessage(ZKWeixinMessageDTO dto) throws BizException;


    /**
     *
     * @param tempalteEntity
     * @param publicEntity
     */
    void setPublicTempalte(WeixinTempalteEntity tempalteEntity, WeixinPublicEntity publicEntity);

}
