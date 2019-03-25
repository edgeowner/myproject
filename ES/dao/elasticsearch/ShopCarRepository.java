package com.zchz.business.dao.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsCarDTO;
import com.zchz.business.dto.elasticsearch.EsShopCarDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 店铺车辆
 *
 * @author Tory.li
 * @create 2016-11-19 13:48
 **/
public interface ShopCarRepository extends ElasticsearchRepository<EsShopCarDTO, Long> {
}
