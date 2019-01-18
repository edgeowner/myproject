package com.huboot.user.weixin.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.utils.JsonUtil;
import com.huboot.share.user_service.data.UserCacheData;
import com.huboot.share.user_service.enums.WxmpMenuLevelEnum;
import com.huboot.share.user_service.enums.WxmpMenuTypeEnum;
import com.huboot.user.weixin.dto.WxMenuButtonDTO;
import com.huboot.user.weixin.dto.WxMenuDTO;
import com.huboot.user.weixin.dto.wycshop.WxmpMenuParentDTO;
import com.huboot.user.weixin.dto.wycshop.WxmpMenuSubDTO;
import com.huboot.user.weixin.dto.wycshop.WxmpMenuTempalteDTO;
import com.huboot.user.weixin.entity.WxmpMenuEntity;
import com.huboot.user.weixin.repository.IWxmpMenuRepository;
import com.huboot.user.weixin.service.IWeixinShopRelationService;
import com.huboot.user.weixin.service.IWxmpMenuService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.open.api.WxOpenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *微信公众号菜单ServiceImpl
 */
@Service("wxmpMenuServiceImpl")
public class WxmpMenuServiceImpl implements IWxmpMenuService {

    private Logger logger = LoggerFactory.getLogger(WxmpMenuServiceImpl.class);
    private static String weixinUrl = "http://mp.weixin.qq.com";
    private static String menuUrl = "https://api.weixin.qq.com/cgi-bin/menu/create";
    private static String menuRuleUrl = "https://api.weixin.qq.com/cgi-bin/menu/addconditional";

    @Autowired
    private IWxmpMenuRepository wxmpMenuRepository;
    @Autowired
    private UserCacheData userCacheData;
    @Autowired
    private IWeixinShopRelationService weixinShopRelationService;
    @Autowired
    private WxOpenService wxOpenService;
    @Autowired
    private WxmpMenuTempalteDTO wxmpMenuTempalteDTO;

    @Override
    public ModelMap getWxmpSetMenuList() {
        ModelMap map = new ModelMap();
        setMenuList(map);
        List<Map<String, String>> funclist = new ArrayList<>();
        logger.info("菜单funclist："+ JsonUtil.buildNormalMapper().toJson(wxmpMenuTempalteDTO.getMenuTempalteList()));
        wxmpMenuTempalteDTO.getMenuTempalteList().stream().forEach(menuTempalteDTO -> {
            Map<String, String> func = new HashMap<>();
            func.put("name", menuTempalteDTO.getName());
            func.put("type", WxmpMenuTypeEnum.miniprogram.getShowName());
            func.put("displayname", menuTempalteDTO.getDisplayname());
            func.put("url", menuTempalteDTO.getUrl());
            funclist.add(func);
        });
        map.put("funclist", funclist);
        return map;
    }

    private void setMenuList(ModelMap map) {
        String wxmpappId = weixinShopRelationService.findWxmpappIdByShopId(userCacheData.getCurrentUserWycShopId());
        if(StringUtils.isEmpty(wxmpappId)){
            throw new BizException("公众号appId不能为空,可能没有授权绑定");
        }
        List<WxmpMenuEntity> menuList = wxmpMenuRepository.findByWxmpappId(wxmpappId);
        List<WxmpMenuParentDTO> dtoList = new ArrayList<>();
        List<WxmpMenuEntity> parentMenuList = menuList.stream().filter(
                o -> WxmpMenuLevelEnum.frist.equals(o.getLevel())
        ).sorted((a, b) -> a.getSequence().compareTo(b.getSequence())).collect(Collectors.toList());

        parentMenuList.stream().forEach(menuEntity -> {
            WxmpMenuParentDTO parentDTO = new WxmpMenuParentDTO();
            parentDTO.setName(menuEntity.getName());
            parentDTO.setType(menuEntity.getType().getShowName());
            parentDTO.setOrder(menuEntity.getSequence());
            parentDTO.setUrl(menuEntity.getUrl());
            List<WxmpMenuSubDTO> subList = new ArrayList<>();
            List<WxmpMenuEntity> subMenuList = menuList.stream().filter(
                    menu -> WxmpMenuLevelEnum.second.equals(menu.getLevel()) && menu.getParentId().equals(menuEntity.getId())
            ).sorted((a, b) -> a.getSequence().compareTo(b.getSequence())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(subMenuList)) {
                subMenuList.stream().forEach(submenu -> {
                    WxmpMenuSubDTO subDTO = new WxmpMenuSubDTO();
                    subDTO.setName(submenu.getName());
                    subDTO.setType(submenu.getType().getShowName());
                    subDTO.setOrder(submenu.getSequence());
                    subDTO.setUrl(submenu.getUrl());
                    subList.add(subDTO);
                });
            }
            parentDTO.setSubList(subList);
            dtoList.add(parentDTO);
        });

        map.put("menulist", dtoList);
    }

