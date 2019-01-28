package com.huboot.file.file.controller.inner;

import com.huboot.file.file.service.IFileService;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import com.huboot.share.file_service.api.feign.FileFeignClient;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "内部端-文件服务-文件信息表 API")
@RestController
public class InnerFileController implements FileFeignClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFileService fileService;

    @Override
    @PostMapping(URL)
    public FileDetailResDTO create(MultipartFile file) throws Exception {
        return fileService.create(file);
    }
}
