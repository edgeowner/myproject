package com.huboot.business.base_model.weixin_service.dao.impl;

import com.huboot.business.base_model.weixin_service.dao.IWeixinPublicDao;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.common.jpa.DefaultBaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

/**
*商家微信公众号配置信息表DaoImpl
*/
@Repository("WeixinPublicDaoImpl")
public class WeixinPublicDaoImpl extends DefaultBaseDao implements IWeixinPublicDao {

    @Autowired
    @Override
    public void setEntityManager(EntityManager entityManager) {
        super.setEntityManager(entityManager);
    }


    @Override
    public Page<WeixinPublicEntity> getPublicPage(String shopUid, String publicUid, Integer page, Integer size) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select pub.* from xiehua_weixin_public pub left outer join xiehua_b2c_shop shop on shop.weixin_uid = pub.weixin_uid ");
        sql.append(" where pub.type = 1 and pub.system = 1 ");
        if(!StringUtils.isEmpty(publicUid)) {
            sql.append(" and pub.weixin_uid = :publicUid ");
            params.put("publicUid", publicUid);
        }
        if(!StringUtils.isEmpty(shopUid)) {
            sql.append(" and shop.shop_uid =  :shopUid ");
            params.put("shopUid", shopUid);
        }

        sql.append("order by pub.create_time desc ");
        return this.queryForPager(sql, WeixinPublicEntity.class, params, page, size);
    }

    @Override
    public Page<WeixinPublicEntity> getMiniappPage(String shopUid, String publicUid, Integer page, Integer size) {
        StringBuilder sql = new StringBuilder("");
        Map<String, Object> params = new HashMap<>();
        sql.append("select pub.* from xiehua_weixin_public pub left outer join xiehua_b2c_shop shop on shop.weixin_uid = pub.weixin_uid ");
        sql.append(" where pub.type = 2 and pub.system = 1 and pub.bind_type = 2 ");
        if(!StringUtils.isEmpty(publicUid)) {
            sql.append(" and pub.weixin_uid = :publicUid ");
            params.put("publicUid", publicUid);
        }
        if(!StringUtils.isEmpty(shopUid)) {
            sql.append(" and shop.shop_uid =  :shopUid ");
            params.put("shopUid", shopUid);
        }

        sql.append("order by pub.create_time desc ");
        return this.queryForPager(sql, WeixinPublicEntity.class, params, page, size);
    }
}