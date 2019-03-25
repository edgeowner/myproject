package com.zchz.business.manager.elasticsearch;

import com.zchz.business.dto.elasticsearch.EsManageLogDTO;
import com.zchz.business.vo.log.EsManageLogQueryVo;
import com.zchz.business.vo.log.EsManageLogVo;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.pager.Pager;

/**
 * 搜索-管理日志 Manager
 *
 * @author HQR
 * @create 2016-11-19 14:08
 **/
public interface EsManageLogManager {

    /**
     * 重建管理日志索引库
     *
     * @throws ApiException
     */
    void rebuildIndex() throws ApiException;

    /**
     * 分页查询
     *
     * @param vo
     * @return
     * @throws ApiException
     */
    public Pager<EsManageLogVo> findForPager(EsManageLogQueryVo vo) throws ApiException;

}
