package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.service.IWeixinTempalteService;
import com.huboot.business.base_model.weixin_service.dto.WeixinTempalteAddDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "微信设置-微信模板信息表 API")
@RestController
@RequestMapping(value = "/base_model/weixin_service/weixinTempalte")
public class WeixinTempalteController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWeixinTempalteService weixinTempalteService;

	@PostMapping(value = "/create/{initType}")
	@ApiOperation(response = void.class, value = "创建")
	public Integer create(@PathVariable("initType") Integer initType, @RequestBody WeixinTempalteAddDTO dto) throws Exception {
		return weixinTempalteService.create(dto, initType);
	}

	@PostMapping(value = "/initTempalteForAllPublic/{stid}")
	@ApiOperation(response = void.class, value = "初始化添加公众号消息模板")
	public void initTempalteForAllPublic(@PathVariable("stid") Integer stid) throws Exception {
		weixinTempalteService.initTempalteForAllPublic(stid);
	}

}
