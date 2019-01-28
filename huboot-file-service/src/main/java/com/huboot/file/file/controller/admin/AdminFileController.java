package com.huboot.file.file.controller.admin;

import com.huboot.file.file.service.IFileService;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "内管端-文件服务-文件信息表 API")
@RestController
@RequestMapping(value = "/admin/file/file")
public class AdminFileController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFileService fileService;

    @PostMapping(value = "")
    @ApiOperation("创建")
    public FileDetailResDTO create(@RequestPart("file") MultipartFile file) throws Exception {
        return fileService.create(file);
    }

    /*@GetMapping(value = "/agentShare")
    @ApiOperation("agentShare")
    public String agentShare() throws Exception {
        return fileService.agentShare();
    }*/
}
