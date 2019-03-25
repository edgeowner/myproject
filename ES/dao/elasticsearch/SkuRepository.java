package com.zchz.business.dao.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsCarDTO;
import com.zchz.business.dto.elasticsearch.EsSkuDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 车型SKU
 *
 * @author Tory.li
 * @create 2016-11-19 13:48
 **/
public interface SkuRepository extends ElasticsearchRepository<EsSkuDTO, String> {
}
