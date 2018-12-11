package com.huboot.business.common.utils.upyun;

import com.huboot.business.common.utils.DateUtil;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

/**
 * Created by user on 2015/8/20.
 */
public class FileUtils {
    /**
     * 将文本文件中的内容读入到buffer中
     *
     * @param buffer   buffer
     * @param filePath 文件路径
     * @throws IOException 异常
     * @author cn.outofmemory
     * @date 2013-1-7
     */
    public static void readToBuffer(StringBuffer buffer, String filePath) throws IOException {
        InputStream is = new FileInputStream(filePath);
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            buffer.append(line); // 将读到的内容添加到 buffer 中
            buffer.append("\n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        reader.close();
        is.close();
    }

    /**
     * 对文件进行 MD5 加密
     *
     * @param file 待加密的文件
     * @return 文件加密后的 MD5 值
     * @throws IOException
     */
    public static String md5(File file) throws IOException {
        FileInputStream is = new FileInputStream(file);
        return md5(is);
    }

    public static String md5(InputStream is) throws IOException {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
            int n = 0;
            byte[] buffer = new byte[1024];
            do {
                n = is.read(buffer);
                if (n > 0) {
                    md5.update(buffer, 0, n);
                }
            } while (n != -1);
            is.skip(0);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            is.close();
        }

        byte[] encodedValue = md5.digest();

        int j = encodedValue.length;
        char finalValue[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte encoded = encodedValue[i];
            finalValue[k++] = hexDigits[encoded >> 4 & 0xf];
            finalValue[k++] = hexDigits[encoded & 0xf];
        }

        return new String(finalValue);
    }

   /* public static String getFilePath(UploadFileTypeEnum uploadFileType, UploadFileBusinessTypeEnum uploadFileBusinessType, String fileMD5, String fileName, Long id) {
        String md5num = fileMD5.replaceAll("[^0-9]", "");
        StringBuffer buffer = new StringBuffer();
//        final String separator = File.separator;
        final String separator = "/";
        buffer.append(uploadFileType.name().toLowerCase());
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
    }*/

    public static void mkParentDirs(String directory) {
        File file = new File(directory);
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
    }
}