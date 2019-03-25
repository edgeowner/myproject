package com.zchz.business.service.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsAreaDTO;
import com.zchz.framework.exception.ApiException;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 搜索-区域 Service
 *
 * @author Tory.li
 * @create 2016-11-22 13:38
 **/
public interface EsAreaService {

    /**
     * 批量更新记录
     *
     * @param list
     * @throws ApiException
     */
    void saveAreaList(List<EsAreaDTO> list) throws ApiException;

    /**
     * 删除所有记录
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
    EsAreaDTO findOne(Long id) throws ApiException;

    /**
     * 根据父ID查询集合对象
     *
     * @param parentId
     * @return
     * @throws ApiException
     */
    Page<EsAreaDTO> search(Long parentId) throws ApiException;

}
