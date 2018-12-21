package com.huboot.business.base_model.weixin_service.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huboot.business.base_model.weixin_service.dto.WxMenuDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.WeixinPublicMenuParentDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.WeixinPublicMenuSubDTO;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicMenuRepository;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicMenuService;
import com.huboot.business.base_model.weixin_service.support.WechatMpFactory;
import com.huboot.business.base_model.weixin_service.config.WeixinConstant;
import com.huboot.business.base_model.weixin_service.dto.WxMenuButtonDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.enums.MenuEnum;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicMenuDTO;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicMenuEntity;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.menu.WxMpMenu;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *微信公众号菜单ServiceImpl
 */
@Service("weixinPublicMenuServiceImpl")
public class WeixinPublicMenuServiceImpl implements IWeixinPublicMenuService {

    private Logger logger = LoggerFactory.getLogger(WeixinPublicMenuServiceImpl.class);

    @Autowired
    private IWeixinPublicMenuRepository weixinPublicMenuRepository;
    @Autowired
    private WechatMpFactory wechatMpFactory;
    @Value("${huboot.domain.zkfront}")
    private String frontDomain;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;


    @Transactional
    @Override
    public void create(WeixinPublicMenuDTO dto) throws BizException {
        WeixinPublicMenuEntity entity = new WeixinPublicMenuEntity();
        BeanUtils.copyProperties(dto, entity);
        weixinPublicMenuRepository.create(entity);
    }

    public WxMpMenu getMenuFromWX(String weixinUid) {
        try {
            WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
            return wechatMpFactory.getWXMpService(publicEntity).getMenuService().menuGet();
        } catch (Exception e) {
            logger.error("获取微信菜单异常", e);
            throw new BizException("获取微信菜单异常");
        }
    }

    /**
     * 初始化菜单
     * @param weixinUid
     * @throws BizException
     */
    @Transactional
    @Override
    public void initMenu(String weixinUid, Integer opttype, String shopUid) throws BizException {
        if(StringUtils.isEmpty(weixinUid)) {
            throw new BizException("微信uid不能为空");
        }
        List<WeixinPublicMenuEntity> menuList = new ArrayList<>();
        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        if(1 == opttype) {
            menuList = getZKInitMenu(weixinUid, shopUid);
        } else if(2 == opttype) {
            menuList = weixinPublicMenuRepository.findByWeixinUid(weixinUid);
        }
        List<WeixinPublicMenuEntity> parentMenuList = menuList.stream().filter(
                o -> WeixinPublicMenuEntity.LevelEnum.frist.equals(o.getLevel())
        ).sorted((a, b) -> a.getSequence().compareTo(b.getSequence())).collect(Collectors.toList());
        WxMenuDTO menu = new WxMenuDTO();
        WxMpService wxMpService = wechatMpFactory.getWXMpService(publicEntity);
        for(WeixinPublicMenuEntity menuEntity : parentMenuList) {
            //view
            WxMenuButtonDTO fristMenu = new WxMenuButtonDTO();
            fristMenu.setName(menuEntity.getName());
            fristMenu.setType(menuEntity.getType());

            //
            List<WeixinPublicMenuEntity> subMenuList = menuList.stream().filter(submenu ->
                    WeixinPublicMenuEntity.LevelEnum.second.equals(submenu.getLevel()) && submenu.getParentId().equals(menuEntity.getId())
            ).sorted((a, b) -> a.getSequence().compareTo(b.getSequence())).collect(Collectors.toList());
            subMenuList.stream().forEach(submenu -> {
                WxMenuButtonDTO secondButton = new WxMenuButtonDTO();
                secondButton.setName(submenu.getName());
                secondButton.setType(submenu.getType());

                fristMenu.getSubButton().add(secondButton);
            });
            menu.getButton().add(fristMenu);
        }
        try {
            logger.info("生成微信菜单：{}", objectMapper.writeValueAsString(menu));

            String menuJson = menu.toJson();
            String url = "https://api.weixin.qq.com/cgi-bin/menu/create";
            if(menu.getMatchRule() != null) {
                url = "https://api.weixin.qq.com/cgi-bin/menu/addconditional";
            }
            String result = wxMpService.post(url, menuJson);
            //wxMpService.getMenuService().menuCreate(menu);
        } catch (Exception e) {
            logger.error("生成微信菜单异常", e);
            throw new BizException("微信菜单内容格式不正确，请重新填写");
        }
    }

