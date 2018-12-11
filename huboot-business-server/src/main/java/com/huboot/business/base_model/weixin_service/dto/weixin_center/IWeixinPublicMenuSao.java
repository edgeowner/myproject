package com.huboot.business.base_model.weixin_service.dto.weixin_center;


import com.huboot.business.base_model.weixin_service.dto.common.constant.ApplicationName;
import com.huboot.business.base_model.weixin_service.dto.weixin_center.dto.WeixinPublicMenuParentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = ApplicationName.XIEHUA_SERVER)
public interface IWeixinPublicMenuSao {

	@GetMapping(value = "/base_model/weixin_service/weixinPublicMenu/getWeixinMentList/{weixinUid}")
	List<WeixinPublicMenuParentDTO> getWeixinMentList(@PathVariable("weixinUid") String weixinUid);

	@PostMapping(value = "/base_model/weixin_service/weixinPublicMenu/saveWeixinMentList/{weixinUid}")
	void saveWeixinMentList(@PathVariable("weixinUid") String weixinUid, @RequestBody List<WeixinPublicMenuParentDTO> menuList);

}
