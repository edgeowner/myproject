package com.zchz.business.service.elasticsearch.impl;

import com.zchz.business.dao.elasticsearch.SkuRepository;
import com.zchz.business.dto.elasticsearch.EsSkuDTO;
import com.zchz.business.service.elasticsearch.EsSkuService;
import com.zchz.framework.exception.ApiException;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 搜索-基础车型 ServiceImpl
 *
 * @author Tory.li
 * @create 2016-11-22 13:41
 **/
@Service("esSkuServiceImpl")
public class SkuServiceImpl implements EsSkuService {

    @Autowired
    private SkuRepository skuRepository;

    @Override
    public void saveSku(EsSkuDTO dto) throws ApiException {
        skuRepository.save(dto);
    }

    @Override
    public void saveSkuList(List<EsSkuDTO> list) throws ApiException {
        skuRepository.save(list);
    }

    @Override
    public void deleteAll() throws ApiException {
        skuRepository.deleteAll();
    }

    @Override
    public EsSkuDTO findOne(String id) throws ApiException {
        return skuRepository.findOne(id);
    }

    @Override
    public List<EsSkuDTO> findAll(List<String> idList) throws ApiException {
        return (List<EsSkuDTO>) skuRepository.findAll(idList);
    }

    @Override
    public Page<EsSkuDTO> search(String brandName, String brandSupplierName, String brandCarSeriesName) throws ApiException {
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(brandName)) {
            bool.must(QueryBuilders.termQuery("spuBrandName", brandName));
        }
        if (!StringUtils.isEmpty(brandSupplierName)) {
            bool.must(QueryBuilders.termQuery("spuSupplierName", brandSupplierName));
        }
        if (!StringUtils.isEmpty(brandCarSeriesName)) {
            bool.must(QueryBuilders.termQuery("spuName", brandCarSeriesName));
        }
        return (Page<EsSkuDTO>) skuRepository.search(bool, new PageRequest(0, 1000));
    }


    @Override
    public void removeSku(String id) throws ApiException {
        skuRepository.delete(id);
    }
}
