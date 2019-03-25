package com.zchz.business.manager.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsShopCarDTO;
import com.zchz.framework.exception.ApiException;
import io.swagger.annotations.Api;

/**
 * 搜索-店铺车辆 Manager
 *
 * @author Tory.li
 * @create 2016-11-19 14:08
 **/
public interface EsShopCarManager {

    /**
     * 重建店铺车辆索引库
     *
     * @throws ApiException
     */
    void rebuildIndex() throws ApiException;

    /**
     * 新增店铺车辆
     *
     * @param dto
     * @throws ApiException
     */
    void create(EsShopCarDTO dto) throws ApiException;

    /**
     * 更新店铺车辆
     *
     * @param dto
     * @throws ApiException
     */
    void update(EsShopCarDTO dto) throws ApiException;

}
