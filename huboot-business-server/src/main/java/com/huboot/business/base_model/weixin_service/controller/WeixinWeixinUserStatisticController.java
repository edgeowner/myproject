package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticChartDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticDTO;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserStatisticSumsDTO;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinUserStatisticFormsDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.service.IWeixinUserStatisticService;
import com.huboot.business.base_model.weixin_service.dto.business_manage_web.WeixinUserAttentionDTO;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/8/7 0007.
 */
@RestController
class WeixinWeixinUserStatisticController implements IWeixinUserStatisticsController {

    @Autowired
    private IWeixinUserStatisticService weixinUserStatisticService;

    @Override
    public Map<String,WeixinUserAttentionDTO> attention(@PathVariable String shopUid){
        Map<String,WeixinUserAttentionDTO> map = weixinUserStatisticService.yesterdayAttention(shopUid);
        return map;
    }

    @Override
    public List<WeixinUserStatisticDTO> map(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam String shopUid,
            @RequestParam Integer source
    ) {
        List<WeixinUserStatisticDTO> result = weixinUserStatisticService.map(startDate,endDate,shopUid,source);
        return result;
    }

    @Override
    public Pager<WeixinUserStatisticDTO> list(
            @RequestParam("startDate")String startDate,
            @RequestParam("endDate")String endDate,
            @RequestParam("orderField") String orderField,
            @RequestParam("orderRule") Integer orderRule,
            @RequestParam("shopUid")String shopUid,
            @RequestParam("pageIndex")Integer pageIndex,
            @RequestParam("pageSize")Integer pageSize
    ) {

        Pager<WeixinUserStatisticDTO> result = weixinUserStatisticService.list(startDate,endDate,orderField,orderRule,shopUid,pageIndex,pageSize);
        return result;

    }

    @Override
    public List<WeixinUserStatisticDTO> export(
            @RequestParam("startDate")String startDate,
            @RequestParam("endDate")String endDate,
            @RequestParam("orderField") String orderField,
            @RequestParam("orderRule") Integer orderRule,
            @RequestParam(value = "shopUid",required = false)String shopUid
    ) {
        List<WeixinUserStatisticDTO> result = weixinUserStatisticService.export(startDate,endDate,orderField,orderRule,shopUid);
        return result;
    }

    @Override
    public WeixinUserStatisticChartDTO chart(@RequestParam(value = "startDate") String startDate,
                                             @RequestParam(value = "endDate") String endDate,
                                             @RequestParam(value = "shopUid", required = false) String shopUid,
                                             @RequestParam(value = "fromDateType") Integer fromDateType) throws Exception {
        return weixinUserStatisticService.chart(startDate, endDate, shopUid, fromDateType);
    }

    @Override
    public WeixinUserStatisticSumsDTO sums(@RequestParam(value = "startDate") String startDate,
                                           @RequestParam(value = "endDate") String endDate,
                                           @RequestParam(value = "shopUid", required = false) String shopUid) throws Exception {
        return weixinUserStatisticService.sums(startDate, endDate, shopUid);
    }

    /***
     * 公众号渠道二维码关注量统计
     *
     * @param weixinUid
     * @param promotionSource**/
    @Override
    public Integer promotionSourceCount(@RequestParam(value = "wexin_uid") String weixinUid,@RequestParam(value = "promotion_souce") String promotionSource) {
        return weixinUserStatisticService.promotionSourceCount(weixinUid,promotionSource);
    }


    @GetMapping(value = "/base_model/weixin_service/userStatistic/formsExport")
    @ApiOperation(response = WeixinUserStatisticFormsDTO.class, value = "报表导出")
    public void export(
            @RequestParam String startDate,
            @RequestParam String endDate,
            @RequestParam(required = false) String orderField,
            @RequestParam(required = false) Integer orderRule
            , HttpServletResponse response
    ) throws Exception {
        weixinUserStatisticService.formsExport(startDate,endDate,orderField,orderRule,response);
    }

}
