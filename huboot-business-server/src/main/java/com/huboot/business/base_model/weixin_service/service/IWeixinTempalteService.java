package com.huboot.business.base_model.weixin_service.service;

import com.huboot.business.base_model.weixin_service.dto.WeixinTempalteAddDTO;
import com.huboot.business.common.component.exception.BizException;

/**
 *微信模板信息表Service
 */
public interface IWeixinTempalteService {

    /**
    * 创建
    * @param dto
    * @throws BizException
    */
    Integer create(WeixinTempalteAddDTO dto, Integer initType) throws BizException;


    /**
     * 初始化添加公众号消息模板
     * @param stid
     */
    void initTempalteForAllPublic(Integer stid) throws BizException;

}
