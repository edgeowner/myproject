package com.huboot.file.file.controller.wycshop;

import com.huboot.file.file.controller.BaseController;
import com.huboot.file.file.service.IFileService;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Api(tags = "网约车管理端-文件服务-文件信息表 API")
@RestController
@RequestMapping(value = "/wycshop/file/file")
public class WycshopFileController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFileService fileService;

    @PostMapping(value = "")
    @ApiOperation("上传")
    public FileDetailResDTO create(@RequestPart("file") MultipartFile file) throws Exception {
        return fileService.create(file);
    }

    /*@RequestMapping(value = "/download",method = {RequestMethod.POST,RequestMethod.GET})
    @ApiOperation("下载:通过参数url下载-阿里云的文件服务")
    public ResponseEntity<InputStreamResource> download(@Valid FileDownloadDTO dto) throws Exception {
        return exportFromAliyun(dto.getTargetUrl(),inputStream);
    }*/
}