    @Transactional
    @Override
    public void saveWxmpMenuList(List<WxmpMenuParentDTO> menuDTOList) {

        String wxmpappId = weixinShopRelationService.findWxmpappIdByShopId(userCacheData.getCurrentUserWycShopId());
        if(StringUtils.isEmpty(wxmpappId)){
            throw new BizException("公众号appId不能为空,可能没有授权绑定");
        }
        if(CollectionUtils.isEmpty(menuDTOList)){
            try {
                wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(wxmpappId).getMenuService().menuDelete();
            } catch (WxErrorException e) {
                logger.error("微信菜单删除异常", e);
                throw new BizException("微信菜单删除异常");
            }
        }else{
            if(menuDTOList.size() > 3) {
                throw new BizException("一级菜单个数不能大于3");
            }
            //全删
            List<WxmpMenuEntity> menuEntityList = wxmpMenuRepository.findByWxmpappId(wxmpappId);
            if(!CollectionUtils.isEmpty(menuEntityList)){
                wxmpMenuRepository.remove(menuEntityList);
            }
            //全增
            saveToWeiXin(saveToLocal(menuDTOList, wxmpappId),wxmpappId);
        }

    }



    /**
     * 同步菜单至微信
     * @param
     * @throws BizException
     */
    public void saveToWeiXin(List<WxmpMenuEntity> menuList, String wxmpappId) throws BizException {

        List<WxmpMenuEntity> parentMenuList = menuList.stream().filter(
                o -> WxmpMenuLevelEnum.frist.equals(o.getLevel())
        ).sorted((a, b) -> a.getSequence().compareTo(b.getSequence())).collect(Collectors.toList());
        WxMenuDTO menu = new WxMenuDTO();
        WxMpService wxMpService = wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(wxmpappId);
        String miniappId = weixinShopRelationService.findMiniappIdByShopId(userCacheData.getCurrentUserWycShopId());
        if(StringUtils.isEmpty(miniappId)){
            throw new BizException("小程序appId不能为空,可能没有授权绑定");
        }
        parentMenuList.stream().forEach(menuEntity -> {
            WxMenuButtonDTO fristMenu = new WxMenuButtonDTO();
            fristMenu.setName(menuEntity.getName());
            fristMenu.setType(menuEntity.getType().getShowName());
            fristMenu.setAppId(miniappId);
            fristMenu.setUrl(weixinUrl);
            fristMenu.setPagePath(menuEntity.getUrl());

            List<WxmpMenuEntity> subMenuList = menuList.stream().filter(submenu ->
                    WxmpMenuLevelEnum.second.equals(submenu.getLevel()) && submenu.getParentId().equals(menuEntity.getId())
            ).sorted((a, b) -> a.getSequence().compareTo(b.getSequence())).collect(Collectors.toList());
            subMenuList.stream().forEach(submenu -> {
                WxMenuButtonDTO secondButton = new WxMenuButtonDTO();
                secondButton.setName(submenu.getName());
                secondButton.setType(submenu.getType().getShowName());
                secondButton.setAppId(miniappId);
                secondButton.setUrl(weixinUrl);
                secondButton.setPagePath(submenu.getUrl());

                fristMenu.getSubButton().add(secondButton);
            });
            menu.getButton().add(fristMenu);
        });

        try {
            logger.info("生成微信菜单：{}", JsonUtil.buildNormalMapper().toJson(menu));

            String menuJson = menu.toJson();
            //此处用原始的方法调用而没有用MenuService，主要是参数实体属性button和buttons的梗
            String result = wxMpService.post(menu.getMatchRule() != null?menuRuleUrl:menuUrl, menuJson);
            //wxMpService.getMenuService().menuCreate(menu);
        } catch (Exception e) {
            logger.error("生成微信菜单异常", e);
            throw new BizException("微信菜单内容格式不正确，请重新填写");
        }
    }


    private List<WxmpMenuEntity> saveToLocal(List<WxmpMenuParentDTO> menuDTOList, String wxmpappId) {
        menuDTOList.stream().forEach(parentDTO -> {
            WxmpMenuEntity menuEntity = new WxmpMenuEntity();
            menuEntity.setWxmpappId(wxmpappId);
            menuEntity.setName(parentDTO.getName());
            menuEntity.setLevel(WxmpMenuLevelEnum.frist);
            menuEntity.setType(WxmpMenuTypeEnum.valueOf(parentDTO.getType()));
            menuEntity.setParentId(0L);
            menuEntity.setSequence(parentDTO.getOrder());
            menuEntity.setUrl(parentDTO.getUrl());
            menuEntity.setNeedAuth(2);
            wxmpMenuRepository.create(menuEntity);
            List<WxmpMenuSubDTO> subMenuList = parentDTO.getSubList();
            if(subMenuList.size() > 5) {
                throw new BizException("二级菜单个数不能大于5");
            }

            subMenuList.stream().forEach(subDTO -> {
                WxmpMenuEntity subMenuEntity = new WxmpMenuEntity();
                subMenuEntity.setWxmpappId(wxmpappId);
                subMenuEntity.setName(subDTO.getName());
                subMenuEntity.setLevel(WxmpMenuLevelEnum.second);
                subMenuEntity.setType(WxmpMenuTypeEnum.valueOf(subDTO.getType()));
                subMenuEntity.setParentId(menuEntity.getId());
                subMenuEntity.setSequence(subDTO.getOrder());
                subMenuEntity.setUrl(subDTO.getUrl());
                subMenuEntity.setNeedAuth(2);
                wxmpMenuRepository.create(subMenuEntity);

            });

        });

        List<WxmpMenuEntity> menuEntityList = wxmpMenuRepository.findByWxmpappId(wxmpappId);
        return menuEntityList;
    }

}
