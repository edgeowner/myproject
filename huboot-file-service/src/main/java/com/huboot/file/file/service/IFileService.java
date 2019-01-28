package com.huboot.file.file.service;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.file.file.dto.FileModifyReqDTO;
import com.huboot.file.file.dto.FilePageResDTO;
import com.huboot.file.file.dto.FileQueryReqDTO;
import com.huboot.share.file_service.api.dto.AddFileByteInfoDTO;
import com.huboot.share.file_service.api.dto.AddFileInputStreamInfoDTO;
import com.huboot.share.file_service.api.dto.AgentShareDTO;
import org.springframework.web.multipart.MultipartFile;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;


import java.io.File;
import java.io.IOException;

/**
 * 文件服务-文件信息表Service
 */
public interface IFileService {

    /**
     * 创建
     *
     * @param file
     * @throws BizException
     */
    FileDetailResDTO create(MultipartFile file) throws BizException, IOException;


    FileDetailResDTO createForInner(File file) throws BizException, IOException;
    FileDetailResDTO createForInner(AddFileByteInfoDTO dto) throws BizException, IOException;
    FileDetailResDTO createForInner(AddFileInputStreamInfoDTO dto) throws BizException, IOException;

    /**
     * 更新
     *
     * @param modifyReqDTO
     * @throws BizException
     */
    void modify(FileModifyReqDTO modifyReqDTO) throws BizException;

    /**
     * 查询
     *
     * @param id
     * @return
     * @throws BizException
     */
    FileDetailResDTO find(Long id) throws BizException;

    /**
     * 删除
     *
     * @param id
     * @throws BizException
     */
    void delete(Long id) throws BizException;

    /**
     * 分页查询
     *
     * @param queryReqDTO
     * @return
     * @throws BizException
     */
    ShowPageImpl<FilePageResDTO> findPage(FileQueryReqDTO queryReqDTO) throws BizException;

    /**
     * 经纪人分享图片
     *
     * @param dto
     * @return
     * @throws Exception
     */
    FileDetailResDTO agentShare(AgentShareDTO dto) throws Exception;

}
