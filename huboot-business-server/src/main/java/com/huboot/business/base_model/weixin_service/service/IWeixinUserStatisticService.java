package com.huboot.business.base_model.weixin_service.service;

import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticChartDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserAttentionDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticSumsDTO;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinUserStatisticFormsDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/7 0007.
 */
public interface IWeixinUserStatisticService {

    void initAllWeixinUserStatistics(String weixinUid, String shopUid,String startDate,String endDate);

    void initAllWeixinUserStatistics();

    void initWeixinUserStatisticsForDay();

    void initWeixinUserStatisticsForDay(String weixinUid, String shopUid,String beforeTime);

    void initWeixinUserStatistics(String weixinUid, String shopUid,String startDate,String endDate);

    public Map<String,WeixinUserAttentionDTO> yesterdayAttention(String shopUid);

    List<WeixinUserStatisticDTO> map(String startDate, String endDate, String shopUid, Integer source);

    Pager<WeixinUserStatisticDTO> list(String startDate, String endDate, String orderField, Integer orderRule, String shopUid, Integer pageIndex, Integer pageSize);

    List<WeixinUserStatisticDTO> export(String startDate, String endDate, String orderField, Integer orderRule, String shopUid);

    // 内管
    WeixinUserStatisticChartDTO chart(String startDate, String endDate, String shopUid, Integer fromDateType) throws Exception;

    WeixinUserStatisticSumsDTO sums(String startDate, String endDate, String shopUid);

    List<WeixinUserStatisticFormsDTO> formsList(String startDate, String endDate, String orderField, Integer orderRule, String shopUid);

    void formsExport(String startDate, String endDate, String orderField, Integer orderRule, HttpServletResponse response);


    /***
     * 公众号渠道二维码关注量统计
     *
     * @param weixinUid
     * @param promotionSource**/
    Integer promotionSourceCount(String weixinUid, String promotionSource);

}
