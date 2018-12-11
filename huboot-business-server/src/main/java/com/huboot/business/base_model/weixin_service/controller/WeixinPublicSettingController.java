package com.huboot.business.base_model.weixin_service.controller;

import com.huboot.business.base_model.weixin_service.dto.WeixinPublicSettingDTO;
import com.huboot.business.base_model.weixin_service.dto.WeixinPublicSettingQueryDTO;
import com.huboot.business.base_model.weixin_service.service.IWeixinPublicSettingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "商家微信公众号配置信息表 API")
@RestController
@RequestMapping(value = "/base_model/weixin_service/weixinPublicSetting")
public class WeixinPublicSettingController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWeixinPublicSettingService weixinPublicSettingService;

	@PostMapping(value = "/")
	@ApiOperation(response = void.class, value = "创建")
	public void post(@RequestBody WeixinPublicSettingDTO dto) throws Exception {
		weixinPublicSettingService.create(dto);
	}

    @PatchMapping(value = "/{id}")
	@ApiOperation(response = void.class, value = "更新")
    public void update(@PathVariable("id") Integer id, @RequestBody WeixinPublicSettingDTO dto) throws Exception {
		dto.setId(id);
		weixinPublicSettingService.update(dto);
    }

    @GetMapping(value = "/{id}")
	@ApiOperation(response = WeixinPublicSettingDTO.class, value = "查询")
    public WeixinPublicSettingDTO get(@PathVariable("id") Integer id) throws Exception {
		WeixinPublicSettingDTO dto = weixinPublicSettingService.find(id);
		return dto;
    }

    @DeleteMapping(value = "/{id}")
	@ApiOperation(response = void.class, value = "删除")
	public void delete(@PathVariable("id") Integer id) throws Exception {
		weixinPublicSettingService.delete(id);
    }

	@GetMapping(value = "/page")
	@ApiOperation(response = WeixinPublicSettingDTO.class, value = "分页")
    public Page<WeixinPublicSettingDTO> findPage(WeixinPublicSettingQueryDTO queryDTO) throws Exception {
		Page<WeixinPublicSettingDTO> page = weixinPublicSettingService.findPage(queryDTO);
    	return page;
    }
}
