package com.zchz.business.manager.elasticsearch.impl;


import com.zchz.business.domain.car.CarDomain;
import com.zchz.business.dto.elasticsearch.EsCarDTO;
import com.zchz.business.manager.elasticsearch.EsCarManager;
import com.zchz.business.service.car.ICarService;
import com.zchz.business.service.elasticsearch.EsCarService;
import com.zchz.business.vo.car.CarQueryVO;
import com.zchz.business.vo.car.CarVO;
import com.zchz.business.vo.car.NavigationSkuVO;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.enums.system.ErrorCodeEnum;
import com.zchz.framework.model.pager.Pager;
import com.zchz.framework.utils.ListCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 搜索-车型SKU ManagerImpl
 *
 * @author Tory.li
 * @create 2016-11-22 13:36
 **/
@Service("esCarManagerImpl")
public class CarManagerImpl implements EsCarManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ICarService carService;
    @Autowired
    private EsCarService esCarService;

    @Override
    public void rebuildIndex() throws ApiException {
        //删除车型SKU索引库
        esCarService.deleteAll();

        //循环新增document,一次1000条记录
        int currentPage = 1;
        do {

            Pager<CarDomain> pager = carService.findForPager(new CarDomain(), currentPage, 1000);
            if (pager == null || CollectionUtils.isEmpty(pager.getPageItems())) {
                break;
            }
            List<EsCarDTO> list = ListCopyUtil.copyAsList(pager.getPageItems().iterator(), EsCarDTO.class);
            esCarService.saveCarList(list);
            logger.info("Indexing zchz/car complete with " + pager.getCurrPage() + "/" + pager.getPageCount() + " Total rows is " + pager.getPageRowsCount());
            currentPage++;
        } while (true);
    }

    @Override
    public CarVO findCarById(Long id) throws ApiException {
        EsCarDTO esCarDTO = esCarService.findOne(id);
        if (esCarDTO == null) {
            throw new ApiException(ErrorCodeEnum.NotFound);
        }
        CarVO carVO = new CarVO();
        BeanUtils.copyProperties(esCarDTO, carVO);
        return carVO;
    }

    @Override
    public List<NavigationSkuVO> findCarByQuery(CarQueryVO query) throws ApiException {
        Page<EsCarDTO> esCarDTOPage = esCarService.search(query.getBrandName(), query.getBrandSupplierName(), query.getBrandCarSeriesName());
        List<NavigationSkuVO> navigationSkuVOList = ListCopyUtil.copyAsList(esCarDTOPage.iterator(), NavigationSkuVO.class);
        return navigationSkuVOList;
    }
}
