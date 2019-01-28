package com.huboot.file.file.service.impl;

import com.huboot.commons.component.exception.BizException;
import com.huboot.commons.jpa.QueryCondition;
import com.huboot.commons.page.ShowPageImpl;
import com.huboot.file.common.component.oss.AliyunOssSao;
import com.huboot.file.common.config.FileDomainProperties;
import com.huboot.file.common.utils.FilePathGenerator;
import com.huboot.file.file.dto.FileModifyReqDTO;
import com.huboot.file.file.dto.FilePageResDTO;
import com.huboot.file.file.dto.FileQueryReqDTO;
import com.huboot.file.file.service.IFileService;
import com.huboot.file.common.utils.ImageUtil;
import com.huboot.file.file.entity.FileEntity;
import com.huboot.file.file.repository.IFileRepository;
import com.huboot.share.file_service.api.dto.AddFileByteInfoDTO;
import com.huboot.share.file_service.api.dto.AddFileInputStreamInfoDTO;
import com.huboot.share.file_service.api.dto.AgentShareDTO;
import com.huboot.share.file_service.api.dto.FileDetailResDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

/**
 * 文件服务-文件信息表ServiceImpl
 */
@Service("fileServiceImpl")
public class FileServiceImpl implements IFileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private IFileRepository fileRepository;
    @Autowired
    private AliyunOssSao aliyunOssSao;
    @Autowired
    private FileDomainProperties fileDomainProperties;

    @Transactional
    @Override
    public FileDetailResDTO create(MultipartFile file) throws BizException, IOException {
        String fileName = file.getOriginalFilename();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadPath = FilePathGenerator.getFilePath(FilePathGenerator.UploadFileTypeEnum.img, fileName);
        try {
            aliyunOssSao.upload(uploadPath, file.getInputStream());
        } catch (Exception e) {
            throw new BizException("上传失败");
        }
        String fullPath = fileDomainProperties.getDomain() + "/" + uploadPath;
        FileEntity entity = new FileEntity();
        entity.setExt(suffix);
        entity.setFileSize(String.valueOf(file.getSize()));
        entity.setFullPath(fullPath);
        entity.setName(file.getOriginalFilename());
        entity.setPath(uploadPath);
        fileRepository.create(entity);
        FileDetailResDTO fileResDTO = new FileDetailResDTO();
        fileResDTO.setId(entity.getId());
        fileResDTO.setFullPath(entity.getFullPath());
        return fileResDTO;
    }

    @Transactional
    @Override
    public FileDetailResDTO createForInner(File file) throws BizException, IOException {
        String fileName = file.getName();
        String prefix = fileName.substring(0, fileName.lastIndexOf("."));
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String uploadPath = FilePathGenerator.getFilePath(FilePathGenerator.UploadFileTypeEnum.img, fileName);
        try {
            aliyunOssSao.upload(uploadPath, file);
        } catch (Exception e) {
            throw new BizException("上传失败");
        }
        String fullPath = fileDomainProperties.getDomain() + "/" + uploadPath;
        FileEntity entity = new FileEntity();
        entity.setExt(suffix);
        entity.setFileSize(String.valueOf(file.length()));
        entity.setFullPath(fullPath);
        entity.setName(prefix);
        entity.setPath(uploadPath);
        fileRepository.create(entity);
        FileDetailResDTO fileResDTO = new FileDetailResDTO();
        fileResDTO.setId(entity.getId());
        fileResDTO.setFullPath(entity.getFullPath());
        return fileResDTO;
    }

    @Override
    public FileDetailResDTO createForInner(@Valid AddFileByteInfoDTO dto) throws BizException, IOException {
        InputStream sbs = new ByteArrayInputStream(dto.getDataBytes());
        String uploadPath = FilePathGenerator.getFilePath(FilePathGenerator.UploadFileTypeEnum.img, dto.getName() + "." + dto.getExt());
        try {
            aliyunOssSao.upload(uploadPath, sbs);
        } catch (Exception e) {
            throw new BizException("上传失败");
        }
        String fullPath = fileDomainProperties.getDomain() + "/" + uploadPath;
        FileEntity entity = new FileEntity();
        entity.setExt(dto.getExt());
        entity.setFileSize("");
        entity.setFullPath(fullPath);
        entity.setName(dto.getName());
        entity.setPath(uploadPath);
        fileRepository.create(entity);
        FileDetailResDTO fileResDTO = new FileDetailResDTO();
        fileResDTO.setId(entity.getId());
        fileResDTO.setFullPath(entity.getFullPath());
        return fileResDTO;
    }

    @Override
    public FileDetailResDTO createForInner(AddFileInputStreamInfoDTO dto) throws BizException, IOException {
        FileChannel fc = null;
        try {
            fc = dto.getFileInputStream().getChannel();
            String uploadPath = FilePathGenerator.getFilePath(FilePathGenerator.UploadFileTypeEnum.img, dto.getName() + "." + dto.getExt());
            try {
                aliyunOssSao.upload(uploadPath, dto.getFileInputStream());
            } catch (Exception e) {
                throw new BizException("上传失败");
            }
            String fullPath = fileDomainProperties.getDomain() + "/" + uploadPath;
            FileEntity entity = new FileEntity();
            entity.setExt(dto.getExt());
            entity.setFileSize(String.valueOf(fc.size()));
            entity.setFullPath(fullPath);
            entity.setName(dto.getName());
            entity.setPath(uploadPath);
            fileRepository.create(entity);
            FileDetailResDTO fileResDTO = new FileDetailResDTO();
            fileResDTO.setId(entity.getId());
            fileResDTO.setFullPath(entity.getFullPath());
            return fileResDTO;
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {

                }
            }
        }
    }

    @Transactional
    @Override
    public void modify(FileModifyReqDTO modifyReqDTO) throws BizException {
        FileEntity entity = fileRepository.find(modifyReqDTO.getId());
        BeanUtils.copyProperties(modifyReqDTO, entity);
        fileRepository.modify(entity);
    }

    @Transactional
    @Override
    public void delete(Long id) throws BizException {
        fileRepository.remove(id);
    }

    @Override
    public FileDetailResDTO find(Long id) throws BizException {
        FileEntity entity = fileRepository.find(id);
        FileDetailResDTO dto = new FileDetailResDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    @Override
    public ShowPageImpl<FilePageResDTO> findPage(FileQueryReqDTO queryReqDTO) throws BizException {

        Page<FileEntity> page = fileRepository.findPage(QueryCondition.from(FileEntity.class).where(list -> {

        }).sort(Sort.by("id").descending()).limit(queryReqDTO.getPage(), queryReqDTO.getSize()));

        return new ShowPageImpl<>(page).map(entity -> {
            FilePageResDTO dto = new FilePageResDTO();
            BeanUtils.copyProperties(entity, dto);
            return dto;
        });
    }

    @Override
    public FileDetailResDTO agentShare(AgentShareDTO dto) throws Exception {
        byte[] fileBytes = ImageUtil.agentShare(fileDomainProperties.getDomain(), dto.getQrcodePath());
        AddFileByteInfoDTO byteInfo = new AddFileByteInfoDTO();
        byteInfo.setName(dto.getName());
        byteInfo.setExt("png");
        byteInfo.setDataBytes(fileBytes);
        return createForInner(byteInfo);
    }
}
