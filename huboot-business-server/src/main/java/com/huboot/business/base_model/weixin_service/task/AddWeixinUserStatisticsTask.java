package com.huboot.business.base_model.weixin_service.task;

import com.huboot.business.base_model.weixin_service.service.IWeixinUserStatisticService;
import com.huboot.business.common.annotation.LocalDisable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/1/27 0027.
 */
@Component
@LocalDisable
public class AddWeixinUserStatisticsTask {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IWeixinUserStatisticService weixinUserStatisticService;

    /**
     * 确认订单后24小时未支付，则自动取消订单
     *
     */
    @Scheduled(cron = "0 10 8 * * ?")//为确保公众号数据已完成统计和处理，请于每天上午8点后查询公众号前一天的数据。
    public void cancelOrderTask(){

        logger.info("---------------------------------每天所有直客店铺初始化微信用户分析数据-------------------------------");
        weixinUserStatisticService.initWeixinUserStatisticsForDay();

    }
    
}
