package com.zchz.business.manager.elasticsearch.impl;


import com.zchz.business.domain.area.AreaDomain;
import com.zchz.business.domain.shop.ShopCarDomain;
import com.zchz.business.domain.shop.ShopDomain;
import com.zchz.business.dto.elasticsearch.EsShopCarDTO;
import com.zchz.business.manager.elasticsearch.EsShopCarManager;
import com.zchz.business.service.area.IAreaService;
import com.zchz.business.service.elasticsearch.EsShopCarService;
import com.zchz.business.service.shop.IShopCarService;
import com.zchz.business.service.shop.IShopService;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.enums.system.ErrorCodeEnum;
import com.zchz.framework.model.pager.Pager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索-店铺车辆 ManagerImpl
 *
 * @author Tory.li
 * @create 2016-11-22 13:36
 **/
@Service("esShopCarManagerImpl")
public class ShopCarManagerImpl implements EsShopCarManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IShopCarService shopCarService;
    @Autowired
    private IShopService shopService;
    @Autowired
    private EsShopCarService esShopCarService;
    @Autowired
    private IAreaService areaService;

    @Override
    public void rebuildIndex() throws ApiException {
        //删除店铺车辆索引库
        esShopCarService.deleteAll();

        //循环新增document,一次1000条记录
        int currentPage = 1;
        do {

            Pager<ShopCarDomain> pager = shopCarService.findForPager(new ShopCarDomain(), currentPage, 1000);
            if (pager == null || CollectionUtils.isEmpty(pager.getPageItems())) {
                break;
            }
            List<EsShopCarDTO> list = new ArrayList<>();
            for (ShopCarDomain domain : pager.getPageItems()) {
                EsShopCarDTO dto = new EsShopCarDTO();
                BeanUtils.copyProperties(domain, dto);
                if (domain.getStartTime() != null)
                    dto.setStartTime(domain.getStartTime().getTime());
                if (domain.getEndTime() != null)
                    dto.setEndTime(domain.getEndTime().getTime());
                ShopDomain shopDomain = shopService.find(domain.getShopId());
                if(shopDomain == null) {
                    logger.info("Indexing zchz/shopCar error, cause by shop not found");
                    continue;
                }
                AreaDomain districtAreaDomain = areaService.find(shopDomain.getAreaId());
                if(districtAreaDomain == null) {
                    logger.info("Indexing zchz/shopCar error, cause by area not found");
                    continue;
                }
                AreaDomain cityAreaDomain = areaService.find(districtAreaDomain.getParentId());
                dto.setCityAreaId(cityAreaDomain.getId());

                list.add(dto);
            }
            esShopCarService.saveShopCarList(list);
            logger.info("Indexing zchz/shopCar complete with " + pager.getCurrPage() + "/" + pager.getPageCount() + " Total rows is " + pager.getPageRowsCount());
            currentPage++;
        } while (true);
    }

    @Override
    public void create(EsShopCarDTO dto) throws ApiException {
        esShopCarService.saveShopCar(dto);
    }

    @Override
    public void update(EsShopCarDTO dto) throws ApiException {
        if (dto.getId() == null) {
            throw new ApiException(ErrorCodeEnum.NOTNULL, "缺少店铺车辆主键");
        }
        esShopCarService.saveShopCar(dto);
    }
}
