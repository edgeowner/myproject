package com.zchz.business.manager.elasticsearch.impl;


import com.zchz.business.dto.elasticsearch.EsManageLogDTO;
import com.zchz.business.dto.sku.SkuDTO;
import com.zchz.business.manager.elasticsearch.EsManageLogManager;
import com.zchz.business.manager.elasticsearch.EsSkuManager;
import com.zchz.business.service.elasticsearch.EsManageLogService;
import com.zchz.business.vo.log.EsManageLogQueryVo;
import com.zchz.business.vo.log.EsManageLogVo;
import com.zchz.business.vo.sku.SkuVO;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.keyGenerator.PrimaryKeyGenerator;
import com.zchz.framework.model.pager.Pager;
import com.zchz.framework.utils.PagerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 搜索-管理日志 ManagerImpl
 *
 * @author HQR
 * @create 2016-11-22 13:36
 **/
@Service("esManageLogManagerImpl")
public class ManageLogManagerImpl implements EsManageLogManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EsManageLogService esManageLogService;

    @Override
    public void rebuildIndex() throws ApiException {

        /*EsManageLogDTO dto = new EsManageLogDTO();
        for(int i = 0;i<10;i++){
            dto.setId(String.valueOf(PrimaryKeyGenerator.SEQUENCE.next()));
            dto.setHandleContent("23");
            dto.setHandleModule("123");
            dto.setHandlerName("fff"+i);
            dto.setHandlerPhone("qeqe");
            dto.setHandleTime(new Date().getTime());
            esManageLogService.saveLog(dto);
        }*/

        //esManageLogService.deleteAll();

    }


    @Override
    public Pager<EsManageLogVo> findForPager(EsManageLogQueryVo vo) throws ApiException {
        Pager<EsManageLogDTO> esManageLogDTOPager = esManageLogService.findForPager(vo);
        Pager<EsManageLogVo> voPager = PagerUtils.convert(esManageLogDTOPager, new PagerUtils.IConvert<EsManageLogDTO, EsManageLogVo>() {
            @Override
            public EsManageLogVo doConvert(EsManageLogDTO esManageLogDTO) {
                EsManageLogVo esManageLogVo = new EsManageLogVo();
                BeanUtils.copyProperties(esManageLogDTO, esManageLogVo);
                esManageLogVo.setHandleTime(esManageLogDTO.getHandleTime());
                return esManageLogVo;
            }
        });

        return voPager;
    }
}
