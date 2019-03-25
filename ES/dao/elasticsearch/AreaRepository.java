package com.zchz.business.dao.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsAreaDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 区域
 *
 * @author Tory.li
 * @create 2016-11-22 13:30
 **/
public interface AreaRepository extends ElasticsearchRepository<EsAreaDTO, Long> {
}
