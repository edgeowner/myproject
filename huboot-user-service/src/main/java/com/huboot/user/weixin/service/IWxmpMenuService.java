package com.huboot.user.weixin.service;

import com.huboot.user.weixin.dto.wycshop.WxmpMenuParentDTO;
import org.springframework.ui.ModelMap;

import java.util.List;

/**
 *微信公众号菜单Service
 */
public interface IWxmpMenuService {

    ModelMap getWxmpSetMenuList();

    void saveWxmpMenuList(List<WxmpMenuParentDTO> menuList);
}
