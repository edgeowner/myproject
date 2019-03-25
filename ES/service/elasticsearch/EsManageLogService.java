package com.zchz.business.service.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsManageLogDTO;
import com.zchz.business.vo.log.EsManageLogQueryVo;
import com.zchz.business.vo.log.EsManageLogVo;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.pager.Pager;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 搜索-管理日志 Service
 *
 * @author HQR
 * @create 2016-11-19 13:51
 **/
public interface EsManageLogService {

    /**
     * 更新记录
     *
     * @param dto
     * @throws ApiException
     */
    void saveLog(EsManageLogDTO dto) throws ApiException;

    /**
     * 批量更新记录
     *
     * @param list
     * @throws ApiException
     */
    void saveLogList(List<EsManageLogDTO> list) throws ApiException;

    /**
     * 删除所有记录
     *
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
    EsManageLogDTO findOne(String id) throws ApiException;

    /**
     * 根据ID组查询集合对象
     *
     * @param idList
     * @return
     * @throws ApiException
     */
    List<EsManageLogDTO> findAll(List<String> idList) throws ApiException;

    /**
     * 分页查询
     *
     * @param vo
     * @return
     * @throws ApiException
     */
    public Pager<EsManageLogDTO> findForPager(EsManageLogQueryVo vo) throws ApiException;
}
