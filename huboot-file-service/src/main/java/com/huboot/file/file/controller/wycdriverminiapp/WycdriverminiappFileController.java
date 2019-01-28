package com.huboot.file.file.controller.wycdriverminiapp;

import com.huboot.file.file.service.IFileService;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "网约车司机小程序端-文件服务-文件信息表 API")
@RestController
@RequestMapping(value = "/wycdriverminiapp/file/file")
public class WycdriverminiappFileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFileService fileService;

    @PostMapping(value = "")
    @ApiOperation("创建")
    public FileDetailResDTO create(@RequestPart("file") MultipartFile file) throws Exception {
        return fileService.create(file);
    }
}
