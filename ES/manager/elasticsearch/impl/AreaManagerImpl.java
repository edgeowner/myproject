package com.zchz.business.manager.elasticsearch.impl;

import com.zchz.business.domain.area.AreaDomain;
import com.zchz.business.dto.elasticsearch.EsAreaDTO;
import com.zchz.business.manager.elasticsearch.EsAreaManager;
import com.zchz.business.service.area.IAreaService;
import com.zchz.business.service.elasticsearch.EsAreaService;
import com.zchz.business.vo.area.AreaVO;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.enums.system.ErrorCodeEnum;
import com.zchz.framework.model.pager.Pager;
import com.zchz.framework.utils.ListCopyUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 搜索-区域 ManagerImpl
 *
 * @author Tory.li
 * @create 2016-11-22 13:36
 **/
@Service("esAreaManagerImpl")
public class AreaManagerImpl implements EsAreaManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IAreaService areaService;
    @Autowired
    private EsAreaService esAreaService;

    @Override
    public void rebuildIndex() throws ApiException {
        //删除车型SKU索引库
        esAreaService.deleteAll();

        //循环新增document,一次1000条记录
        int currentPage = 1;
        do {
            Pager<AreaDomain> pager = areaService.findForPager(new AreaDomain(), currentPage, 1000);
            if (pager == null || CollectionUtils.isEmpty(pager.getPageItems())) {
                break;
            }
            List<EsAreaDTO> list = ListCopyUtil.copyAsList(pager.getPageItems().iterator(), EsAreaDTO.class);
            esAreaService.saveAreaList(list);
            logger.info("Indexing zchz/area complete with " + pager.getCurrPage() + "/" + pager.getPageCount() + " Total rows is " + pager.getPageRowsCount());
            currentPage++;
        } while (true);
    }

    @Override
    public AreaVO findAreaById(Long id) throws ApiException {
        if (id == null) {
            throw new ApiException(ErrorCodeEnum.NOTNULL);
        }
        EsAreaDTO esAreaDTO = esAreaService.findOne(id);
        if (esAreaDTO == null) {
            throw new ApiException(ErrorCodeEnum.NotFound);
        }
        AreaVO areaVO = new AreaVO();
        BeanUtils.copyProperties(esAreaDTO, areaVO);
        return areaVO;
    }

    @Override
    public List<AreaVO> findAreaByParentId(Long parentId) {
        if (parentId == null) {
            throw new ApiException(ErrorCodeEnum.NOTNULL);
        }
        Page<EsAreaDTO> esAreaDTOPage = esAreaService.search(parentId);
        List<AreaVO> areaVOList = ListCopyUtil.copyAsList(esAreaDTOPage.iterator(), AreaVO.class);
        return areaVOList;
    }
}
