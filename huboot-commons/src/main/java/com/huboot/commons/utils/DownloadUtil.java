package com.huboot.commons.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * Created by Administrator on 2018/5/31 0031.
 */
public class DownloadUtil {

    public static void downloadExcel(HttpServletRequest request, HttpServletResponse response, String fileName) {
        try {
            fileName = fileName + ".xls";
            // 设置response参数，可以打开下载页面
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            String header = request.getHeader("User-Agent").toUpperCase();
            if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE")) {
                fileName = URLEncoder.encode(fileName, "utf-8");
                fileName = fileName.replace("+", "%20");    //IE下载文件名空格变+号问题
            } else {
                fileName = new String(fileName.getBytes(), "ISO-8859-1");
            }
            response.setHeader("content-disposition", "attachment;filename=\"" + fileName + "\"");
        } catch (Exception e) {
            throw new RuntimeException("导出错误");
        }

    }
}
