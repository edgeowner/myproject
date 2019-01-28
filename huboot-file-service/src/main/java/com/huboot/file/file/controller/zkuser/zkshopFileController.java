package com.huboot.file.file.controller.zkuser;

import com.huboot.file.file.controller.BaseController;
import com.huboot.file.file.service.IFileService;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "直客商家端-文件服务-文件信息表 API")
@RestController
@RequestMapping(value = "/zkshop/file/file")
public class zkshopFileController extends BaseController {


    @Autowired
    private IFileService fileService;

    @PostMapping(value = "")
    @ApiOperation("上传")
    public FileDetailResDTO create(@RequestPart("file") MultipartFile file) throws Exception {
        return fileService.create(file);
    }


}
