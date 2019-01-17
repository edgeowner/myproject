package com.huboot.share.user_service.api.feign;

import com.huboot.share.common.feign.FeignCilentConfig;
import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.user_service.api.dto.QrcodeCreateDTO;
import com.huboot.share.user_service.api.fallback.MiniAppFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@FeignClient(name = ServiceName.USER_SERVICE, configuration = FeignCilentConfig.class, fallback = MiniAppFallback.class)
public interface MiniAppFeignCilent {

    /**
     * 小程序场景吗
     * @param createDTO
     * @return
     */
    @PostMapping("/inner/user/miniapp/create_qrcode")
    String createQrcode(@RequestBody QrcodeCreateDTO createDTO);

    /**
     * 小程序路径参数吗
     * @param createDTO
     * @return
     */
    @PostMapping("/inner/user/miniapp/create_path_qrcode")
    String createPathQrcode(@RequestBody QrcodeCreateDTO createDTO);
}
