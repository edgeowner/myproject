package com.huboot.business.base_model.weixin_service.dao.impl;

import com.huboot.business.base_model.weixin_service.dao.IWeixinPublicSettingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicSettingRepository;

/**
*商家微信公众号配置信息表DaoImpl
*/
@Repository("weixinPublicSettingDaoImpl")
public class WeixinPublicSettingDaoImpl implements IWeixinPublicSettingDao {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private IWeixinPublicSettingRepository repository;

}