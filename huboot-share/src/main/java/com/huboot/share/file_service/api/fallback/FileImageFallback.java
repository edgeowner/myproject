package com.huboot.share.file_service.api.fallback;

import com.huboot.share.file_service.api.dto.AgentShareDTO;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import com.huboot.share.file_service.api.feign.FileImageFeignClient;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@Component
public class FileImageFallback implements FileImageFeignClient {

    @Override
    public FileDetailResDTO agentShare(AgentShareDTO dto) throws Exception {
        return null;
    }
}
