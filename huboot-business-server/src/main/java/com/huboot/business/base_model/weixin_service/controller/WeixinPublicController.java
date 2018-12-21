package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.WexinPublicPagerDTO;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.*;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicService;
import com.huboot.business.base_model.weixin_service.service.IWeixinUserService;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicCreateDTO;
import com.huboot.business.base_model.weixin_service.dto.WexinAuthShopDTO;
import com.huboot.business.base_model.weixin_service.dto.dto.InitPublicFromMPDTO;
import com.huboot.business.base_model.weixin_service.dto.dto.WeixinAuthDTO;
import com.huboot.business.base_model.weixin_service.dto.util.Pager;
import com.huboot.business.base_model.weixin_service.service.IWeixinUserStatisticService;
import com.huboot.business.common.utils.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;


@Api(tags = "微信设置-商家微信公众号配置信息表 API")
@RestController
@RequestMapping(value = "/base_model/weixin_service/weixinPublic")
public class WeixinPublicController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWeixinPublicService weixinPublicService;
	@Autowired
	private IWeixinUserService weixinUserService;
	@Value("${huboot.thdcWeixinUid}")
	private String thdcWeixinUid;
	@Autowired
	private IWeixinUserStatisticService weixinUserStatisticService;

	@PostMapping(value = "/userYouhua")
	@ApiOperation(response = void.class, value = "初始化用戶信息")
	public void userYouhua() throws Exception {
		new Thread(() -> {
			weixinUserService.userYouhua();
		}).start();
	}

	@GetMapping(value = "/authShopPager")
	@ApiOperation(response = String.class, value = "授权店铺列表")
	public Pager<WexinAuthShopDTO> authShopPager(@RequestParam(value = "shopUid", required = false)String shopUid,
												 @RequestParam("page")Integer page,
												 @RequestParam("size")Integer size) {
		return weixinPublicService.authShopPager(shopUid, page, size);
	}

	@GetMapping(value = "/publicPager")
	@ApiOperation(response = String.class, value = "公众号列表")
	public Pager<WexinPublicPagerDTO> publicPager(@RequestParam(value = "shopUid", required = false)String shopUid,
                                                  @RequestParam(value = "publicUid", required = false)String publicUid,
                                                  @RequestParam("page")Integer page,
                                                  @RequestParam("size")Integer size) {
		return weixinPublicService.publicPager(shopUid, publicUid, page, size);
	}

	@GetMapping(value = "/getQrCode/{weixinUid}")
	@ApiOperation(response = String.class, value = "获取公众号二维码")
	public String getQrCode(@PathVariable("weixinUid") String weixinUid) throws Exception {
		return weixinPublicService.getQrCode(weixinUid);
	}

	@GetMapping(value = "/getWeixinPublic/{weixinUid}")
	@ApiOperation(response = String.class, value = "通过微信uid查询微信信息")
	public WeixinPublicDTO getWeixinPublic(@PathVariable("weixinUid") String weixinUid) {
		return weixinPublicService.getWeixinPublic(weixinUid);
	}

	@PostMapping(value = "/create")
	@ApiOperation(response = void.class, value = "创建公众号")
	public void post(@RequestBody WeixinPublicCreateDTO dto) throws Exception {
		weixinPublicService.create(dto);
	}

	@PostMapping(value = "/initPublic")
	@ApiOperation(response = void.class, value = "初始化公众号")
	public void initPublic(@RequestBody InitPublicFromMPDTO initPublicFromMPDTO) throws Exception {
		weixinPublicService.initPublic(initPublicFromMPDTO.getWeixinUid());
	}

	@PostMapping(value = "/getAuthUrl/{weixinUid}")
	@ApiOperation(response = String.class, value = "获取微信授权认证url")
	public String getAuthUrl(@PathVariable("weixinUid") String weixinUid, @RequestBody WeixinPublicAuthUrlDTO authUrlDTO) {
		return weixinPublicService.getAuthUrl(weixinUid, authUrlDTO);
	}
	@PostMapping(value = "/ticket/{weixinUid}")
	@ApiOperation(response = String.class, value = "获取微信使用JSSDK")
	public WeixinPublicJsapiSignatureDTO getTicket(@PathVariable("weixinUid") String weixinUid, @RequestBody WeixinPublicTicketDTO ticketDTO) {
		return weixinPublicService.getTicket(weixinUid, ticketDTO);
	}
	@GetMapping(value = "/getOpenId")
	@ApiOperation(response = String.class, value = "获取公众号opendid")
	public WeixinAuthDTO getOpenId(@RequestParam("weixinUid") String weixinUid, @RequestParam("wxcode") String wxcode) {
		return weixinPublicService.getOpenId(weixinUid, wxcode);
	}

	@PostMapping(value = "/getShortUrl")
	@ApiOperation(response = String.class, value = "获取短链接")
	public String getShortUrl(@RequestBody Map<String, String> map) {
		String url = map.get("url").toString();
		return weixinPublicService.getShortUrl(url, thdcWeixinUid);
	}

	@GetMapping(value = "/getWxUserInfo")
	@ApiOperation(response = String.class, value = "绑定直客openid跟手机号")
	public WeixinBindUserDTO getWxUserInfo(@RequestParam("weixinUid") String weixinUid, @RequestParam("wxcode") String wxcode) {
		return weixinPublicService.getWxUserInfo(weixinUid, wxcode);
	}


	@PostMapping(value = "/getAndCreatePubQrCode")
	@ApiOperation(response = String.class, value = "获取公众号二维码")
	public String getAndCreatePubQrCode(@RequestBody Map<String, String> map) throws Exception {
		return weixinPublicService.getAndCreatePubQrCode(map);
	}

	@GetMapping(value = "/getSubscribeCountWithSence")
	@ApiOperation(response = String.class, value = "获取通过渠道码关注的人数")
	public WeixinSubscribeCountDTO getSubscribeCountWithSence(@RequestParam("startTime") String startTime,
											  @RequestParam("endTime") String endTime,
											  @RequestParam("weixinUid") String weixinUid,
											  @RequestParam("sence") String sence) {
		return weixinPublicService.getSubscribeCountWithSence(startTime, endTime, weixinUid, sence);
	}

	@PostMapping(value = "/initUserStatistics/{weixinUid}/{shopUid}/{startDate}/{endDate}")
	@ApiOperation(response = void.class, value = "初始化微信用户分析数据")
	public void initUserStatisticsByweixinUidAndShopUid(@PathVariable("weixinUid") String weixinUid,
														@PathVariable("shopUid") String shopUid,
    													@PathVariable("startDate") String startDate,
														@PathVariable("endDate") String endDate) {
		weixinUserStatisticService.initWeixinUserStatistics(weixinUid,shopUid,startDate,endDate);
	}

	@PostMapping(value = "/initUserStatistics/all")
	@ApiOperation(response = void.class, value = "初始化全部店铺微信用户分析数据")
	public void initUserStatisticsAll() {
		weixinUserStatisticService.initAllWeixinUserStatistics();
	}


	@PostMapping(value = "/initUserStatistics/currentDay")
	@ApiOperation(response = void.class, value = "当天用户分析数据")
	public void initUserStatisticsCurrentDay() {
		weixinUserStatisticService.initWeixinUserStatisticsForDay();
	}

    @GetMapping(value = "/getAuthorizerInfoByWxCode/{wxCode}")
    @ApiOperation(response = String.class, value = "通过微信wxCode查询微信信息")
    public Map<String,String> getAuthorizerInfoByWxCode(@PathVariable("wxCode") String wxCode) {
		WeixinBindUserDTO dto = weixinPublicService.getWxUserInfo(thdcWeixinUid, wxCode);
		return JsonUtils.fromObjectToMap(dto);
    }

	@GetMapping(value = "/user/isSubscribe")
	@ApiOperation(response = String.class, value = "查询用户是否关注")
	public Map<String,String> isSubscribe(@RequestParam("openId") String openId) throws Exception{
		return weixinPublicService.isSubscribe(openId);
	}

	@GetMapping(value = "/getShopQrcodeUrl/{shopUid}")
	@ApiOperation(response = String.class, value = "查询用户是否关注")
	public Map<String,String> getShopQrcodeUrl(@PathVariable("shopUid") String shopUid) throws Exception{
		return weixinPublicService.getShopQrcodeUrl(shopUid);
	}

}
