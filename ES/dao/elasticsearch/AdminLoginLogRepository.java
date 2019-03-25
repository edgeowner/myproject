package com.zchz.business.dao.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsAdminLoginLogDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 后台登录日志
 *
 * @author HQR
 * @create 2016-11-19 13:48
 **/
public interface AdminLoginLogRepository extends ElasticsearchRepository<EsAdminLoginLogDTO, String> {
}
