package com.zchz.business.service.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsShopCarDTO;
import com.zchz.framework.exception.ApiException;
import org.elasticsearch.action.search.SearchResponse;

import java.util.List;

/**
 * 搜索-店铺车型 Service
 *
 * @author Tory.li
 * @create 2016-11-19 13:51
 **/
public interface EsShopCarService {

    /**
     * 批量更新记录
     *
     * @param list
     * @throws ApiException
     */
    void saveShopCarList(List<EsShopCarDTO> list) throws ApiException;

    /**
     * 更新一条记录
     *
     * @param dto
     * @throws ApiException
     */
    void saveShopCar(EsShopCarDTO dto) throws ApiException;

    /**
     * 删除所有记录
     *
     * @throws ApiException
     */
    void deleteAll() throws ApiException;

    /**
     * 根据车系ID、租用时间开始、租用时间结束查询聚合出的店铺记录
     *
     * @param carSeriesId
     * @param startTime
     * @param endTime
     * @return
     * @throws ApiException
     */
    SearchResponse search(Long carSeriesId, Long startTime, Long endTime) throws ApiException;

}
