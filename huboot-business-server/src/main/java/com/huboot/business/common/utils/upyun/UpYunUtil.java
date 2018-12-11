package com.huboot.business.common.utils.upyun;

import com.huboot.business.base_model.login.sso.client.dto.FileServiceTypeEnum;
import com.huboot.business.base_model.login.sso.client.dto.UploadFileBusinessTypeEnum;
import com.huboot.business.base_model.login.sso.client.dto.UploadFileTypeEnum;
import com.huboot.business.common.utils.DateUtil;
import main.java.com.UpYun;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

/**
 * 又拍云工具类
 *
 * @author Tory.li
 * @create 2017-02-28 14:25
 **/
public class UpYunUtil implements Serializable {

    //服务名（存放需要带水印的公共图片）
    private static final String PUBLIC_IMG_BUCKET_NAME = "zchz-img1";
    //服务名（存放客户相关的私有图片）
    private static final String PRIVATE_IMG_BUCKET_NAME = "zchz-img2";
    //服务名（存放公共文件）
    private static final String PUBLIC_FILE_BUCKET_NAME = "zchz-file1";
    //服务名（存放客户相关的私有文件）
    private static final String PRIVATE_FILE_BUCKET_NAME = "zchz-file2";
    //操作员名称
    private static final String USER_NAME = "lijian";
    //操作员密码
    private static final String PASSWORD = "upyun_LIJIAN2016";

    //域名列表
    private static final String IMG1_DOMAIN = "http://img1.zchz.com";
    private static final String IMG2_DOMAIN = "http://img2.zchz.com";
    private static final String FILE1_DOMAIN = "http://file1.zchz.com";
    private static final String FILE2_DOMAIN = "http://file2.zchz.com";

    private static final UpYun zchzImg1Config = new UpYun(PUBLIC_IMG_BUCKET_NAME, USER_NAME, PASSWORD);
    private static final UpYun zchzImg2Config = new UpYun(PRIVATE_IMG_BUCKET_NAME, USER_NAME, PASSWORD);
    private static final UpYun zchzFile1Config = new UpYun(PUBLIC_FILE_BUCKET_NAME, USER_NAME, PASSWORD);
    private static final UpYun zchzFile2Config = new UpYun(PRIVATE_FILE_BUCKET_NAME, USER_NAME, PASSWORD);

    public static Boolean writeFile(FileServiceTypeEnum type, String picPath, File file, String contentSecret) throws IOException {
        UpYun upyun = getUpYunConfig(type);
        upyun.setFileSecret(contentSecret);
        return upyun.writeFile(picPath, file, true);
    }

    public static Boolean writeFile(FileServiceTypeEnum type, String picPath, byte[] datas, String contentSecret) throws IOException {
        UpYun upyun = getUpYunConfig(type);
        upyun.setFileSecret(contentSecret);
        return upyun.writeFile(picPath, datas, true);
    }



    public static UpYun getUpYunConfig(FileServiceTypeEnum type) {
        UpYun upyun;
        if (type.equals(FileServiceTypeEnum.PublicImage)) {
            upyun = zchzImg1Config;
        } else if (type.equals(FileServiceTypeEnum.PrivateImage)) {
            upyun = zchzImg2Config;
        } else if (type.equals(FileServiceTypeEnum.PublicFile)) {
            upyun = zchzFile1Config;
        } else {
            upyun = zchzFile2Config;
        }
        return upyun;
    }

    public static String getDomain(FileServiceTypeEnum type) {
        if (type.equals(FileServiceTypeEnum.PublicImage)) {
            return IMG1_DOMAIN;
        } else if (type.equals(FileServiceTypeEnum.PrivateImage)) {
            return IMG2_DOMAIN;
        } else if (type.equals(FileServiceTypeEnum.PublicFile)) {
            return FILE1_DOMAIN;
        } else {
            return FILE2_DOMAIN;
        }
    }

    public static String getFilePath(UploadFileTypeEnum uploadFileType, String service, UploadFileBusinessTypeEnum uploadFileBusinessType, String fileMD5, String fileName, Long id) {
        String md5num = fileMD5.replaceAll("[^0-9]", "");
        StringBuffer buffer = new StringBuffer();
        final String separator = "/";
        buffer.append(separator);
        buffer.append(uploadFileType.name().toLowerCase());
        buffer.append(separator);
        buffer.append(service);
        buffer.append(separator);
        buffer.append(uploadFileBusinessType.name().toLowerCase());
        buffer.append(separator);
        buffer.append(DateUtil.dateToString(new Date(), "yyyy"));
        buffer.append(separator);
        buffer.append(fileMD5.substring(0, 3));
        buffer.append(separator);
        buffer.append(fileMD5.substring(3, 6));
        buffer.append(separator);
        if (!(id == null || id == 0l)) {
            buffer.append(id);
            buffer.append("_");
        }
        if (md5num == "") {
            int sIndex = fileMD5.length() - 14;
            sIndex = sIndex < 0 ? 0 : sIndex;
            buffer.append(fileMD5.substring(sIndex));
        } else {
            if (md5num.length() > 18) {
                md5num = md5num.substring(0, 18);
            }
            buffer.append(Long.toHexString(Long.valueOf(md5num)));
        }
        buffer.append(".");
        buffer.append(fileName.substring(fileName.lastIndexOf(".") + 1));
        return buffer.toString();
    }

    public static void main(String[] args) throws Exception {
        /*System.out.println(UpYun.md5("3879987967049203712"));*/
        UpYun upyun = getUpYunConfig(FileServiceTypeEnum.PrivateImage);

        boolean result = upyun.readFile("/img/company/2017/aca/22b/3879987967061786624_32da881f8c8db92.png", new File("D:\\123.png"));
        upyun.setFileSecret(UpYun.md5("3879987967049203712"));
        upyun.writeFile("/test/123.png", new File("D:/123.png"), true);
    }

}
