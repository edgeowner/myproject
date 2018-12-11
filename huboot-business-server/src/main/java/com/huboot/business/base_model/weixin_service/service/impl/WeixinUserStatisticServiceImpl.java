package com.huboot.business.base_model.weixin_service.service.impl;

import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticChartDTO;
import com.huboot.business.common.utils.ExcelWriteUtils;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserAttentionDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticSumsDTO;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinUserStatisticFormsDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.entity.WeixinPublicEntity;
import com.huboot.business.base_model.weixin_service.entity.WeixinUserStatisticsEntiy;
import com.huboot.business.base_model.weixin_service.repository.IWeixinPublicRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinUserPublicMapRepository;
import com.huboot.business.base_model.weixin_service.repository.IWeixinUserStatisticsRepository;
import com.huboot.business.base_model.weixin_service.service.IWeixinUserStatisticService;
import com.huboot.business.base_model.weixin_service.support.WechatMpFactory;
import com.huboot.business.common.component.exception.BizException;
import com.huboot.business.common.utils.DateUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpDataCubeService;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserCumulate;
import me.chanjar.weixin.mp.bean.datacube.WxDataCubeUserSummary;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2018/8/7 0007.
 */
@Service
public class WeixinUserStatisticServiceImpl implements IWeixinUserStatisticService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IWeixinUserStatisticsRepository userStatisticsRepository;

    @Autowired
    private IWeixinUserPublicMapRepository weixinUserPublicMapRepository;

    @Autowired
    private MongoOperations mongoOperations;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IWeixinUserStatisticsRepository weixinUserStatisticsRepository;
    @Autowired
    private IWeixinPublicRepository weixinPublicRepository;
    @Autowired
    private WechatMpFactory wechatMpFactory;


    @Override
    @Transactional
    public void initAllWeixinUserStatistics(String weixinUid, String shopUid,String startTime,String endTime) {

        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        WxMpService wxMpService = wechatMpFactory.getWXMpService(publicEntity);
        WxMpDataCubeService wxMpDataCubeService = wxMpService.getDataCubeService();
        Date endDate = DateUtil.parse(endTime,DateUtil.YYYY_MM_DD);
        Date startDate = DateUtil.parse(startTime,DateUtil.YYYY_MM_DD);
        //2018-04-18
        do{
            try{
                logger.info("-----startDate:"+DateUtil.format(DateUtil.YYYY_MM_DD,startDate)+";endDate:"+DateUtil.format(DateUtil.YYYY_MM_DD,endDate));
                List<String> dateList = new ArrayList<String>();
                Date addDate = startDate;
                while(addDate.compareTo(endDate)<=0){
                    dateList.add(DateUtil.format(DateUtil.YYYY_MM_DD,addDate));
                    addDate = DateUtil.getDateAddDay(addDate,1);
                }
                List<WxDataCubeUserSummary> summaryList = wxMpDataCubeService.getUserSummary(startDate,endDate);
                Map<String, WxDataCubeUserSummary> summaryMap = summaryList.stream().collect(Collectors.toMap(k -> DateUtil.format(DateUtil.YYYY_MM_DD,k.getRefDate())+"&"+k.getUserSource(), wxDataCubeUserSummary -> wxDataCubeUserSummary));
                List<WxDataCubeUserCumulate> cumulateList = wxMpDataCubeService.getUserCumulate(startDate,endDate);
                Map<String, WxDataCubeUserCumulate> cumulateMap = cumulateList.stream().collect(Collectors.toMap(k -> DateUtil.format(DateUtil.YYYY_MM_DD,k.getRefDate()), wxDataCubeUserCumulate -> wxDataCubeUserCumulate));

                for(String date : dateList){
                    Boolean isReturnData = false;
                    for(String dateSourceKey : summaryMap.keySet()) {
                        if(dateSourceKey.contains(date)){
                            WxDataCubeUserSummary summary = summaryMap.get(dateSourceKey);
                            String datekey = dateSourceKey.split("&")[0];
                            WxDataCubeUserCumulate cumulate = cumulateMap.get(datekey);
                            WeixinUserStatisticsEntiy statisticsEntiy = new WeixinUserStatisticsEntiy();
                            statisticsEntiy.setCancelUser(summary.getCancelUser());
                            statisticsEntiy.setCumulateUser(cumulate.getCumulateUser());
                            statisticsEntiy.setId(UUID.randomUUID().toString().replace("-", ""));
                            statisticsEntiy.setNewUser(summary.getNewUser());
                            statisticsEntiy.setRefDate(summary.getRefDate());
                            statisticsEntiy.setShopUid(shopUid);
                            statisticsEntiy.setUserSource(summary.getUserSource());
                            Integer addUser = statisticsEntiy.getNewUser() - statisticsEntiy.getCancelUser();
                            statisticsEntiy.setAddUser(addUser);
                            weixinUserStatisticsRepository.save(statisticsEntiy);
                            isReturnData = true;
                        }

                    }

                    if(!isReturnData){
                        WxDataCubeUserCumulate cumulate = cumulateMap.get(date);
                        WeixinUserStatisticsEntiy statisticsEntiy = new WeixinUserStatisticsEntiy();
                        statisticsEntiy.setCancelUser(0);
                        statisticsEntiy.setCumulateUser(cumulate.getCumulateUser());
                        statisticsEntiy.setId(UUID.randomUUID().toString().replace("-", ""));
                        statisticsEntiy.setNewUser(0);
                        statisticsEntiy.setRefDate(cumulate.getRefDate());
                        statisticsEntiy.setShopUid(shopUid);
                        statisticsEntiy.setUserSource(0);
                        statisticsEntiy.setAddUser(0);
                        weixinUserStatisticsRepository.save(statisticsEntiy);
                    }


                }


            } catch (WxErrorException e) {
                logger.error("公众号用户统计数据初始化异常："+"weixinUid:"+weixinUid+"---shopUid:"+shopUid+"----------------" + e.getError().getJson());
                throw new BizException("公众号用户统计数据初始化异常："+"weixinUid:"+weixinUid+"---shopUid:"+shopUid);
            }

            endDate = DateUtil.getDateAddDay(startDate,-1);
            startDate = DateUtil.getDateAddDay(endDate,-6);

        }while(DateUtil.compareDate(startDate,DateUtil.parse("2018-04-01",DateUtil.YYYY_MM_DD))>=0);

    }


    @Override
    public void initAllWeixinUserStatistics() {
        List<WeixinUserStatisticsEntiy> statisticsEntiyList = weixinUserStatisticsRepository.findAll();
        if(!CollectionUtils.isEmpty(statisticsEntiyList)){
            return;
        }
       /* List<ZkShopRespDTO> zkShopRespDTOList = b2CShopSao.findAllShopForInit();
        for(ZkShopRespDTO zkShopRespDTO : zkShopRespDTOList){
            if(!org.springframework.util.StringUtils.isEmpty(zkShopRespDTO.getWeixinUid())
                    &&!org.springframework.util.StringUtils.isEmpty(zkShopRespDTO.getShopUid())){
                try{
                    String endDate = DateUtil.format(DateUtil.YYYY_MM_DD,DateUtil.getDateAddDay(DateUtil.getCurrentData(),-1));
                    String startDate = DateUtil.format(DateUtil.YYYY_MM_DD,DateUtil.getDateAddDay(DateUtil.getCurrentData(),-7));
                    this.initAllWeixinUserStatistics(zkShopRespDTO.getWeixinUid(), zkShopRespDTO.getShopUid(),startDate,endDate);
                }catch (Exception e ){
                    logger.error("-------------------------公众号用户统计数据初始化异常："+"weixinUid:"+zkShopRespDTO.getWeixinUid()+"---shopUid:"+zkShopRespDTO.getShopUid()+"----------------",e);

                }
            }
        }*/

    }


    @Override
    public void initWeixinUserStatisticsForDay() {
       /* List<ZkShopRespDTO> zkShopRespDTOList = b2CShopSao.findAllShopForInit();
        for(ZkShopRespDTO zkShopRespDTO : zkShopRespDTOList){
            if(!org.springframework.util.StringUtils.isEmpty(zkShopRespDTO.getWeixinUid())
                    &&!org.springframework.util.StringUtils.isEmpty(zkShopRespDTO.getShopUid())){
                try{
                    String beforeTime = DateUtil.format(DateUtil.YYYY_MM_DD,DateUtil.getDateAddDay(DateUtil.getCurrentData(),-1));
                    this.initWeixinUserStatisticsForDay(zkShopRespDTO.getWeixinUid(), zkShopRespDTO.getShopUid(),beforeTime);
                }catch (Exception e ){
                    logger.error("-------------------------每天定时同步公众号用户统计数据初始化异常："+"weixinUid:"+zkShopRespDTO.getWeixinUid()+"---shopUid:"+zkShopRespDTO.getShopUid()+"----------------",e);

                }
            }
        }*/
    }


    @Override
    public void initWeixinUserStatisticsForDay(String weixinUid, String shopUid,String beforeTime) {

        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        WxMpService wxMpService = wechatMpFactory.getWXMpService(publicEntity);
        WxMpDataCubeService wxMpDataCubeService = wxMpService.getDataCubeService();
        Date beforeDate = DateUtil.parse(beforeTime,DateUtil.YYYY_MM_DD);
        try{
            logger.info("------weixinUid:"+weixinUid+";shopUid:"+shopUid+"-----date:"+DateUtil.format(DateUtil.YYYY_MM_DD,beforeDate));
            List<String> dateList = new ArrayList<String>();
            dateList.add(DateUtil.format(DateUtil.YYYY_MM_DD,beforeDate));
            List<WxDataCubeUserSummary> summaryList = wxMpDataCubeService.getUserSummary(beforeDate,beforeDate);
            Map<String, WxDataCubeUserSummary> summaryMap = summaryList.stream().collect(Collectors.toMap(k -> DateUtil.format(DateUtil.YYYY_MM_DD,k.getRefDate())+"&"+k.getUserSource(), wxDataCubeUserSummary -> wxDataCubeUserSummary));
            List<WxDataCubeUserCumulate> cumulateList = wxMpDataCubeService.getUserCumulate(beforeDate,beforeDate);
            Map<String, WxDataCubeUserCumulate> cumulateMap = cumulateList.stream().collect(Collectors.toMap(k -> DateUtil.format(DateUtil.YYYY_MM_DD,k.getRefDate()), wxDataCubeUserCumulate -> wxDataCubeUserCumulate));

            for(String date : dateList){
                Boolean isReturnData = false;
                for(String dateSourceKey : summaryMap.keySet()) {
                    if(dateSourceKey.contains(date)){
                        WxDataCubeUserSummary summary = summaryMap.get(dateSourceKey);
                        String datekey = dateSourceKey.split("&")[0];
                        WxDataCubeUserCumulate cumulate = cumulateMap.get(datekey);
                        WeixinUserStatisticsEntiy statisticsEntiy = new WeixinUserStatisticsEntiy();
                        statisticsEntiy.setCancelUser(summary.getCancelUser());
                        statisticsEntiy.setCumulateUser(cumulate.getCumulateUser());
                        statisticsEntiy.setId(UUID.randomUUID().toString().replace("-", ""));
                        statisticsEntiy.setNewUser(summary.getNewUser());
                        statisticsEntiy.setRefDate(summary.getRefDate());
                        statisticsEntiy.setShopUid(shopUid);
                        statisticsEntiy.setUserSource(summary.getUserSource());
                        Integer addUser = statisticsEntiy.getNewUser() - statisticsEntiy.getCancelUser();
                        statisticsEntiy.setAddUser(addUser);
                        weixinUserStatisticsRepository.save(statisticsEntiy);
                        isReturnData = true;
                    }

                }

                if(!isReturnData){
                    WxDataCubeUserCumulate cumulate = cumulateMap.get(date);
                    if(cumulate != null){
                        WeixinUserStatisticsEntiy statisticsEntiy = new WeixinUserStatisticsEntiy();
                        statisticsEntiy.setCancelUser(0);
                        statisticsEntiy.setCumulateUser(cumulate.getCumulateUser());
                        statisticsEntiy.setId(UUID.randomUUID().toString().replace("-", ""));
                        statisticsEntiy.setNewUser(0);
                        statisticsEntiy.setRefDate(cumulate.getRefDate());
                        statisticsEntiy.setShopUid(shopUid);
                        statisticsEntiy.setUserSource(0);
                        statisticsEntiy.setAddUser(0);
                        weixinUserStatisticsRepository.save(statisticsEntiy);
                    }else{
                        //如果为空则取上一天的总量
                        Date start = DateUtil.getDateAddDay(DateUtil.parse(date),-1);
                        start = DateUtil.getDateAddHour(start, -8);
                        List<WeixinUserStatisticsEntiy> statisticsList = weixinUserStatisticsRepository.findByRefDateAndShopUid(start,shopUid);
                        if(!CollectionUtils.isEmpty(statisticsList)){
                            WeixinUserStatisticsEntiy statisticsEntiy = new WeixinUserStatisticsEntiy();
                            statisticsEntiy.setCancelUser(0);
                            statisticsEntiy.setCumulateUser(statisticsList.get(0).getCumulateUser());
                            statisticsEntiy.setId(UUID.randomUUID().toString().replace("-", ""));
                            statisticsEntiy.setNewUser(0);
                            statisticsEntiy.setRefDate(DateUtil.parse(date));
                            statisticsEntiy.setShopUid(shopUid);
                            statisticsEntiy.setUserSource(0);
                            statisticsEntiy.setAddUser(0);
                            weixinUserStatisticsRepository.save(statisticsEntiy);
                        }

                    }

                }

            }

        } catch (WxErrorException e) {
            logger.error("公众号用户统计数据初始化异常："+"weixinUid:"+weixinUid+"---shopUid:"+shopUid+"----------------" + e.getError().getJson());
            throw new BizException("公众号用户统计数据初始化异常："+"weixinUid:"+weixinUid+"---shopUid:"+shopUid);
        }
    }


    @Override
    public void initWeixinUserStatistics(String weixinUid, String shopUid, String startTime, String endTime) {

        WeixinPublicEntity publicEntity = weixinPublicRepository.findByWeixinUid(weixinUid);
        WxMpService wxMpService = wechatMpFactory.getWXMpService(publicEntity);
        WxMpDataCubeService wxMpDataCubeService = wxMpService.getDataCubeService();
        Date endDate = DateUtil.parse(endTime,DateUtil.YYYY_MM_DD);
        Date startDate = DateUtil.parse(startTime,DateUtil.YYYY_MM_DD);

        try{
            logger.info("-----startDate:"+DateUtil.format(DateUtil.YYYY_MM_DD,startDate)+";endDate:"+DateUtil.format(DateUtil.YYYY_MM_DD,endDate));
            List<String> dateList = new ArrayList<String>();
            Date addDate = startDate;
            while(addDate.compareTo(endDate)<=0){
                dateList.add(DateUtil.format(DateUtil.YYYY_MM_DD,addDate));
                addDate = DateUtil.getDateAddDay(addDate,1);
            }
            List<WxDataCubeUserSummary> summaryList = wxMpDataCubeService.getUserSummary(startDate,endDate);
            Map<String, WxDataCubeUserSummary> summaryMap = summaryList.stream().collect(Collectors.toMap(k -> DateUtil.format(DateUtil.YYYY_MM_DD,k.getRefDate())+"&"+k.getUserSource(), wxDataCubeUserSummary -> wxDataCubeUserSummary));
            List<WxDataCubeUserCumulate> cumulateList = wxMpDataCubeService.getUserCumulate(startDate,endDate);
            Map<String, WxDataCubeUserCumulate> cumulateMap = cumulateList.stream().collect(Collectors.toMap(k -> DateUtil.format(DateUtil.YYYY_MM_DD,k.getRefDate()), wxDataCubeUserCumulate -> wxDataCubeUserCumulate));

            for(String date : dateList){
                Boolean isReturnData = false;
                for(String dateSourceKey : summaryMap.keySet()) {
                    if(dateSourceKey.contains(date)){
                        WxDataCubeUserSummary summary = summaryMap.get(dateSourceKey);
                        String datekey = dateSourceKey.split("&")[0];
                        WxDataCubeUserCumulate cumulate = cumulateMap.get(datekey);
                        WeixinUserStatisticsEntiy statisticsEntiy = new WeixinUserStatisticsEntiy();
                        statisticsEntiy.setCancelUser(summary.getCancelUser());
                        statisticsEntiy.setCumulateUser(cumulate.getCumulateUser());
                        statisticsEntiy.setId(UUID.randomUUID().toString().replace("-", ""));
                        statisticsEntiy.setNewUser(summary.getNewUser());
                        statisticsEntiy.setRefDate(summary.getRefDate());
                        statisticsEntiy.setShopUid(shopUid);
                        statisticsEntiy.setUserSource(summary.getUserSource());
                        Integer addUser = statisticsEntiy.getNewUser() - statisticsEntiy.getCancelUser();
                        statisticsEntiy.setAddUser(addUser);
                        weixinUserStatisticsRepository.save(statisticsEntiy);
                        isReturnData = true;
                    }

                }

                if(!isReturnData){
                    WxDataCubeUserCumulate cumulate = cumulateMap.get(date);
                    WeixinUserStatisticsEntiy statisticsEntiy = new WeixinUserStatisticsEntiy();
                    statisticsEntiy.setCancelUser(0);
                    statisticsEntiy.setCumulateUser(cumulate.getCumulateUser());
                    statisticsEntiy.setId(UUID.randomUUID().toString().replace("-", ""));
                    statisticsEntiy.setNewUser(0);
                    statisticsEntiy.setRefDate(cumulate.getRefDate());
                    statisticsEntiy.setShopUid(shopUid);
                    statisticsEntiy.setUserSource(0);
                    statisticsEntiy.setAddUser(0);
                    weixinUserStatisticsRepository.save(statisticsEntiy);
                }


            }


        } catch (WxErrorException e) {
            logger.error("公众号用户统计数据初始化异常："+"weixinUid:"+weixinUid+"---shopUid:"+shopUid+"----------------" + e.getError().getJson());
            throw new BizException("公众号用户统计数据初始化异常："+"weixinUid:"+weixinUid+"---shopUid:"+shopUid);
        }

    }

    private Calendar getCalendar(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    private String formatPercentage(int yesterday, int before) {
        if (before == 0) {
            return null;
        }
        int diliverNum = yesterday;//举例子的变量
        int queryMailNum = yesterday - before;//举例子的变量
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(1);
        /**
         *
         	日统计：【（1天前数值-2天数值）/2天数值】*100%（数值按照四舍五入原则，取到小数点后一位）；
         	周统计：【（1天前数值-8天前数值）/8天前数值】*100%（取值规则同上）；
         	月统计：【（1天前数值-31天前数值/31天前数值）】*100%（取值规则同上）；
         */
        String result = numberFormat.format((float) queryMailNum / (float) before * 100);
        result = new BigDecimal(result).abs().toString();
        return result + "%";
    }

    private void countNew(List<WeixinUserStatisticsEntiy> yesterdayEntity, List<WeixinUserStatisticsEntiy> before, Map<String, WeixinUserAttentionDTO> result, int day) {
        WeixinUserAttentionDTO.Item item = null;
        WeixinUserAttentionDTO weixinUserAttentionDTO = result.get("new");
        int target = 0;
        int source = 0;

        if (!yesterdayEntity.isEmpty()) {
            source = yesterdayEntity.stream().map(WeixinUserStatisticsEntiy::getNewUser).reduce(Integer::sum).get();
        }
        if (!before.isEmpty()) {
            target = before.stream().map(WeixinUserStatisticsEntiy::getNewUser).reduce(Integer::sum).get();
        }
        if (target == 0) {
            switch (day) {
                case 2:
                    weixinUserAttentionDTO.setYesterday(null);
                    break;
                case 8:
                    weixinUserAttentionDTO.setWeek(null);
                    break;
                case 31:
                    weixinUserAttentionDTO.setMonth(null);
                    break;
            }
        } else if (source >= target) {
            String v = formatPercentage(source, target);
            item = weixinUserAttentionDTO.new Item();
            item.setValue(v);
            item.setAsc(true);
        } else {
            String v = formatPercentage(source, target);
            item = weixinUserAttentionDTO.new Item();
            item.setValue(v);
            item.setAsc(false);
        }
        switch (day) {
            case 2:
                weixinUserAttentionDTO.setYesterday(item);
                break;
            case 8:
                weixinUserAttentionDTO.setWeek(item);
                break;
            case 31:
                weixinUserAttentionDTO.setMonth(item);
                break;
        }
    }

    private void countCancel(List<WeixinUserStatisticsEntiy> yesterdayEntity, List<WeixinUserStatisticsEntiy> before, Map<String, WeixinUserAttentionDTO> result, int day) {
        WeixinUserAttentionDTO weixinUserAttentionDTO = result.get("cancel");
        int source = 0;
        int target = 0;
        WeixinUserAttentionDTO.Item item = null;
        if (!yesterdayEntity.isEmpty()) {
            source = yesterdayEntity.stream().map(WeixinUserStatisticsEntiy::getCancelUser).reduce(Integer::sum).get();
        }
        if (!before.isEmpty()) {
            target = before.stream().map(WeixinUserStatisticsEntiy::getCancelUser).reduce(Integer::sum).get();
        }
        if (target == 0) {
            switch (day) {
                case 2:
                    weixinUserAttentionDTO.setYesterday(null);
                    break;
                case 8:
                    weixinUserAttentionDTO.setWeek(null);
                    break;
                case 31:
                    weixinUserAttentionDTO.setMonth(null);
                    break;
            }
        } else if (source >= target) {
            String v = formatPercentage(source, target);
            item = weixinUserAttentionDTO.new Item();
            item.setValue(v);
            item.setAsc(true);
        } else {
            String v = formatPercentage(source, target);
            item = weixinUserAttentionDTO.new Item();
            item.setValue(v);
            item.setAsc(false);
        }
        switch (day) {
            case 2:
                weixinUserAttentionDTO.setYesterday(item);
                break;
            case 8:
                weixinUserAttentionDTO.setWeek(item);
                break;
            case 31:
                weixinUserAttentionDTO.setMonth(item);
                break;
        }
    }

    private void countAdd(List<WeixinUserStatisticsEntiy> yesterdayEntity, List<WeixinUserStatisticsEntiy> before, Map<String, WeixinUserAttentionDTO> result, int day) {
        WeixinUserAttentionDTO weixinUserAttentionDTO = result.get("add");
        WeixinUserAttentionDTO.Item item = null;
        int source = 0;
        int target = 0;
        if (!yesterdayEntity.isEmpty()) {
            source = yesterdayEntity.stream().map(WeixinUserStatisticsEntiy::getAddUser).reduce(Integer::sum).get();
        }
        if (!before.isEmpty()) {
            target = before.stream().map(WeixinUserStatisticsEntiy::getAddUser).reduce(Integer::sum).get();
        }
        if (target == 0) {
            switch (day) {
                case 2:
                    weixinUserAttentionDTO.setYesterday(null);
                    break;
                case 8:
                    weixinUserAttentionDTO.setWeek(null);
                    break;
                case 31:
                    weixinUserAttentionDTO.setMonth(null);
                    break;
            }
        } else if (source >= target) {
            String v = formatPercentage(source, target);
            item = weixinUserAttentionDTO.new Item();
            item.setValue(v);
            item.setAsc(true);
        } else {
            String v = formatPercentage(source, target);
            item = weixinUserAttentionDTO.new Item();
            item.setValue(v);
            item.setAsc(false);
        }
        switch (day) {
            case 2:
                weixinUserAttentionDTO.setYesterday(item);
                break;
            case 8:
                weixinUserAttentionDTO.setWeek(item);
                break;
            case 31:
                weixinUserAttentionDTO.setMonth(item);
                break;
        }
    }

    private void countCumulate(List<WeixinUserStatisticsEntiy> yesterdayEntity, List<WeixinUserStatisticsEntiy> before, Map<String, WeixinUserAttentionDTO> result, int day) {
        WeixinUserAttentionDTO weixinUserAttentionDTO = result.get("cumulate");
        WeixinUserAttentionDTO.Item item = null;
        int source = 0;
        int target = 0;
        if (!yesterdayEntity.isEmpty()) {
            source = yesterdayEntity.stream().map(WeixinUserStatisticsEntiy::getCumulateUser).reduce(Integer::sum).get();
        }
        if (!before.isEmpty()) {
            target = before.stream().map(WeixinUserStatisticsEntiy::getCumulateUser).reduce(Integer::sum).get();
        }
        if (target == 0) {
            switch (day) {
                case 2:
                    weixinUserAttentionDTO.setYesterday(null);
                    break;
                case 8:
                    weixinUserAttentionDTO.setWeek(null);
                    break;
                case 31:
                    weixinUserAttentionDTO.setMonth(null);
                    break;
            }
        } else if (source >= target) {
            String v = formatPercentage(source, target);
            item = weixinUserAttentionDTO.new Item();
            item.setValue(v);
            item.setAsc(true);
        } else {
            String v = formatPercentage(source, target);
            item = weixinUserAttentionDTO.new Item();
            item.setValue(v);
            item.setAsc(false);
        }
        switch (day) {
            case 2:
                weixinUserAttentionDTO.setYesterday(item);
                break;
            case 8:
                weixinUserAttentionDTO.setWeek(item);
                break;
            case 31:
                weixinUserAttentionDTO.setMonth(item);
                break;
        }
    }

    @Override
    public Map<String, WeixinUserAttentionDTO> yesterdayAttention(String shopUid) {

        Map<String, WeixinUserAttentionDTO> result = new HashMap<>();
        result.put("new", new WeixinUserAttentionDTO());
        result.put("cancel", new WeixinUserAttentionDTO());
        result.put("add", new WeixinUserAttentionDTO());
        result.put("cumulate", new WeixinUserAttentionDTO());

        Calendar yesterdayCal = getCalendar(-1);
        ;
        List<WeixinUserStatisticsEntiy> yesterdayEntity = userStatisticsRepository.findByRefDateAndShopUid(yesterdayCal.getTime(), shopUid);
        if (!yesterdayEntity.isEmpty()) {

            int newCount = yesterdayEntity.stream().map(WeixinUserStatisticsEntiy::getNewUser).reduce(Integer::sum).get();
            result.get("new").setCount(newCount);

            int cancelCount = yesterdayEntity.stream().map(WeixinUserStatisticsEntiy::getCancelUser).reduce(Integer::sum).get();
            result.get("cancel").setCount(cancelCount);

            int addCount = yesterdayEntity.stream().map(WeixinUserStatisticsEntiy::getAddUser).reduce(Integer::sum).get();
            result.get("add").setCount(addCount);

            int cumulateCount = yesterdayEntity.get(0).getCumulateUser();
                    //yesterdayEntity.stream().map(WeixinUserStatisticsEntiy::getCumulateUser).reduce(Integer::sum).get();
            result.get("cumulate").setCount(cumulateCount);

        }

        //一天前
        Calendar beforeYesterdayCal = getCalendar(-2);
        ;
        List<WeixinUserStatisticsEntiy> beforeYesterdayEntity = userStatisticsRepository.findByRefDateAndShopUid(beforeYesterdayCal.getTime(), shopUid);
        countNew(yesterdayEntity, beforeYesterdayEntity, result, 2);
        countCancel(yesterdayEntity, beforeYesterdayEntity, result, 2);
        countAdd(yesterdayEntity, beforeYesterdayEntity, result, 2);
        countCumulate(yesterdayEntity, beforeYesterdayEntity, result, 2);

        //七天前
        Calendar sevenCal = getCalendar(-8);
        ;
        List<WeixinUserStatisticsEntiy> sevenEntity = userStatisticsRepository.findByRefDateAndShopUid(sevenCal.getTime(), shopUid);
        countNew(yesterdayEntity, sevenEntity, result, 8);
        countCancel(yesterdayEntity, sevenEntity, result, 8);
        countAdd(yesterdayEntity, sevenEntity, result, 8);
        countCumulate(yesterdayEntity, sevenEntity, result, 8);

        //三十一天前
        Calendar thirtyCal = getCalendar(-31);
        Date monthDate = thirtyCal.getTime();
        List<WeixinUserStatisticsEntiy> monthEntity = userStatisticsRepository.findByRefDateAndShopUid(monthDate, shopUid);
        countNew(yesterdayEntity, monthEntity, result, 31);
        countCancel(yesterdayEntity, monthEntity, result, 31);
        countAdd(yesterdayEntity, monthEntity, result, 31);
        countCumulate(yesterdayEntity, monthEntity, result, 31);
        return result;

    }

    /**
     * 趋势图
     *
     * @param startDate
     * @param endDate
     * @param shopUid
     * @param source
     * @return
     */
    public List<WeixinUserStatisticDTO> map(String startDate, String endDate, String shopUid, Integer source) {

        List<WeixinUserStatisticDTO> result = new ArrayList<>();
        List<WeixinUserStatisticsEntiy> entities = findList(startDate, endDate, shopUid, source, null, null, null, null);
        entities.stream().forEach(weixinUserStatisticsEntiy -> {
            WeixinUserStatisticDTO dto = new WeixinUserStatisticDTO();
            dto.setAddUser(weixinUserStatisticsEntiy.getAddUser());
            dto.setCancelUser(weixinUserStatisticsEntiy.getCancelUser());
            dto.setCumulateUser(weixinUserStatisticsEntiy.getCumulateUser());
            dto.setNewUser(weixinUserStatisticsEntiy.getNewUser());
            dto.setDate(DateUtil.format(DateUtil.YYYY_MM_DD, weixinUserStatisticsEntiy.getRefDate()));
            result.add(dto);
        });
        Date s = DateUtil.parse(startDate);
        Date e = DateUtil.parse(endDate);
        int a = DateUtil.daysBetween(s, e);
        Calendar c = Calendar.getInstance();
        c.setTime(s);
        for (int i = 0; i < a; i++) {
            String d = DateUtil.format(DateUtil.YYYY_MM_DD, c.getTime());
            Optional<WeixinUserStatisticDTO> optional = result.stream().filter(entity -> {
                return entity.getDate().equals(d);
            }).findFirst();
            if (!optional.isPresent()) {
                WeixinUserStatisticDTO dto = new WeixinUserStatisticDTO();
                dto.setAddUser(0);
                dto.setCancelUser(0);
                dto.setCumulateUser(0);
                dto.setNewUser(0);
                dto.setDate(d);
                result.add(dto);
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        result.sort((o1, o2) -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date d1 = simpleDateFormat.parse(o1.getDate());
                Date d2 = simpleDateFormat.parse(o2.getDate());
                int aa = d1.compareTo(d2);
                return aa;
            } catch (ParseException e1) {

                logger.warn("时间格式化错误，{}", o1, o2);
                e1.printStackTrace();
            }
            return 0;
        });
        return result;

    }

    @Override
    public Pager<WeixinUserStatisticDTO> list(
            String startDate,
            String endDate,
            String orderField,
            Integer orderRule,
            String shopUid,
            Integer pageIndex,
            Integer pageSize
    ) {

        Pager<WeixinUserStatisticDTO> pager = new Pager<>();
        List<WeixinUserStatisticsEntiy> entities = findList(startDate, endDate, shopUid, null, orderField, orderRule, pageIndex, pageSize);
        int count = findCount(startDate, endDate, shopUid, null);

        entities.stream().forEach(weixinUserStatisticsEntiy -> {
            WeixinUserStatisticDTO dto = new WeixinUserStatisticDTO();
            dto.setAddUser(weixinUserStatisticsEntiy.getAddUser());
            dto.setCancelUser(weixinUserStatisticsEntiy.getCancelUser());
            dto.setCumulateUser(weixinUserStatisticsEntiy.getCumulateUser());
            dto.setNewUser(weixinUserStatisticsEntiy.getNewUser());
            dto.setDate(DateUtil.format(DateUtil.YYYY_MM_DD, weixinUserStatisticsEntiy.getRefDate()));
            pager.getPageItems().add(dto);
        });
        pager.setRowsCount(count);
        pager.setPageRowsCount(pager.getPageItems().size());
        pager.setCurrPage(pageIndex);
        pager.setPageSize(pageSize);
        if (count <= pager.getPageSize()) {
            pager.setPageCount(1);
        } else {
            double size = count / pager.getPageSize();
            if (count % pager.getPageSize() == 0) {
                pager.setPageCount((int) size);
            } else {
                pager.setPageCount((int) size + 1);
            }
        }
        return pager;


    }

    @Override
    public List<WeixinUserStatisticDTO> export(String startDate, String endDate, String orderField, Integer orderRule, String shopUid) {

        List<WeixinUserStatisticsEntiy> entities = findList(startDate, endDate, shopUid, null, orderField, orderRule, null, Integer.MAX_VALUE);
        List<WeixinUserStatisticDTO> result = new ArrayList<>();
        entities.stream().forEach(weixinUserStatisticsEntiy -> {
            WeixinUserStatisticDTO dto = new WeixinUserStatisticDTO();
            dto.setAddUser(weixinUserStatisticsEntiy.getAddUser());
            dto.setCancelUser(weixinUserStatisticsEntiy.getCancelUser());
            dto.setCumulateUser(weixinUserStatisticsEntiy.getCumulateUser());
            dto.setNewUser(weixinUserStatisticsEntiy.getNewUser());
            dto.setDate(DateUtil.format(DateUtil.YYYY_MM_DD, weixinUserStatisticsEntiy.getRefDate()));
            result.add(dto);
        });
        return result;

    }

    @Override
    public WeixinUserStatisticChartDTO chart(String startDate, String endDate, String shopUid, Integer fromDateType) throws Exception {
        WeixinUserStatisticChartDTO dto = new WeixinUserStatisticChartDTO();
        List<WeixinUserStatisticsEntiy> entities = new ArrayList<>();
        if (shopUid != null && !"".equals(shopUid)) {
            entities = findList(startDate, endDate, shopUid, null, null, null, null, null);
        } else {
            entities = findList(startDate, endDate, null, null, null, null, null, null);
        }

        List<Integer> cancelUser = new ArrayList<>();
        List<Integer> newUser = new ArrayList<>();
        List<String> date = new ArrayList<>();

        List<String> dateList = new ArrayList<>();
        List<WeixinUserStatisticsEntiy> list = new ArrayList<>();
        if (WeixinUserStatisticsEntiy.FindDateTypeEnum.None.getValue().equals(fromDateType) || WeixinUserStatisticsEntiy.FindDateTypeEnum.Week.getValue().equals(fromDateType) || WeixinUserStatisticsEntiy.FindDateTypeEnum.OneMonth.getValue().equals(fromDateType)) {
            dateList = DateUtil.getDayBetween(startDate, endDate);
            for (String s : dateList) {
                list = entities.stream().filter(t -> s.equals(DateUtil.format("yyyy-MM-dd", t.getRefDate()))).collect(Collectors.toList());
                if (list.size() > 0) {
                    int newSum = list.stream().map(WeixinUserStatisticsEntiy::getNewUser).reduce(Integer::sum).get();
                    int cancelSum = list.stream().map(WeixinUserStatisticsEntiy::getCancelUser).reduce(Integer::sum).get();
                    cancelUser.add(cancelSum);
                    newUser.add(newSum);
                    date.add(s);
                } else {
                    cancelUser.add(0);
                    newUser.add(0);
                    date.add(s);
                }
            }
        } else if (fromDateType.equals(WeixinUserStatisticsEntiy.FindDateTypeEnum.Trimester.getValue()) || fromDateType.equals(WeixinUserStatisticsEntiy.FindDateTypeEnum.HalfYear.getValue()) || fromDateType.equals(WeixinUserStatisticsEntiy.FindDateTypeEnum.Year.getValue())) {
            dateList = DateUtil.getMonthBetween(startDate, endDate);
            for (String s : dateList) {
                list = entities.stream().filter(t -> DateUtil.format("yyyy-MM", t.getRefDate()).equals(s)).collect(Collectors.toList());
                if (list.size() > 0) {
                    int newSum = list.stream().map(WeixinUserStatisticsEntiy::getNewUser).reduce(Integer::sum).get();
                    int cancelSum = list.stream().map(WeixinUserStatisticsEntiy::getCancelUser).reduce(Integer::sum).get();
                    cancelUser.add(cancelSum);
                    newUser.add(newSum);
                    date.add(s);
                } else {
                    cancelUser.add(0);
                    newUser.add(0);
                    date.add(s);
                }
            }
        }
        dto.setCancelUser(cancelUser);
        dto.setNewUser(newUser);
        dto.setDate(date);
        return dto;
    }

    @Override
    public WeixinUserStatisticSumsDTO sums(String startDate, String endDate, String shopUid) {
//        Date star = DateUtil.parse(startDate, DateUtil.YYYY_MM_DD);
//        Date end = DateUtil.parse(endDate, DateUtil.YYYY_MM_DD);
        WeixinUserStatisticSumsDTO dto = new WeixinUserStatisticSumsDTO();
//        List<WeixinUserStatisticsEntiy> entitys = userStatisticsRepository.findByRefDateIsBetweenAndShopUid(star, end, shopUid);
        List<WeixinUserStatisticsEntiy> entitys = findList(startDate, endDate, shopUid, null, null, null, null, null);

        entitys = entitys.stream().sorted(Comparator.comparing(WeixinUserStatisticsEntiy::getRefDate).reversed()).collect(Collectors.toList());
        if (entitys.size() > 0) {
            Integer newUser = entitys.stream().map(WeixinUserStatisticsEntiy::getNewUser).reduce(Integer::sum).get();
            Integer cancelUser = entitys.stream().map(WeixinUserStatisticsEntiy::getCancelUser).reduce(Integer::sum).get();
            dto.setAddUser(newUser - cancelUser);

            /**
             * 2018-08-24调整统计方法注销此逻辑
             */
            /*Map<String, Integer> newUsreMap = new HashMap<>();
            Map<String, Integer> cancelUserMap = new HashMap<>();
            if (StringUtils.isEmpty(shopUid)) {
                for (WeixinUserStatisticsEntiy entity : entitys) {
                    if (!StringUtils.isEmpty(entity.getShopUid()) && !newUsreMap.containsKey(entity.getShopUid())) {
//                        map.put(entity.getShopUid(), entity.getCumulateUser().toString());
                        newUsreMap.put(entity.getShopUid(), entity.getNewUser());
                        cancelUserMap.put(entity.getShopUid(), entity.getCancelUser());
                    }
                }
                Integer newUser = newUsreMap.entrySet().stream().map(s -> s.getValue()).reduce(0, (x ,y) -> x + y);
                Integer cancelUser = cancelUserMap.entrySet().stream().map(s -> s.getValue()).reduce(0, (x ,y) -> x + y);
                dto.setAddUser(newUser - cancelUser);

//                dto.setAddUser(map.entrySet().stream().map(s -> Integer.valueOf(s.getValue())).reduce(0 ,(x ,y) -> x + y));
            } else {
//            list = entitys.stream().filter(t -> endDate.equals(DateUtil.format("yyyy-MM-dd", t.getRefDate()))).collect(Collectors.toList());
//            dto.setCumulateUser(entitys.stream().map(WeixinUserStatisticsEntiy::getCumulateUser).reduce(Integer::sum).get());
                dto.setAddUser(entitys.get(0).getCumulateUser() == null ? 0 : entitys.get(0).getCumulateUser());
            }*/
        }
        return dto;
    }

    private int findCount(String startDate, String endDate, String shopUid, Integer source) {

        Date start = DateUtil.parse(startDate);
        start = DateUtil.getDateAddHour(start, -8);
        Date end = DateUtil.parse(endDate);
        end = DateUtil.getDateAddHour(end, -8);
        Criteria criteria = Criteria.where("refDate").gte(start).lte(end);
        if (!StringUtils.isEmpty(shopUid)) {
            criteria.and("shopUid").is(shopUid);
        }
        if (source != null && source != -1) {
            criteria.and("userSource").is(source);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(criteria),
                Aggregation.group("refDate", "shopUid")
        );
//查询条件
        AggregationResults<Map> results = mongoOperations.aggregate(aggregation, WeixinUserStatisticsEntiy.class, Map.class);
        List<Map> maps = results.getMappedResults();
        return (int) maps.size();

    }

    private List<Map> getList(String startDate, String endDate, String shopUid, Integer source, String orderField, Integer orderRule, Integer pageIndex, Integer pageSize) {

        Date start = DateUtil.parse(startDate);
        start = DateUtil.getDateAddHour(start, -8);
        Date end = DateUtil.parse(endDate);
        end = DateUtil.getDateAddHour(end, -8);
        Criteria criteria = Criteria.where("refDate").gte(start).lte(end);
        if (!StringUtils.isEmpty(shopUid)) {
            criteria.and("shopUid").is(shopUid);
        }
        if (source != null && source != -1) {
            criteria.and("userSource").is(source);
        }

        List<AggregationOperation> operations = new ArrayList<>();

        operations.add(Aggregation.match(criteria));
        operations.add(
                Aggregation.group("refDate", "shopUid").sum("newUser").as("newUser").sum("cancelUser").as("cancelUser").sum("addUser").as("addUser").max("cumulateUser").as("cumulateUser")
        );
        operations.add(
                Aggregation.project().
                        and("newUser").as("newUser").
                        and("shopUid").as("shopUid").
                        and("cancelUser").as("cancelUser").
                        and("addUser").as("addUser").
                        and("cumulateUser").as("cumulateUser").
                        and("refDate").as("_id")
        );

        if (StringUtils.isNotEmpty(orderField)) {
            if (orderRule != null && orderRule == 1) {
                SortOperation sort = Aggregation.sort(Sort.by(orderField).descending());
                operations.add(sort);
            } else {
                SortOperation sort = Aggregation.sort(Sort.by(orderField).ascending());
                operations.add(sort);
            }
        } else {
            SortOperation sort = Aggregation.sort(Sort.by("_id").ascending());
            operations.add(sort);
        }

        if (pageIndex != null && pageIndex >= 0) {
            SkipOperation skipOp = Aggregation.skip((long) (pageIndex - 1) * pageSize);
            operations.add(skipOp);
        }
        if (pageSize != null && pageSize > 0) {
            LimitOperation limitOp = Aggregation.limit(pageSize);
            operations.add(limitOp);
        }

        Aggregation aggregation = Aggregation.newAggregation(
                WeixinUserStatisticsEntiy.class,
                operations
        );

        AggregationResults<Map> results = mongoOperations.aggregate(aggregation, WeixinUserStatisticsEntiy.class, Map.class);
        List<Map> maps = results.getMappedResults();
        return maps;

    }

    private List<WeixinUserStatisticsEntiy> findList(String startDate, String endDate, String shopUid, Integer source, String orderField, Integer orderRule, Integer pageIndex, Integer pageSize) {

        List<Map> maps = getList(startDate, endDate, shopUid, source, orderField, orderRule, pageIndex, pageSize);
        List<WeixinUserStatisticsEntiy> result = new ArrayList<>();
        Iterator<Map> iterator = maps.iterator();
        while (iterator.hasNext()) {

            Map<String, Object> entiyMap = iterator.next();
            WeixinUserStatisticsEntiy w = new WeixinUserStatisticsEntiy();
            w.setNewUser((int) entiyMap.get("newUser"));
            w.setCancelUser((int) entiyMap.get("cancelUser"));
            w.setAddUser((int) entiyMap.get("addUser"));
            w.setCumulateUser(Integer.parseInt(entiyMap.get("cumulateUser").toString()));
            w.setRefDate((Date) entiyMap.get("_id"));
            w.setShopUid(entiyMap.get("shopUid").toString());
            result.add(w);
        }
        return result;


    }


    @Override
    public List<WeixinUserStatisticFormsDTO> formsList(String startDate, String endDate, String orderField, Integer orderRule, String shopUid) {
        List<Map> maps = getList(startDate, endDate, shopUid, null, orderField, orderRule, null, null);
        List<WeixinUserStatisticFormsDTO> result = new ArrayList<>();
        Iterator<Map> iterator = maps.iterator();
        while (iterator.hasNext()){

            Map<String,Object> entiyMap = iterator.next();
            WeixinUserStatisticFormsDTO w = new WeixinUserStatisticFormsDTO();
            w.setNewUser((int)entiyMap.get("newUser"));
            w.setCancelUser((int)entiyMap.get("cancelUser"));
            w.setAddUser((int)entiyMap.get("addUser"));
            w.setCumulateUser(Integer.parseInt(entiyMap.get("cumulateUser").toString()));
            w.setDate(DateUtil.format(DateUtil.YYYY_MM_DD, (Date)entiyMap.get("_id")));
            //设置商户名称
           /* ZkShopRespDTO shopRespDTO = b2CShopSao.findShopByShopUid((String)entiyMap.get("shopUid"));
            if(shopRespDTO != null){
                w.setShopName(shopRespDTO.getName());
            }*/

            result.add(w);
        }
        return result;
    }

    @Override
    public void formsExport(String startDate, String endDate, String orderField, Integer orderRule, HttpServletResponse response) {
        try {
            WeixinUserStatisticsEntiy.IndexType indexType = null;
            if(StringUtils.isNotEmpty(orderField)){
                try {
                    indexType = WeixinUserStatisticsEntiy.IndexType.valueOf(orderField);
                } catch (IllegalArgumentException e) {
                    orderField = null;
                }
                if(orderField != null){
                    orderField = indexType.name();
                }
            }
            List<WeixinUserStatisticFormsDTO> list = this.formsList(startDate,endDate,orderField,orderRule,null);

            ExcelWriteUtils.ByteFile file = ExcelWriteUtils.writeSkuExcel("111", new String[]{
                    "时间", "商户名称","新关注人数", "取消关注人数", "实际关注人数", "总关注人数"
            }, new String[]{
                    "date", "shopName","newUser", "cancelUser", "addUser", "cumulateUser"}, WeixinUserStatisticFormsDTO.class, list.toArray());
            InputStream is = new ByteArrayInputStream(file.getContent());
            // 设置response参数，可以打开下载页面
            response.reset();
            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String(("微信用户统计" + ".xlsx").getBytes("utf-8"), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                bis = new BufferedInputStream(is);
                bos = new BufferedOutputStream(out);
                byte[] buff = new byte[2048];
                int bytesRead;
                while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                    bos.write(buff, 0, bytesRead);
                }
            } catch (final IOException e) {
                throw new BizException(e.getMessage());
            } finally {
                if (bis != null)
                    bis.close();
                if (bos != null)
                    bos.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /***
     * 公众号渠道二维码关注量统计
     *  @param weixinUid
     * @param promotionSource**/
    @Override
    public Integer promotionSourceCount(String weixinUid, String promotionSource) {
       return weixinUserPublicMapRepository.promotionSourceCount(weixinUid,promotionSource);
    }


}


