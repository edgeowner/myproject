package com.zchz.business.dao.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsManageLogDTO;
import com.zchz.business.dto.elasticsearch.EsSkuDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 管理日志
 *
 * @author HQR
 * @create 2016-11-19 13:48
 **/
public interface ManageLogRepository extends ElasticsearchRepository<EsManageLogDTO, String> {
}
