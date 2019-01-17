package com.huboot.commons.utils;

import com.huboot.commons.component.exception.BizException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/***
 * Excel 文件读取工具类
 *
 * @author Coollf
 *
 */
public class ExcelReadUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExcelReadUtil.class);

    public enum ExcelType {
        XLSX, XLS;
    }

    public static <T> List<T> readAllObject(final InputStream input, final ExcelType type, final int sheetIndex,
                                            final Class<T> clazz, final int startRow, final String... fieldNames) throws Exception {
        List<T> datas = new ArrayList<>();

        List<Row> rows = null;
        if (type == ExcelType.XLS) {
            rows = loadXls(input, sheetIndex);
        } else if (type == ExcelType.XLSX) {
            rows = loadXlsx(input, sheetIndex);
        }
        final PropertyDescriptor[] propertyDescriptors = new PropertyDescriptor[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            String fieldName = fieldNames[i];
            propertyDescriptors[i] = new PropertyDescriptor(fieldName, clazz);
        }
        for (int index = startRow; index < rows.size(); index++) {
            datas.add(rowToBean(rows.get(index), propertyDescriptors, clazz));
        }
        return datas;
    }

    private static List<Row> loadXls(InputStream is, int sheetIndex) throws IOException {
        List<Row> rowData = new ArrayList<>();

        HSSFWorkbook wb = new HSSFWorkbook(is);
        HSSFSheet hssfSheet = wb.getSheetAt(sheetIndex);
        int rowNum = hssfSheet.getLastRowNum();
        for (int rowIdx = 0; rowIdx <= rowNum; rowIdx++) {
            HSSFRow hssfRow = hssfSheet.getRow(rowIdx);
            if (hssfRow != null) {
                rowData.add(hssfRow);
            }
        }
        wb.close();
        return rowData;
    }

    private static List<Row> loadXlsx(InputStream is, int sheetIndex) throws IOException {
        List<Row> rowData = new ArrayList<>();
        XSSFWorkbook wb = new XSSFWorkbook(is);
        XSSFSheet xssfSheet = wb.getSheetAt(sheetIndex);
        int rowNum = xssfSheet.getLastRowNum();
        for (int rowIdx = 0; rowIdx <= rowNum; rowIdx++) {
            XSSFRow xssfRow = xssfSheet.getRow(rowIdx);
            if (xssfRow != null) {
                rowData.add(xssfRow);
            }
        }
        wb.close();
        return rowData;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            LOGGER.debug("单元格未定义");
            return null;
        }
        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            if (HSSFDateUtil.isCellDateFormatted(cell)) {// 处理日期格式、时间格式
                SimpleDateFormat sdf = null;
                if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                        .getBuiltinFormat("h:mm")) {
                    sdf = new SimpleDateFormat("HH:mm");
                } else {// 日期
                    sdf = new SimpleDateFormat("yyyy-MM-dd");
                }
                Date date = cell.getDateCellValue();
                return sdf.format(date);
            } else if (cell.getCellStyle().getDataFormat() == 58) {
                // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                double value = cell.getNumericCellValue();
                Date date = org.apache.poi.ss.usermodel.DateUtil
                        .getJavaDate(value);
                return sdf.format(date);
            } else {
                DecimalFormat df = new DecimalFormat("#.##");//修正数值格式化,如果后面都为0时舍弃
                return df.format(cell.getNumericCellValue());
            }
        }
        return cell.getStringCellValue();
    }

    private static Object convertValue(Class<?> c, String stringValue) {
        double value = 0.0D;
        try {
            if (stringValue != null) {
                value = Double.parseDouble(stringValue);
            }
            if ((c == Byte.TYPE) || (c == Byte.class))
                return Byte.valueOf(stringValue == null ? 0 : (byte) (int) value);
            if ((c == Short.TYPE) || (c == Short.class))
                return Short.valueOf(stringValue == null ? 0 : (short) (int) value);
            if ((c == Integer.TYPE) || (c == Integer.class))
                return Integer.valueOf(stringValue == null ? 0 : (int) value);
            if ((c == Long.TYPE) || (c == Long.class))
                return Long.valueOf(stringValue == null ? 0L : (long) value);
            if ((c == Float.TYPE) || (c == Float.class))
                return Float.valueOf(stringValue == null ? 0.0F : (float) value);
            if ((c == String.class) || (c == String.class)) {
                return stringValue == null ? "" : stringValue.trim();
            }
            if (c == BigDecimal.class) {
                return stringValue == null ? BigDecimal.ZERO : new BigDecimal(value);
            }
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            if ((c == Boolean.TYPE) || (c == Boolean.class))
                return Boolean.valueOf(stringValue == null ? false : Boolean.parseBoolean(stringValue));
            if (c == String.class)
                return stringValue;
        }
        return null;
    }

    private static <T> T convertToBean(PropertyDescriptor[] propertyDescriptors, String[] values, Class<T> clazz)
            throws InstantiationException, IllegalAccessException, IntrospectionException, IllegalArgumentException,
            InvocationTargetException {

        T t = clazz.newInstance();
        for (int i = 0; i < propertyDescriptors.length; i++) {
            PropertyDescriptor pd = propertyDescriptors[i];
            Method m = pd.getWriteMethod();
            m.invoke(t, new Object[]{convertValue(pd.getPropertyType(), values[i])});
        }
        return t;
    }

    private static <T> T rowToBean(Row r, PropertyDescriptor[] propertyDescriptors, Class<T> clazz) throws Exception {
        if (r == null)
            return null;
        String[] fieldValues = new String[propertyDescriptors.length];
        for (int i = 0; i < propertyDescriptors.length; i++) {
            String cellValue;
            try {
                cellValue = getCellValue(r.getCell(i, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK));
            } catch (Exception e) {
                LOGGER.error("读取{},{}列数据出现异常,请检查", r.getRowNum() + 1, i + 1, e);
                throw new BizException(String.format("读取第%s行,第%s列数据出现异常,请检查", r.getRowNum() + 1, i + 1));
            }
            if (cellValue != null) {//处理下输入的前后空格
                cellValue = cellValue.trim();
            }
            fieldValues[i] = cellValue;
        }
        try {
            return convertToBean(propertyDescriptors, fieldValues, clazz);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | IntrospectionException e) {
            LOGGER.error("创建实例失败", e);
        }
        throw new BizException("创建实例失败，请检查单元格的值！第" + (r.getRowNum() + 1) + "行，单元格的值为：" + Arrays.toString(fieldValues));
    }

}
