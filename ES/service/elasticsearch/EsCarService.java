package com.zchz.business.service.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsCarDTO;
import com.zchz.framework.exception.ApiException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 搜索-车型SKU Service
 *
 * @author Tory.li
 * @create 2016-11-19 13:51
 **/
public interface EsCarService {

    /**
     * 批量更新记录
     *
     * @param list
     * @throws ApiException
     */
    void saveCarList(List<EsCarDTO> list) throws ApiException;

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
    EsCarDTO findOne(Long id) throws ApiException;

    /**
     * 根据ID组查询集合对象
     *
     * @param idList
     * @return
     * @throws ApiException
     */
    List<EsCarDTO> findAll(List<Long> idList) throws ApiException;

    /**
     * 根据品牌名称、品牌厂商名称、品牌车系名称查询对象集合
     *
     * @param brandName
     * @param brandSupplierName
     * @param brandCarSeriesName
     * @return
     * @throws ApiException
     */
    Page<EsCarDTO> search(String brandName, String brandSupplierName, String brandCarSeriesName) throws ApiException;
}
