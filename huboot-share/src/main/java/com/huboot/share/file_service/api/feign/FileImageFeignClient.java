package com.huboot.share.file_service.api.feign;

import com.huboot.share.common.constant.ServiceName;
import com.huboot.share.common.feign.FeignCilentConfig;
import com.huboot.share.file_service.api.dto.AgentShareDTO;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import com.huboot.share.file_service.api.fallback.FileImageFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@FeignClient(name = ServiceName.FILE_SERVICE, configuration = FeignCilentConfig.class, fallback = FileImageFallback.class)
public interface FileImageFeignClient {

    @PostMapping("/inner/file/image/agent_share")
    public FileDetailResDTO agentShare(@RequestBody AgentShareDTO dto) throws Exception;


}
