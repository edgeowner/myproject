package com.huboot.business.common.utils;

import com.huboot.business.common.component.exception.BizException;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/*********
 * Excel 导出文件
 *
 * @author
 *
 */
public class ExcelWriteUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelWriteUtils.class);

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
            HSSFCell cell = row.createCell(i, 1);
            cell.setCellValue(title[i]);
        }
        for (Map<String, Object> rowData : datas) {
            HSSFRow _row = sheet.createRow(rowIndex++);
            for (int cellIndex = 0; cellIndex < keys.length; cellIndex++) {
                HSSFCell _cell = _row.createCell(cellIndex, 1);
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

    /***********
     * xls 格式
     *
     * @param fileName
     * @param titles
     * @param keys
     * @param datas
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ByteFile writeElderExcel(String fileName, String[] titles, String[] keys, Object... datas) {
        // 先偷懒实现
        List<Map<String, Object>> _datas = new ArrayList<>();
        for (Object o : datas) {
            if (o instanceof Map) {
                _datas.add((Map<String, Object>) o);
            } else {
                try {
                    _datas.add(JsonUtils.fromObjectToMap(o));
                } catch (Exception e) {
                    throw new BizException("导出Excel出错");
                }
            }
        }
        return writeElderExcel(fileName, titles, keys, _datas);

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
            XSSFCell cell = row.createCell(i, 1);
            cell.setCellValue(title[i]);
        }
        for (Map<String, Object> rowData : datas) {
            XSSFRow _row = sheet.createRow(rowIndex++);
            for (int cellIndex = 0; cellIndex < keys.length; cellIndex++) {
                XSSFCell _cell = _row.createCell(cellIndex, 1);
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

    /***
     * 将数据 导出到 xlsx
     *
     * @param fileName
     * @param titles
     * @param keys
     * @param datas
     * @return
     */
    @SuppressWarnings("unchecked")
    public static ByteFile writeExcel(String fileName, String[] titles, String[] keys, Object... datas) {
        // 先偷懒实现
        List<Map<String, Object>> _datas = new ArrayList<>();

        for (Object o : datas) {
            if (o instanceof Map) {
                _datas.add((Map<String, Object>) o);
            } else {
                try {
                    _datas.add(JsonUtils.fromObjectToMap(o));
                } catch (Exception e) {
                    throw new BizException("导出Excel出错");
                }
            }
        }
        return writeExcel(fileName, titles, keys, _datas);
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
            XSSFCell cell = row.createCell(i, 1);
            cell.setCellValue(titles[i]);
        }
        for (Object rowData : datas) {
            XSSFRow _row = sheet.createRow(rowIndex++);
            for (int cellIndex = 0; cellIndex < keys.length; cellIndex++) {
                XSSFCell _cell = _row.createCell(cellIndex, 1);
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

    /**
     * sku车型定制导出
     *
     * @param fileName
     * @param titles
     * @param keys
     * @param clazz
     * @param datas
     * @return
     * @throws Exception
     */
    public static ByteFile writeSkuExcel(String fileName, String[] titles, String[] keys, Class<?> clazz,
                                         Object... datas) {
        try {
            return doWriteSkuExcel(fileName, titles, new BeanPropertyValue(clazz, keys), keys, datas);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static ByteFile doWriteSkuExcel(String fileName, String[] titles, ObjectPropertyValue beanPropertyValue,
                                            String[] keys, Object[] datas) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet0");
        sheet.setColumnWidth(0, 10 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 10 * 256);
        sheet.setColumnWidth(1, 25 * 256);
        sheet.setColumnWidth(5, 45 * 256);
        sheet.setColumnWidth(9, 50 * 256);
        int rowIndex = 0;
        XSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = row.createCell(i, 1);
            cell.setCellValue(titles[i]);
        }
        for (Object rowData : datas) {
            XSSFRow _row = sheet.createRow(rowIndex++);
            for (int cellIndex = 0; cellIndex < keys.length; cellIndex++) {
                XSSFCell _cell = _row.createCell(cellIndex, 1);
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


    /**
     * 余额提现操作定制导出
     *
     * @param fileName
     * @param titles
     * @param keys
     * @param clazz
     * @param datas
     * @return
     * @throws Exception
     */
    public static ByteFile writeBalancePayForAnotherExcel(String fileName, String[] titles, String[] keys, Map<String, Object> totalMap, String applyStartTime, String applyEndTime, Class<?> clazz,
                                                          Object... datas) {
        try {
            return doWriteBalancePayForAnotherExcel(fileName, titles, new BeanPropertyValue(clazz, keys), keys, datas, totalMap, applyStartTime, applyEndTime);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static ByteFile doWriteBalancePayForAnotherExcel(String fileName, String[] titles, ObjectPropertyValue beanPropertyValue,
                                                             String[] keys, Object[] datas, Map<String, Object> totalMap, String applyStartTime, String applyEndTime) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet0");
        sheet.setColumnWidth(0, 20 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 20 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(1, 30 * 256);
        sheet.setColumnWidth(5, 30 * 256);
        sheet.setColumnWidth(6, 20 * 256);
        int rowIndex = 0;

        //设置申请提现日期
        XSSFRow applyDateRow = sheet.createRow(rowIndex++);
        XSSFCell applyDateRowCell = applyDateRow.createCell(0, 1);
        applyDateRowCell.setCellValue("提现申请日期");
        XSSFCell applyDateRowValueCell = applyDateRow.createCell(1, 1);
        applyDateRowValueCell.setCellValue(applyStartTime + " - " + applyEndTime);

        //设置提现申请数
        XSSFRow totalRow = sheet.createRow(rowIndex++);
        XSSFCell totalCell = totalRow.createCell(0, 1);
        XSSFCell totalValueCell = totalRow.createCell(1, 1);
        totalCell.setCellValue("提现申请数:");
        Long total = (Long) totalMap.get("totalRecord");
        totalValueCell.setCellValue(total.toString() + "条");
        //设置申请提现总额
        XSSFRow applyCashMoneyRow = sheet.createRow(rowIndex++);
        XSSFCell applyCashMoneyCell = applyCashMoneyRow.createCell(0, 1);
        XSSFCell applyCashMoneyValueCell = applyCashMoneyRow.createCell(1, 1);
        applyCashMoneyCell.setCellValue("申请提现总额:");
        applyCashMoneyValueCell.setCellValue(((BigDecimal) totalMap.get("applyCashMoneyTotal")).toString() + "元");
        //设置应代付总额
        XSSFRow payForAnotherMoneyRow = sheet.createRow(rowIndex++);
        XSSFCell payForAnotherMoneyCell = payForAnotherMoneyRow.createCell(0, 1);
        XSSFCell payForAnotherMoneyValueCell = payForAnotherMoneyRow.createCell(1, 1);
        payForAnotherMoneyCell.setCellValue("应代付总额:");
        payForAnotherMoneyValueCell.setCellValue(((BigDecimal) totalMap.get("payForAnotherMoneyTotal")).toString() + "（含代付手续费" + total * 1.5 + "元）");


        //设置列表
        rowIndex = rowIndex + 1;//空一行
        XSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = row.createCell(i, 1);
            cell.setCellValue(titles[i]);
        }
        for (Object rowData : datas) {
            XSSFRow _row = sheet.createRow(rowIndex++);
            for (int cellIndex = 0; cellIndex < keys.length; cellIndex++) {
                XSSFCell _cell = _row.createCell(cellIndex, 1);
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

    /**
     * 红包账户明细定制导出
     *
     * @param fileName
     * @param titles
     * @param keys
     * @param clazz
     * @param datas
     * @return
     * @throws Exception
     */
    public static ByteFile writeRedPacketExcel(String fileName, String[] titles, String[] keys, Map<String, Object> totalMap, Class<?> clazz,
                                               Object... datas) {
        try {
            return doWriteRedPacketExcel(fileName, titles, new BeanPropertyValue(clazz, keys), keys, datas, totalMap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static ByteFile doWriteRedPacketExcel(String fileName, String[] titles, ObjectPropertyValue beanPropertyValue,
                                                             String[] keys, Object[] datas, Map<String, Object> totalMap) {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet0");
        int rowIndex = 0;

        //设置发放红包总额
        XSSFRow applyDateRow = sheet.createRow(rowIndex++);
        XSSFCell applyDateRowCell = applyDateRow.createCell(0, 1);
        XSSFCell applyDateRowValueCell = applyDateRow.createCell(1, 1);
        applyDateRowCell.setCellValue("发放红包总额:");
        applyDateRowValueCell.setCellValue(totalMap.get("sendAmount").toString() + "元");

        //设置已使用红包总额
        XSSFRow totalRow = sheet.createRow(rowIndex++);
        XSSFCell totalCell = totalRow.createCell(0, 1);
        XSSFCell totalValueCell = totalRow.createCell(1, 1);
        totalCell.setCellValue("已使用红包总额:");
        totalValueCell.setCellValue(totalMap.get("usedAmount").toString() + "元");
        //设置已失效红包总额
        XSSFRow applyCashMoneyRow = sheet.createRow(rowIndex++);
        XSSFCell applyCashMoneyCell = applyCashMoneyRow.createCell(0, 1);
        XSSFCell applyCashMoneyValueCell = applyCashMoneyRow.createCell(1, 1);
        applyCashMoneyCell.setCellValue("已失效红包总额:");
        applyCashMoneyValueCell.setCellValue(totalMap.get("expireAmount").toString() + "元");
        //设置未使用红包总额
        XSSFRow payForAnotherMoneyRow = sheet.createRow(rowIndex++);
        XSSFCell payForAnotherMoneyCell = payForAnotherMoneyRow.createCell(0, 1);
        XSSFCell payForAnotherMoneyValueCell = payForAnotherMoneyRow.createCell(1, 1);
        payForAnotherMoneyCell.setCellValue("未使用红包总额:");
        payForAnotherMoneyValueCell.setCellValue(totalMap.get("useAmount").toString() + "元");


        //设置列表
        rowIndex = rowIndex + 1;//空一行
        XSSFRow row = sheet.createRow(rowIndex++);
        for (int i = 0; i < titles.length; i++) {
            XSSFCell cell = row.createCell(i, 1);
            cell.setCellValue(titles[i]);
        }
        for (Object rowData : datas) {
            XSSFRow _row = sheet.createRow(rowIndex++);
            for (int cellIndex = 0; cellIndex < keys.length; cellIndex++) {
                XSSFCell _cell = _row.createCell(cellIndex, 1);
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


    public static class ByteFile implements Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public ByteFile() {
        }

        public ByteFile(String fileName,byte[] data){
            this.fileName = fileName;
            this.content = data;
            this.size = data==null ? 0: data.length;
        }

        /**
         * 文件名
         */
        private String fileName;

        private byte []  content;

        private long size;

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public byte[] getContent() {
            return content;
        }

        public void setContent(byte[] content) {
            this.content = content;
        }

        public long getSize() {
            return size;
        }

        public void setSize(long size) {
            this.size = size;
        }



    }



}
