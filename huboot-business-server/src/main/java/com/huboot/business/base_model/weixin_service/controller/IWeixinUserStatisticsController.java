package com.huboot.business.base_model.weixin_service.controller;


import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticChartDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserAttentionDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticSumsDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/7 0007.
 */
public interface IWeixinUserStatisticsController {


    public static final String ATTENTION = "/base_model/weixin_service/userStatistic/attention/{shopUid}";

    public static final String MAP = "/base_model/weixin_service/userStatistic/map";

    public static final String LIST = "/base_model/weixin_service/userStatistic/list";

    public static final String EXPORT = "/base_model/weixin_service/userStatistic/export";

    public static final String CHART = "/base_model/weixin_service/userStatistic/chart";

    public static final String SUMS = "/base_model/weixin_service/userStatistic/sums";

    String URL_PROMOTION_SOURCE_COUNT = "/base_model/weixin_service/promotion_source/count";//公众号渠道二维码关注量统计

    @GetMapping(ATTENTION)
    public Map<String,WeixinUserAttentionDTO> attention(
            @PathVariable("shopUid") String shopUid
    );

    @GetMapping(MAP)
    public List<WeixinUserStatisticDTO> map(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("shopUid") String shopUid,
            @RequestParam("source") Integer source
    );

    @GetMapping(LIST)
    Pager<WeixinUserStatisticDTO> list(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("orderField") String orderField,
            @RequestParam("orderRule") Integer orderRule,
            @RequestParam("shopUid") String shopUid,
            @RequestParam("pageIndex") Integer pageIndex,
            @RequestParam("pageSize") Integer pageSize);


    @GetMapping(EXPORT)
    List<WeixinUserStatisticDTO> export(
            @RequestParam("startDate") String startDate,
            @RequestParam("endDate") String endDate,
            @RequestParam("orderField") String orderField,
            @RequestParam("orderRule") Integer orderRule,
            @RequestParam(value = "shopUid", required = false) String shopUid
    );

    @GetMapping(CHART)
    WeixinUserStatisticChartDTO chart(@RequestParam(value = "startDate") String startDate,
                                      @RequestParam(value = "endDate") String endDate,
                                      @RequestParam(value = "shopUid", required = false) String shopUid,
                                      @RequestParam(value = "fromDateType") Integer fromDateType) throws Exception;

    @GetMapping(SUMS)
    WeixinUserStatisticSumsDTO sums(@RequestParam(value = "startDate") String startDate,
                                    @RequestParam(value = "endDate") String endDate,
                                    @RequestParam(value = "shopUid", required = false) String shopUid) throws Exception;


    /***
     * 公众号渠道二维码关注量统计
     * **/
    @GetMapping(URL_PROMOTION_SOURCE_COUNT)
    Integer promotionSourceCount(@RequestParam(value = "wexin_uid") String weixinUid, @RequestParam(value = "promotion_souce") String promotionSource);
}
