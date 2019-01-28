package com.huboot.file.file.repository;

import com.huboot.commons.jpa.IBaseRepository;
import com.huboot.file.file.entity.FileEntity;
import org.springframework.stereotype.Repository;

/**
*文件服务-文件信息表Repository
*/
@Repository("fileRepository")
public interface IFileRepository extends IBaseRepository<FileEntity> {

}
