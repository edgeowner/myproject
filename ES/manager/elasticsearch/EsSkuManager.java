package com.zchz.business.manager.elasticsearch;

import com.zchz.business.vo.car.CarQueryVO;
import com.zchz.business.vo.car.NavigationSkuVO;
import com.zchz.framework.exception.ApiException;

import java.util.List;

/**
 * 搜索-基础车型 Manager
 *
 * @author Tory.li
 * @create 2016-11-19 14:08
 **/
public interface EsSkuManager {

    /**
     * 重建车型SKU索引库
     *
     * @throws ApiException
     */
    void rebuildIndex() throws ApiException;

    /**
     * 根据品牌名称、品牌厂商名称、品牌车系名称查询车型SKU集合
     *
     * @param query
     * @return
     * @throws ApiException
     */
    String findCarByQuery(CarQueryVO query) throws ApiException;
    /**
     * 重建某个ID车型SKU索引
     *
     * @throws ApiException
     */
     void rebuildIndexById(Long skuId) throws ApiException;

    /**
     * 获取直客订单车型筛选列表
     *
     * @return
     */
    List<String> customPriceSkus();

}
