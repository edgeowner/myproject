package com.zchz.business.service.elasticsearch.impl;

import com.netflix.discovery.converters.Auto;
import com.zchz.business.dao.elasticsearch.ManageLogRepository;
import com.zchz.business.domain.log.EsManageLogDomain;
import com.zchz.business.domain.shop.ShopCarDomain;
import com.zchz.business.dto.elasticsearch.EsCarDTO;
import com.zchz.business.dto.elasticsearch.EsManageLogDTO;
import com.zchz.business.service.elasticsearch.EsManageLogService;
import com.zchz.business.vo.log.EsManageLogQueryVo;
import com.zchz.business.vo.log.EsManageLogVo;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.pager.Pager;
import com.zchz.framework.utils.DateUtil;
import com.zchz.framework.utils.ListCopyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 搜索-管理日志 ServiceImpl
 *
 * @author HQR
 * @create 2016-11-22 13:41
 **/
@Service("esManageLogServiceImpl")
public class ManageLogServiceImpl implements EsManageLogService {

    @Autowired
    private ManageLogRepository manageLogRepository;

    @Override
    public void saveLog(EsManageLogDTO dto) throws ApiException {
        manageLogRepository.save(dto);
    }

    @Override
    public void saveLogList(List<EsManageLogDTO> list) throws ApiException {
        manageLogRepository.save(list);
    }

    @Override
    public void deleteAll() throws ApiException {
        manageLogRepository.deleteAll();
    }

    @Override
    public EsManageLogDTO findOne(String id) throws ApiException {
        return manageLogRepository.findOne(id);
    }

    @Override
    public List<EsManageLogDTO> findAll(List<String> idList) throws ApiException {
        return (List<EsManageLogDTO>) manageLogRepository.findAll(idList);
    }

    @Override
    public Pager<EsManageLogDTO>  findForPager(EsManageLogQueryVo vo) throws ApiException {
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(vo.getId())) {
            bool.must(QueryBuilders.termQuery("id", vo.getId()));
        }
        if (!StringUtils.isEmpty(vo.getHandleModule())) {
            bool.must(QueryBuilders.termQuery("handleModule", vo.getHandleModule()));
        }
        if (!StringUtils.isEmpty(vo.getHandlerPhone())) {
            bool.must(QueryBuilders.termQuery("handlerPhone", vo.getHandlerPhone()));
        }
        if (!StringUtils.isEmpty(vo.getHandlerName())) {
            bool.must(QueryBuilders.termQuery("handlerName", vo.getHandlerName()));
        }
        if(!StringUtils.isEmpty(vo.getStartTime())&&!StringUtils.isEmpty(vo.getEndTime())){
            try{

                Date startime = DateUtil.parse(vo.getStartTime(),"yyyy-MM-dd HH:mm:ss");
                Date endtime  = DateUtil.parse(vo.getEndTime(),"yyyy-MM-dd HH:mm:ss");
                bool.must(QueryBuilders.rangeQuery("handleTime").gte(startime.getTime()).lte(endtime.getTime()));
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        Integer curPage = vo.getPage();
        Integer pageSize = vo.getPer_page();

        Page<EsManageLogDTO> esManageLogDTOPage = (Page<EsManageLogDTO>) manageLogRepository.search(bool,new PageRequest(curPage-1,pageSize, new Sort(Sort.Direction.DESC, "handleTime")));
        Pager<EsManageLogDTO> eslogPager = CreatPager(curPage, pageSize, esManageLogDTOPage);
        /*List<EsManageLogDTO> loglist = (List<EsManageLogDTO>)eslogPager.getPageItems();
        List<EsManageLogDomain> logDomains = new ArrayList<EsManageLogDomain>();
        if (!CollectionUtils.isEmpty(loglist)) {
            logDomains = ListCopyUtil.copyAsList(loglist.iterator(), EsManageLogDomain.class);
        }
        //按照时间降序排序
        if(logDomains.size() > 1){
            Collections.sort(logDomains, new Comparator<EsManageLogDomain>() {
                @Override
                public int compare(EsManageLogDomain o1, EsManageLogDomain o2) {
                    return DateUtil.compareDate(new Date(o2.getHandleTime()), new Date(o1.getHandleTime()));
                }
            });
            if (!CollectionUtils.isEmpty(logDomains)) {
                loglist = ListCopyUtil.copyAsList(logDomains.iterator(), EsManageLogDTO.class);
            }
            eslogPager.setPageItems(loglist);
        }*/


        return eslogPager;

    }



    private Pager<EsManageLogDTO> CreatPager(Integer curPage, Integer pageSize, Page<EsManageLogDTO> esManageLogDTOPage) {
        Integer pageCount = null;
        if (curPage == 0) {
            curPage = 1;
        }

        if (pageSize == 0) {
            pageSize = 3;
        }

        int rowsCount = new Long(esManageLogDTOPage.getTotalElements()).intValue();
        try {
            pageCount = ((rowsCount / pageSize) > (rowsCount / pageSize) ? (rowsCount / pageSize) + 1 : rowsCount / pageSize);
            if (rowsCount % pageSize > 0) {
                pageCount = pageCount + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //变相解决查询所有
        if (pageSize != null && pageSize.intValue() == -1) {
            pageCount = 0;
            curPage = 1;
            pageSize = rowsCount;
        }
        List<EsManageLogDTO> list = null;
        if (((curPage - 1) * pageSize) < rowsCount) {
            list=  esManageLogDTOPage.getContent();
        }

        //构建一个空list
        if (list == null) {
            list = new ArrayList<EsManageLogDTO>();
        }
        Pager<EsManageLogDTO> pager = new Pager<EsManageLogDTO>();
        pager.setPageItems(list);
        pager.setRowsCount(rowsCount);
        pager.setCurrPage(curPage);
        pager.setPageCount(pageCount.intValue());
        pager.setPageSize(pageSize);

        if (list == null || list.isEmpty()) {
            pager.setPageRowsCount(0);
        } else {
            pager.setPageRowsCount(list.size());
        }
        return pager;
    }

}
