package com.zchz.business.manager.elasticsearch.impl;


import com.zchz.business.dto.elasticsearch.EsAdminLoginLogDTO;
import com.zchz.business.manager.elasticsearch.EsAdminLoginLogManager;
import com.zchz.business.service.elasticsearch.EsAdminLoginLogService;
import com.zchz.business.vo.log.EsAdminLoginLogQueryVo;
import com.zchz.business.vo.log.EsAdminLoginLogVo;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.pager.Pager;
import com.zchz.framework.utils.PagerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 搜索-管理日志 AdminLoginrImpl
 *
 * @author HQR
 * @create 2016-11-22 13:36
 **/
@Service("esAdminLoginLogManagerImpl")
public class AdminLoginLogManagerImpl implements EsAdminLoginLogManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EsAdminLoginLogService esAdminLoginLogService;

    @Override
    public void rebuildIndex() throws ApiException {

        /*EsAdminLoginLogDTO dto = new EsAdminLoginLogDTO();
        for(int i = 0;i<10;i++){
            dto.setId(String.valueOf(PrimaryKeyGenerator.SEQUENCE.next()));
            dto.setHandleContent("23");
            dto.setHandleModule("123");
            dto.setHandlerName("fff"+i);
            dto.setHandlerPhone("qeqe");
            dto.setHandleTime(new Date().getTime());
            esAdminLoginLogService.saveLog(dto);
        }*/

        //esAdminLoginLogService.deleteAll();

    }


    @Override
    public Pager<EsAdminLoginLogVo> findForPager(EsAdminLoginLogQueryVo vo) throws ApiException {
        Pager<EsAdminLoginLogDTO> esAdminLoginLogDTOPager = esAdminLoginLogService.findForPager(vo);
        Pager<EsAdminLoginLogVo> voPager = PagerUtils.convert(esAdminLoginLogDTOPager, new PagerUtils.IConvert<EsAdminLoginLogDTO, EsAdminLoginLogVo>() {
            @Override
            public EsAdminLoginLogVo doConvert(EsAdminLoginLogDTO esAdminLoginLogDTO) {
                EsAdminLoginLogVo esAdminLoginLogVo = new EsAdminLoginLogVo();
                BeanUtils.copyProperties(esAdminLoginLogDTO, esAdminLoginLogVo);
                return esAdminLoginLogVo;
            }
        });

        return voPager;
    }
}
