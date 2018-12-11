package com.huboot.business.base_model.weixin_service.repository;

import com.huboot.business.base_model.weixin_service.entity.WeixinUserStatisticsEntiy;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface IWeixinUserStatisticsRepository extends MongoRepository<WeixinUserStatisticsEntiy, String> {

    WeixinUserStatisticsEntiy findOneByShopUid(String shopUid);

    WeixinUserStatisticsEntiy findByRefDateBetweenAndShopUid(Date after, Date before, String shopUid);

    List<WeixinUserStatisticsEntiy> findByRefDateIsBetweenAndShopUid(Date after, Date before, String shopUid);

    List<WeixinUserStatisticsEntiy> findByRefDateAndShopUid(Date refDate,String shopUid);

}