    private String getMenuUrl(WeixinPublicMenuEntity menuEntity, WxMpService wxMpService) {
        if(1 == menuEntity.getNeedAuth()) {
            return wxMpService.oauth2buildAuthorizationUrl(menuEntity.getUrl(),
                    WeixinConstant.WEIXIN_SCOPE_USERINFO, WeixinConstant.WEIXIN_STATE);
        } else {
            return menuEntity.getUrl();
        }
    }

    /**
     * 初始化菜单内容
     * 1.店铺首页: /
     * 2.订单中心: /orderlist
     * 3.个人中心: /userHome
     * @param weixinUid
     * @return
     * @throws BizException
     */
    private List<WeixinPublicMenuEntity> getZKInitMenu(String weixinUid, String shopUid) throws BizException {
        this.deleteMenuByWeixinUid(weixinUid);
        List<WeixinPublicMenuEntity> menuList = new ArrayList<>();

        WeixinPublicMenuEntity homeMenu = new WeixinPublicMenuEntity();
        homeMenu.setWeixinUid(weixinUid);
        homeMenu.setName(MenuEnum.home.getName());
        homeMenu.setLevel(WeixinPublicMenuEntity.LevelEnum.frist);
        homeMenu.setType(WeixinPublicMenuEntity.TypeEnum.view.name());
        homeMenu.setParentId(0);
        homeMenu.setSequence(1);
        homeMenu.setUrl(frontDomain + MenuEnum.home.getUrl().replaceFirst("\\{0\\}", shopUid));
        homeMenu.setNeedAuth(2);
        menuList.add(homeMenu);

        WeixinPublicMenuEntity orderMenu = new WeixinPublicMenuEntity();
        orderMenu.setWeixinUid(weixinUid);
        orderMenu.setName(MenuEnum.orderlist.getName());
        orderMenu.setLevel(WeixinPublicMenuEntity.LevelEnum.frist);
        orderMenu.setType(WeixinPublicMenuEntity.TypeEnum.view.name());
        orderMenu.setParentId(0);
        homeMenu.setSequence(2);
        orderMenu.setUrl(frontDomain + MenuEnum.orderlist.getUrl().replaceFirst("\\{0\\}", shopUid));
        orderMenu.setNeedAuth(2);
        menuList.add(orderMenu);

        WeixinPublicMenuEntity userMenu = new WeixinPublicMenuEntity();
        userMenu.setWeixinUid(weixinUid);
        userMenu.setName(MenuEnum.usercenter.getName());
        userMenu.setLevel(WeixinPublicMenuEntity.LevelEnum.frist);
        userMenu.setType(WeixinPublicMenuEntity.TypeEnum.view.name());
        userMenu.setParentId(0);
        homeMenu.setSequence(3);
        userMenu.setUrl(frontDomain +  MenuEnum.usercenter.getUrl().replaceFirst("\\{0\\}", shopUid));
        userMenu.setNeedAuth(2);
        menuList.add(userMenu);

        return weixinPublicMenuRepository.create(menuList);
    }

    /**
     *
     * @param weixinUid
     */
    private void deleteMenuByWeixinUid(String weixinUid) {
        List<WeixinPublicMenuEntity> oldList = weixinPublicMenuRepository.findByWeixinUid(weixinUid);
        if(!CollectionUtils.isEmpty(oldList)) {
            try {
                weixinPublicMenuRepository.remove(oldList);
            } catch (IOException e) {
                throw new BizException("初始化菜单异常");
            }
        }
    }

