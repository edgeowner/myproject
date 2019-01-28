package com.huboot.file.common.utils;

import com.huboot.commons.enums.BaseEnum;
import com.huboot.commons.utils.keyGenerator.PrimaryKeyGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

public class FilePathGenerator {

    public static String getFilePath(UploadFileTypeEnum uploadFileType, String fileName) {
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        LocalDate localDate = LocalDate.now();
        String path = uploadFileType.toString() + "/" + localDate.getYear() + "/" + localDate.getMonthValue() + "/" + localDate.getDayOfMonth() + "/" + PrimaryKeyGenerator.SEQUENCE.next() + "." + suffix;
        return path;
    }

    @AllArgsConstructor
    @Getter
    public enum UploadFileTypeEnum implements BaseEnum {

        img("图片"), word("word"), video("视频"), ppt("ppt"), excel("excel");

        private String showName;
    }

    @AllArgsConstructor
    @Getter
    public enum UploadFileExtEnum implements BaseEnum {

        jpg("jpg"), png("png");

        private String showName;
    }
}
