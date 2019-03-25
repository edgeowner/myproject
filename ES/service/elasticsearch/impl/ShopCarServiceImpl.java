package com.zchz.business.service.elasticsearch.impl;

import com.zchz.business.dao.elasticsearch.ShopCarRepository;
import com.zchz.business.dto.elasticsearch.EsShopCarDTO;
import com.zchz.business.service.elasticsearch.EsShopCarService;
import com.zchz.framework.exception.ApiException;
import com.zchz.framework.model.enums.dict.CustomerShopCarAllotStatusEnum;
import com.zchz.framework.model.enums.dict.CustomerShopCarMarketStatusEnum;
import com.zchz.framework.model.enums.dict.CustomerShopCarRentStatusEnum;
import com.zchz.framework.utils.SecurityContextHelper;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.common.ParseField;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsBuilder;
import org.elasticsearch.search.aggregations.metrics.max.MaxBuilder;
import org.elasticsearch.search.aggregations.metrics.min.MinBuilder;
import org.elasticsearch.search.aggregations.pipeline.bucketmetrics.min.MinBucketBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 搜索-车型SKU ServiceImpl
 *
 * @author Tory.li
 * @create 2016-11-22 13:41
 **/
@Service("esShopCarServiceImpl")
public class ShopCarServiceImpl implements EsShopCarService {

    @Autowired
    private ShopCarRepository shopCarRepository;
    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public void saveShopCarList(List<EsShopCarDTO> list) throws ApiException {
        shopCarRepository.save(list);
    }

    @Override
    public void saveShopCar(EsShopCarDTO dto) throws ApiException {
        shopCarRepository.save(dto);
    }

    @Override
    public void deleteAll() throws ApiException {
        shopCarRepository.deleteAll();
    }

    @Override
    public SearchResponse search(Long carSeriesId, Long startTime, Long endTime) throws ApiException {
        SearchRequestBuilder requestBuilder = template.getClient().prepareSearch("zchz-shop-car").setTypes("shopCar");
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        bool.must(QueryBuilders.termQuery("carSeriesId", carSeriesId));
        bool.must(QueryBuilders.termQuery("marketStatus", CustomerShopCarMarketStatusEnum.Up.getValue()));
        bool.must(QueryBuilders.termQuery("cityAreaId", SecurityContextHelper.getCityAreaIdIfExits()));
        bool.mustNot(QueryBuilders.termQuery("shopId", SecurityContextHelper.getShopIdIfExits()));
        bool.must(QueryBuilders.rangeQuery("startTime").lte(startTime));

        //排除已分配
        bool.mustNot(QueryBuilders.termQuery("allotStatus", CustomerShopCarAllotStatusEnum.Alloted.getValue()));

        //可租车辆
        BoolQueryBuilder canRent = QueryBuilders.boolQuery();
        canRent.must(QueryBuilders.termQuery("rentStatus", CustomerShopCarRentStatusEnum.CanRent.getValue()));
        canRent.must(QueryBuilders.rangeQuery("startTime").lte(startTime));
        bool.should(canRent);

        //在租车辆
        BoolQueryBuilder renting = QueryBuilders.boolQuery();
        renting.must(QueryBuilders.termQuery("rentStatus", CustomerShopCarRentStatusEnum.Renting.getValue()));
        //
        BoolQueryBuilder renttime = QueryBuilders.boolQuery();
        renttime.must(QueryBuilders.rangeQuery("startTime").lte(startTime));
        renttime.must(QueryBuilders.rangeQuery("endTime").gte(endTime));

        renting.mustNot(renttime);

        bool.should(renting);
        bool.minimumNumberShouldMatch(1);

        TermsBuilder shop = AggregationBuilders.terms("shop").field("shopId").size(100);
        MinBuilder minPrice = AggregationBuilders.min("min_price").field("price");
        MaxBuilder maxPrice = AggregationBuilders.max("max_price").field("price");
        requestBuilder.setQuery(bool);
        requestBuilder.addAggregation(shop.subAggregation(minPrice).subAggregation(maxPrice));
        requestBuilder.setSearchType(SearchType.COUNT);
        SearchResponse response = requestBuilder.execute().actionGet();
        return response;
    }

}
