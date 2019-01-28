package com.huboot.file.common.component.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectResult;
import com.huboot.file.common.component.oss.config.AliyunOssProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;
import java.io.File;
import java.io.InputStream;

@Configuration
@EnableConfigurationProperties(AliyunOssProperties.class)
public class AliyunOssSao implements InitializingBean {

    private OSS ossClient;

    private final AliyunOssProperties aliyunOssProperties;

    public AliyunOssSao(AliyunOssProperties aliyunOssProperties) {
        this.aliyunOssProperties = aliyunOssProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        OSSClientBuilder ossClientBuilder = new OSSClientBuilder();
        ossClient = ossClientBuilder.build(aliyunOssProperties.getOutEndpoint(), aliyunOssProperties.getAccessKeyId(), aliyunOssProperties.getAccessKeySecret());
        if (!ossClient.doesBucketExist(aliyunOssProperties.getBucketName())) {
            ossClient.createBucket(aliyunOssProperties.getBucketName());
            CreateBucketRequest createBucketRequest = new CreateBucketRequest(aliyunOssProperties.getBucketName());
            createBucketRequest.setCannedACL(CannedAccessControlList.Private);
            ossClient.createBucket(createBucketRequest);
        }
    }

    public void upload(String filePath, File file) {
        PutObjectResult result = ossClient.putObject(aliyunOssProperties.getBucketName(), filePath, file);
    }

    public void upload(String filePath, InputStream inputStream) {
        PutObjectResult result = ossClient.putObject(aliyunOssProperties.getBucketName(), filePath, inputStream);
    }

    public OSSObject downLoad(String filePath) {
        OSSObject result = ossClient.getObject(aliyunOssProperties.getBucketName(), filePath);
        return result;
    }

    @PreDestroy
    private void destroy(){
        // 关闭OSSClient。
        ossClient.shutdown();
    }

}
