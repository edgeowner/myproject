package com.zchz.business.manager.elasticsearch;

import com.zchz.business.vo.car.CarQueryVO;
import com.zchz.business.vo.car.CarVO;
import com.zchz.business.vo.car.NavigationSkuVO;
import com.zchz.framework.exception.ApiException;

import java.util.List;

/**
 * 搜索-车型SKU Manager
 *
 * @author Tory.li
 * @create 2016-11-19 14:08
 **/
public interface EsCarManager {

    /**
     * 重建车型SKU索引库
     *
     * @throws ApiException
     */
    void rebuildIndex() throws ApiException;

    /**
     * 根据ID查询车型SKU详细信息
     *
     * @param id
     * @throws ApiException
     */
    CarVO findCarById(Long id) throws ApiException;

    /**
     * 根据品牌名称、品牌厂商名称、品牌车系名称查询车型SKU集合
     *
     * @param query
     * @return
     * @throws ApiException
     */
    List<NavigationSkuVO> findCarByQuery(CarQueryVO query) throws ApiException;
}
