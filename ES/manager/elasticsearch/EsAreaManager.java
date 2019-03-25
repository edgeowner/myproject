package com.zchz.business.manager.elasticsearch;

import com.zchz.business.vo.area.AreaVO;
import com.zchz.framework.exception.ApiException;

import java.util.List;

/**
 * 搜索-区域 Manager
 *
 * @author Tory.li
 * @create 2016-11-22 13:35
 **/
public interface EsAreaManager {

    /**
     * 重建车型SKU索引库
     *
     * @throws ApiException
     */
    void rebuildIndex() throws ApiException;

    /**
     * 根据ID查询区域详细信息
     *
     * @param id
     * @throws ApiException
     */
    AreaVO findAreaById(Long id) throws ApiException;

    /**
     * 根据父ID查询区域集合
     *
     * @param parentId
     * @return
     * @throws ApiException
     */
    List<AreaVO> findAreaByParentId(Long parentId) throws ApiException;
}
