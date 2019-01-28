package com.huboot.share.file_service.api.feign;

import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import feign.Headers;
import feign.RequestLine;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2018/9/10 0010.
 */
public interface FileFeignClient {

    String URL = "/inner/file/multipart";

    @RequestLine("POST " + URL)
    @Headers("Content-Type: multipart/form-data")
    public FileDetailResDTO create(MultipartFile file) throws Exception;


}
