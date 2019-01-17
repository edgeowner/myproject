package com.huboot.share.common.system;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ConnectionController implements IConnectionController {

	@GetMapping(URL)
	public String connection(@PathVariable("fromService")String fromService) {
		log.info("收到来自" + fromService + "的连接请求");
		return "ok";
	}

}
