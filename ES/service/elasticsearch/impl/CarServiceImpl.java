package com.zchz.business.service.elasticsearch.impl;

import com.zchz.business.dao.elasticsearch.CarRepository;
import com.zchz.business.dto.elasticsearch.EsCarDTO;
import com.zchz.business.service.elasticsearch.EsCarService;
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
 * 搜索-车型SKU ServiceImpl
 *
 * @author Tory.li
 * @create 2016-11-22 13:41
 **/
@Service("esCarServiceImpl")
public class CarServiceImpl implements EsCarService {

    @Autowired
    private CarRepository carRepository;

    @Override
    public void saveCarList(List<EsCarDTO> list) throws ApiException {
        carRepository.save(list);
    }

    @Override
    public void deleteAll() throws ApiException {
        carRepository.deleteAll();
    }

    @Override
    public EsCarDTO findOne(Long id) throws ApiException {
        return carRepository.findOne(id);
    }

    @Override
    public List<EsCarDTO> findAll(List<Long> idList) throws ApiException {
        return (List<EsCarDTO>) carRepository.findAll(idList);
    }

    @Override
    public Page<EsCarDTO> search(String brandName, String brandSupplierName, String brandCarSeriesName) throws ApiException {
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(brandName)) {
            bool.must(QueryBuilders.termQuery("pinpai", brandName));
        }
        if (!StringUtils.isEmpty(brandSupplierName)) {
            bool.must(QueryBuilders.termQuery("changshang", brandSupplierName));
        }
        if (!StringUtils.isEmpty(brandCarSeriesName)) {
            bool.must(QueryBuilders.termQuery("chexi", brandCarSeriesName));
        }
        return (Page<EsCarDTO>) carRepository.search(bool, new PageRequest(0, 1000));
    }
}
