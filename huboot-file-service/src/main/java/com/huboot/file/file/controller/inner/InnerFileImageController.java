package com.huboot.file.file.controller.inner;

import com.huboot.file.file.service.IFileService;
import com.huboot.share.file_service.api.dto.AgentShareDTO;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import com.huboot.share.file_service.api.feign.FileImageFeignClient;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "内部端-文件服务-文件信息表 API(处理图片)")
@RestController
public class InnerFileImageController implements FileImageFeignClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFileService fileService;

    @Override
    public FileDetailResDTO agentShare(@RequestBody AgentShareDTO dto) throws Exception {
        logger.info(String.format("朋友圈图片参数：%s | %s", dto.getName(), dto.getQrcodePath()));
        return fileService.agentShare(dto);
    }
}
