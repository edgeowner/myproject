package com.zchz.business.service.elasticsearch.impl;

import com.zchz.business.dao.elasticsearch.AdminLoginLogRepository;
import com.zchz.business.domain.log.EsAdminLoginLogDomain;
import com.zchz.business.dto.elasticsearch.EsAdminLoginLogDTO;
import com.zchz.business.service.elasticsearch.EsAdminLoginLogService;
import com.zchz.business.service.elasticsearch.EsManageLogService;
import com.zchz.business.vo.log.EsAdminLoginLogQueryVo;
import com.zchz.business.vo.log.EsManageLogQueryVo;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.pager.Pager;
import com.zchz.framework.utils.DateUtil;
import com.zchz.framework.utils.ListCopyUtil;
import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 搜索-管理日志 ServiceImpl
 *
 * @author HQR
 * @create 2016-11-22 13:41
 **/
@Service("esAdminLoginLogServiceImpl")
public class AdminLoginLogServiceImpl implements EsAdminLoginLogService {

    @Autowired
    private AdminLoginLogRepository adminLoginLogRepository;

    @Override
    public void saveLog(EsAdminLoginLogDTO dto) throws ApiException {
        adminLoginLogRepository.save(dto);
    }

    @Override
    public void saveLogList(List<EsAdminLoginLogDTO> list) throws ApiException {
        adminLoginLogRepository.save(list);
    }

    @Override
    public void deleteAll() throws ApiException {
        adminLoginLogRepository.deleteAll();
    }

    @Override
    public EsAdminLoginLogDTO findOne(String id) throws ApiException {
        return adminLoginLogRepository.findOne(id);
    }

    @Override
    public List<EsAdminLoginLogDTO> findAll(List<String> idList) throws ApiException {
        return (List<EsAdminLoginLogDTO>) adminLoginLogRepository.findAll(idList);
    }

    @Override
    public Pager<EsAdminLoginLogDTO>  findForPager(EsAdminLoginLogQueryVo vo) throws ApiException {
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(vo.getUserId())) {
            bool.must(QueryBuilders.termQuery("userId", vo.getUserId()));
        }
        if (!StringUtils.isEmpty(vo.getType())) {
            bool.must(QueryBuilders.termQuery("type", vo.getType()));
        }
        if (!StringUtils.isEmpty(vo.getLoginIp())) {
            bool.must(QueryBuilders.termQuery("loginIp", vo.getLoginIp()));
        }
        if (!StringUtils.isEmpty(vo.getName())) {
            bool.must(QueryBuilders.termQuery("name", vo.getName()));
        }
        if (!StringUtils.isEmpty(vo.getPhone())) {
            bool.must(QueryBuilders.termQuery("phone", vo.getPhone()));
        }
        if (!StringUtils.isEmpty(vo.getUserName())) {
            bool.must(QueryBuilders.termQuery("userName", vo.getUserName()));
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

        Page<EsAdminLoginLogDTO> EsAdminLoginLogDTOPage = (Page<EsAdminLoginLogDTO>) adminLoginLogRepository.search(bool,new PageRequest(curPage-1,pageSize,new Sort(Sort.Direction.DESC, "handleTime")));
        Pager<EsAdminLoginLogDTO> eslogPager = CreatPager(curPage, pageSize, EsAdminLoginLogDTOPage);
        /*List<EsAdminLoginLogDTO> loglist = (List<EsAdminLoginLogDTO>)eslogPager.getPageItems();
        List<EsAdminLoginLogDomain> logDomains = new ArrayList<EsAdminLoginLogDomain>();
        if (!CollectionUtils.isEmpty(loglist)) {
            logDomains = ListCopyUtil.copyAsList(loglist.iterator(), EsAdminLoginLogDomain.class);
        }
        //按照时间降序排序
        if(logDomains.size() > 1){
            Collections.sort(logDomains, new Comparator<EsAdminLoginLogDomain>() {
                @Override
                public int compare(EsAdminLoginLogDomain o1, EsAdminLoginLogDomain o2) {
                    return DateUtil.compareDate(new Date(o2.getHandleTime()), new Date(o1.getHandleTime()));
                }
            });
            if (!CollectionUtils.isEmpty(logDomains)) {
                loglist = ListCopyUtil.copyAsList(logDomains.iterator(), EsAdminLoginLogDTO.class);
            }
            eslogPager.setPageItems(loglist);
        }*/


        return eslogPager;

    }



    private Pager<EsAdminLoginLogDTO> CreatPager(Integer curPage, Integer pageSize, Page<EsAdminLoginLogDTO> EsAdminLoginLogDTOPage) {
        Integer pageCount = null;
        if (curPage == 0) {
            curPage = 1;
        }

        if (pageSize == 0) {
            pageSize = 3;
        }

        int rowsCount = new Long(EsAdminLoginLogDTOPage.getTotalElements()).intValue();
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
        List<EsAdminLoginLogDTO> list = null;
        if (((curPage - 1) * pageSize) < rowsCount) {
            list=  EsAdminLoginLogDTOPage.getContent();
        }

        //构建一个空list
        if (list == null) {
            list = new ArrayList<EsAdminLoginLogDTO>();
        }
        Pager<EsAdminLoginLogDTO> pager = new Pager<EsAdminLoginLogDTO>();
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
