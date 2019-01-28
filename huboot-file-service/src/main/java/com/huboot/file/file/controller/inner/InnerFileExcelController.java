package com.huboot.file.file.controller.inner;

import com.huboot.file.file.service.IFileService;
import com.huboot.share.file_service.api.dto.AddFileByteInfoDTO;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import com.huboot.share.file_service.api.feign.FileExcelFeignClient;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "内部端-文件服务-文件信息表 API(处理图片)")
@RestController
public class InnerFileExcelController implements FileExcelFeignClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFileService fileService;

    @Override
    public FileDetailResDTO create(@RequestBody AddFileByteInfoDTO dto) throws Exception {
        return fileService.createForInner(dto);
    }
}