    /**
     *
     * @param weixinUid
     * @return
     */
    @Override
    public List<WeixinPublicMenuParentDTO> getWeixinMentList(String weixinUid) {
        Assert.notNull(weixinUid, "微信uid不能为空");
        List<WeixinPublicMenuParentDTO> dtoList = new ArrayList<>();
        List<WeixinPublicMenuEntity> menuList = weixinPublicMenuRepository.findByWeixinUid(weixinUid);
        if(CollectionUtils.isEmpty(menuList)) {
           return  dtoList;
        }
        List<WeixinPublicMenuEntity> parentMenuList = menuList.stream().filter(
                o -> WeixinPublicMenuEntity.LevelEnum.frist.equals(o.getLevel())
        ).sorted((a, b) -> a.getSequence().compareTo(b.getSequence())).collect(Collectors.toList());
        for(WeixinPublicMenuEntity menuEntity : parentMenuList) {
            WeixinPublicMenuParentDTO parentDTO = new WeixinPublicMenuParentDTO();
            parentDTO.setName(menuEntity.getName());
            parentDTO.setType(menuEntity.getType());
            parentDTO.setOrder(menuEntity.getSequence());
            parentDTO.setUrl(menuEntity.getUrl());
            List<WeixinPublicMenuSubDTO> subList = new ArrayList<>();
            List<WeixinPublicMenuEntity> subMenuList = menuList.stream().filter(
                    menu -> WeixinPublicMenuEntity.LevelEnum.second.equals(menu.getLevel()) && menu.getParentId().equals(menuEntity.getId())
            ).sorted((a, b) -> a.getSequence().compareTo(b.getSequence())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(subMenuList)) {
                subMenuList.forEach(submenu -> {
                    WeixinPublicMenuSubDTO subDTO = new WeixinPublicMenuSubDTO();
                    subDTO.setName(submenu.getName());
                    subDTO.setType(submenu.getType());
                    subDTO.setOrder(submenu.getSequence());
                    subDTO.setUrl(submenu.getUrl());
                    subList.add(subDTO);
                });
            }
            parentDTO.setSubList(subList);
            dtoList.add(parentDTO);
        }

        return dtoList;
    }

    /**
     *
     * @param weixinUid
     * @param menuDTOList
     */
    @Transactional
    @Override
    public void saveWeixinMentList(String weixinUid, List<WeixinPublicMenuParentDTO> menuDTOList) {

        this.deleteMenuByWeixinUid(weixinUid);

        if(menuDTOList.size() > 3) {
            throw new BizException("一级菜单个数不能大于3");
        }

        if(CollectionUtils.isEmpty(menuDTOList)) {
            WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
            try {
                wechatMpFactory.getWXMpService(publicEntity).getMenuService().menuDelete();
            } catch (WxErrorException e) {
                logger.error("微信菜单删除异常", e);
                throw new BizException("微信菜单删除异常");
            }
            return;
        }

        for(WeixinPublicMenuParentDTO parentDTO : menuDTOList) {
            WeixinPublicMenuEntity menuEntity = new WeixinPublicMenuEntity();
            menuEntity.setWeixinUid(weixinUid);
            menuEntity.setName(parentDTO.getName());
            menuEntity.setLevel(WeixinPublicMenuEntity.LevelEnum.frist);
            menuEntity.setType(parentDTO.getType());
            menuEntity.setParentId(0);
            menuEntity.setSequence(parentDTO.getOrder());
            menuEntity.setUrl(parentDTO.getUrl());
            menuEntity.setNeedAuth(2);
            weixinPublicMenuRepository.create(menuEntity);
            List<WeixinPublicMenuSubDTO> subMenuList = parentDTO.getSubList();
            if(subMenuList.size() > 5) {
                throw new BizException("二级菜单个数不能大于5");
            }
            for(WeixinPublicMenuSubDTO subDTO : subMenuList) {
                WeixinPublicMenuEntity subMenuEntity = new WeixinPublicMenuEntity();
                subMenuEntity.setWeixinUid(weixinUid);
                subMenuEntity.setName(subDTO.getName());
                subMenuEntity.setLevel(WeixinPublicMenuEntity.LevelEnum.second);
                subMenuEntity.setType(subDTO.getType());
                subMenuEntity.setParentId(menuEntity.getId());
                subMenuEntity.setSequence(subDTO.getOrder());
                subMenuEntity.setUrl(subDTO.getUrl());
                subMenuEntity.setNeedAuth(2);
                weixinPublicMenuRepository.create(subMenuEntity);
            }
        }
        //ZkShopRespDTO shopRespDTO = bmwB2cShopFeign.findShopByWeixinUid(weixinUid);
        this.initMenu(weixinUid, 2, "");
    }
}
