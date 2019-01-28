package com.huboot.share.file_service.api.fallback;

import com.huboot.share.file_service.api.dto.AddFileByteInfoDTO;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import com.huboot.share.file_service.api.feign.FileExcelFeignClient;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
@Component
public class FileExcelFallback implements FileExcelFeignClient {

    @Override
    public FileDetailResDTO create(AddFileByteInfoDTO dto) throws Exception {
        return null;
    }
}
