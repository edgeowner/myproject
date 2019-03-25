package com.zchz.business.service.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsCarDTO;
import com.zchz.business.dto.elasticsearch.EsSkuDTO;
import com.zchz.framework.exception.ApiException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 搜索-基础车型 Service
 *
 * @author Tory.li
 * @create 2016-11-19 13:51
 **/
public interface EsSkuService {

    /**
     * 更新记录
     *
     * @param dto
     * @throws ApiException
     */
    void saveSku(EsSkuDTO dto) throws ApiException;

    /**
     * 批量更新记录
     *
     * @param list
     * @throws ApiException
     */
    void saveSkuList(List<EsSkuDTO> list) throws ApiException;

    /**
     * 删除所有记录
     *
     * @throws ApiException
     */
    void deleteAll() throws ApiException;

    /**
     * 根据ID查询单个对象
     *
     * @param id
     * @return
     * @throws ApiException
     */
    EsSkuDTO findOne(String id) throws ApiException;

    /**
     * 根据ID组查询集合对象
     *
     * @param idList
     * @return
     * @throws ApiException
     */
    List<EsSkuDTO> findAll(List<String> idList) throws ApiException;

    public Page<EsSkuDTO> search(String brandName, String brandSupplierName, String brandCarSeriesName) throws ApiException;

    /**
     * 删除记录
     *
     * @param id
     * @throws ApiException
     */
    void removeSku(String id) throws ApiException;
}
