package com.huboot.user.weixin.service;


import com.huboot.commons.page.ShowPageImpl;
import com.huboot.user.weixin.dto.admin.WeixinShopRelationPagerDTO;

/**
 * Created by Administrator on 2018/12/3 0003.
 */
public interface IWeixinShopRelationService {

    String findMiniappIdByShopId(Long shopId);

    Long findShopIdByMiniappId(String miniappId);

    String findWxmpappIdByShopId(Long shopId);

    Long findShopIdByWxmpappId(String wxmpappId);

    //
    void saveMiniappRelation(String miniappId, Long shopId);

    //
    void saveWxmpRelation(String wxmpId, Long shopId);

    ShowPageImpl<WeixinShopRelationPagerDTO> getPager(Integer page, Integer size);

    void initOpenapp();
}
