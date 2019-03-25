package com.zchz.business.service.elasticsearch.impl;

import com.zchz.business.dao.elasticsearch.AreaRepository;
import com.zchz.business.dto.elasticsearch.EsAreaDTO;
import com.zchz.business.service.elasticsearch.EsAreaService;
import com.zchz.framework.exception.ApiException;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 搜索-区域 ServiceImpl
 *
 * @author Tory.li
 * @create 2016-11-22 13:41
 **/
@Service("esAreaServiceImpl")
public class AreaServiceImpl implements EsAreaService {

    @Autowired
    private AreaRepository areaRepository;

    @Override
    public void saveAreaList(List<EsAreaDTO> list) throws ApiException {
        areaRepository.save(list);
    }

    @Override
    public void deleteAll() throws ApiException {
        areaRepository.deleteAll();
    }

    @Override
    public EsAreaDTO findOne(Long id) throws ApiException {
        return areaRepository.findOne(id);
    }

    @Override
    public Page<EsAreaDTO> search(Long parentId) throws ApiException {
        return (Page<EsAreaDTO>) areaRepository.search(QueryBuilders.termQuery("parentId", parentId), new PageRequest(0, 10000));
    }
}
