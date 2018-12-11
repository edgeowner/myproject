package com.huboot.business.base_model.weixin_service.service;

import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.WeixinPublicMenuParentDTO;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicMenuDTO;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;

import java.util.List;

/**
 *微信公众号菜单Service
 */
public interface IWeixinPublicMenuService {

    /**
    * 创建
    * @param dto
    * @throws BizException
    */
    void create(WeixinPublicMenuDTO dto) throws BizException;

    /**
     * 初始化菜单
     *
     * @param weixinUid
     * @param opttype 1-微信初始化时创建，2-后期创建
     * @throws BizException
     */
    void initMenu(String weixinUid, Integer opttype, String shopUid) throws BizException;


    /**
     * 从微信服务器获取公众号菜单
     * @param weixinUid
     * @return
     */
    WxMpMenu getMenuFromWX(String weixinUid);

    /**
     *
     * @param weixinUid
     * @return
     */
    List<WeixinPublicMenuParentDTO> getWeixinMentList(String weixinUid);

    /**
     *
     * @param menuList
     */
    void saveWeixinMentList(String weixinUid, List<WeixinPublicMenuParentDTO> menuList);

}
