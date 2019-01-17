package com.huboot.commons.utils;

import com.huboot.commons.component.exception.BizException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*********
 * Excel 导出文件
 *
 * @author
 *
 */
public class ExcelWriteUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelWriteUtil.class);

    /**
     * xls 格式
     *
     * @param fileName
     * @param title
     * @param keys
     * @param datas
     * @return
     */
    public static ByteFile writeElderExcel(String fileName, String[] title, String[] keys,
                                           List<Map<String, Object>> datas) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("sheet0");
        int rowIndex = 0;
        HSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < title.length; i++) {
            HSSFCell cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(title[i]);
        }
        for (Map<String, Object> rowData : datas) {
            HSSFRow _row = sheet.createRow(rowIndex++);
            for (int cellIndex = 0; cellIndex < keys.length; cellIndex++) {
                HSSFCell _cell = _row.createCell(cellIndex, CellType.STRING);
                _cell.setCellValue(String.valueOf(rowData.get(keys[cellIndex])));
            }
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            out.flush();
            return new ByteFile(fileName, out.toByteArray());
        } catch (Exception e) {
            throw new BizException("导出Excel出错");
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    LOGGER.error("关闭HSSWorkbook ");
                }
            }
        }
    }

    /**
     * 写到文件 xlsx 格式
     *
     * @param fileName
     * @param title
     * @param keys
     * @param datas
     * @return
     */
    public static ByteFile writeExcel(String fileName, String[] title, String[] keys, List<Map<String, Object>> datas) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet0");
        int rowIndex = 0;
        XSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < title.length; i++) {
            XSSFCell cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(title[i]);
        }
        for (Map<String, Object> rowData : datas) {
            XSSFRow _row = sheet.createRow(rowIndex++);
            for (int cellIndex = 0; cellIndex < keys.length; cellIndex++) {
                XSSFCell _cell = _row.createCell(cellIndex, CellType.STRING);
                _cell.setCellValue(String.valueOf(rowData.get(keys[cellIndex])));
            }
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteFile(fileName, out.toByteArray());
        } catch (Exception e) {
            throw new BizException("导出Excel出错");
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    LOGGER.error("关闭HSSWorkbook 出错", e);
                }
            }
        }
    }

    /**
     * 指定类型
     *
     * @param fileName
     * @param titles
     * @param keys
     * @param clazz
     * @param datas
     * @return
     * @throws Exception
     */
    public static ByteFile writeExcel(String fileName, String[] titles, String[] keys, Class<?> clazz,
                                      Object... datas) {
        try {
            return doWriteExcel(fileName, titles, new BeanPropertyValue(clazz, keys), keys, datas);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ByteFile doWriteExcel(String fileName, String[] titles, ObjectPropertyValue beanPropertyValue,
                                         String[] keys, Object[] datas) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet0");
        int rowIndex = 0;
        XSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = row.createCell(i, CellType.STRING);
            cell.setCellValue(titles[i]);
        }
        for (Object rowData : datas) {
            XSSFRow _row = sheet.createRow(rowIndex++);
            for (int cellIndex = 0; cellIndex < keys.length; cellIndex++) {
                XSSFCell _cell = _row.createCell(cellIndex, CellType.STRING);
                _cell.setCellValue(doConvertValue(beanPropertyValue.getValue(rowData, (keys[cellIndex]))));
            }
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteFile(fileName, out.toByteArray());
        } catch (Exception e) {
            throw new BizException("导出Excel出错");
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    LOGGER.error("关闭XSSWorkbook 出错", e);
                }
            }
        }
    }

    public static class BeanPropertyValue implements ObjectPropertyValue {
        private Map<String, PropertyDescriptor> read = new java.util.concurrent.ConcurrentHashMap<>();

        public BeanPropertyValue(Class<?> clazz, String[] keys) throws IntrospectionException {
            for (String key : keys) {
                read.put(key, new PropertyDescriptor(key, clazz));
            }
        }

        @Override
        public Object getValue(Object obj, String key) {
            PropertyDescriptor reader = read.get(key);
            if (reader != null) {
                try {
                    obj = reader.getReadMethod().invoke(obj);
                    return obj;
                } catch (Exception e) {
                    // 此处异常吞掉.
                }
            }
            return null;
        }
    }

    /***
     * 进行值转换
     * @param value
     * @return
     */
    private static String doConvertValue(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof String) {
            return (String) value;
        }
        if (value instanceof Date) {
            DateUtil.formatDefaultDate((Date) value);
        }

        return String.valueOf(value); //默认处理
    }

    interface ObjectPropertyValue {
        public Object getValue(Object obj, String key);
    }


}
