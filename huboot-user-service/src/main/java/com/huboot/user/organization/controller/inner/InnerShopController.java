package com.huboot.user.organization.controller.inner;

import com.huboot.share.user_service.api.dto.ShopDetaiInfo;
import com.huboot.share.user_service.api.feign.ShopFeignClient;
import com.huboot.user.organization.service.IOrganizationShopService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "内部端-用户服务-公司表 API")
@RestController
public class InnerShopController implements ShopFeignClient {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IOrganizationShopService shopService;

	@Override
	public ShopDetaiInfo get(@PathVariable("id") Long id) {
		return shopService.findDetail(id);
	}

	@Override
	public ShopDetaiInfo findByShopSn(@PathVariable("shopSn") String shopSn) {
		return shopService.findByShopSn(shopSn);
	}
}
