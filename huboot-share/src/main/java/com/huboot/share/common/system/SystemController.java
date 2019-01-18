package com.huboot.share.common.system;

import com.huboot.share.common.feign.ResultErrorDecoder;
import com.huboot.share.common.feign.RequestHeaderInterceptor;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.encoding.FeignClientEncodingProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "服务连接测试 API")
@RestController
@EnableConfigurationProperties(FeignClientEncodingProperties.class)
public class SystemController {

	@Autowired
	private FeignClientEncodingProperties properties;
	/*@Autowired
	private Decoder decoder;*/
	@Autowired
	private Client client;
	@Value("${spring.application.name:none}")
	private String applicationName;

	@GetMapping("/admin/system/test_connection/{serviceName}")
	public String connection(@PathVariable("serviceName")String serviceName) {
		IConnectionController connectionController = this.getConnectionController(serviceName);
		return connectionController.connection(applicationName);
	}

	private IConnectionController getConnectionController(String serviceName) {
		return Feign.builder()
				.client(client)
				//.decoder(decoder)
				.errorDecoder(new ResultErrorDecoder())
				.requestInterceptor(new RequestHeaderInterceptor(properties))
				.target(IConnectionController.class, "http://" + serviceName.toUpperCase());
	}

}
