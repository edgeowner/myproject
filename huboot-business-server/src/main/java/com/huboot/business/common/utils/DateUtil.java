package com.huboot.business.common.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author zhangtiebin@hn-zhixin.com
 * @ClassName: DateUtil
 * @Description: 时间工具类
 * @date 2015年6月24日 上午10:29:27
 */
public class DateUtil {
    /**
     * Date format pattern used to parse HTTP date headers in RFC 1123 format.
     */
    public static final String PATTERN_RFC1123 = "EEE, dd MMM yyyy HH:mm:ss zzz";

    /**
     * Date format pattern used to parse HTTP date headers in RFC 1036 format.
     */
    public static final String PATTERN_RFC1036 = "EEEE, dd-MMM-yy HH:mm:ss zzz";

    /**
     * Date format pattern used to parse HTTP date headers in ANSI C
     * <code>asctime()</code> format.
     */
    public static final String PATTERN_ASCTIME = "EEE MMM d HH:mm:ss yyyy";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";

    public static final String HH_MM = "HH:mm";

    public static final String MM_DD_HH_MM = "MM-dd HH:mm";

    static {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1, 0, 0);
    }

    private static final TimeZone GMT = TimeZone.getTimeZone("GMT");

    /**
     * 获取当前时间的秒数
     *
     * @return
     */
    public static Long getCurrentSecond() {

        return System.currentTimeMillis() / 1000;
    }

    public static String formatForLocalDateTime(LocalDateTime time,String format){
        DateTimeFormatter df = DateTimeFormatter.ofPattern(format);
        String applyTime = df.format(time);
        return applyTime;
    }

    /**
     * 格式化
     *
     * @return
     */
    public static Date parse(String date) {
        return parse(date,YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * 格式化
     *
     * @param format
     * @return
     */
    public static Date parse(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 格式化
     *
     * @param time
     * @return
     */
    public static String formatTimestamp(Timestamp time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (time == null) {
            return null;
        }

        return sdf.format(time);
    }

    /**
     * 格式化
     *
     * @param date
     * @return
     */
    public static String formatDefaultDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null) {
            return null;
        }

        return sdf.format(date);
    }

    /**
     * 格式化当前时间  返回格式yyMMdd
     *
     * @return
     */
    public static String getCurrentDateYYMMDD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 格式化当前时间  返回格式yyMMdd
     *
     * @return
     */
    public static String getCurrentYMDHM() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    public static String getCurrentFullTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 格式化当前时间  返回格式yyyyMMdd
     *
     * @return
     */
    public static String getCurrentDateYYYYMMDD() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date currentTime = new Date();
        return formatter.format(currentTime);
    }

    /**
     * 格式化
     *
     * @param second
     * @return
     */
    public static String formatSecond(Long second) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (second == null) {
            return null;
        }
        return sdf.format(new Date(second * 1000));
    }

    /**
     * 格式化
     *
     * @param second
     * @return
     */
    public static String formatSecondOne(Long second) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if (second == null) {
            return null;
        }
        return sdf.format(new Date(second * 1000));
    }

    /**
     * 格式化
     *
     * @return
     */
    public static String formatCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 格式化
     *
     * @return
     */
    public static String format(String format, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 格式化 yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatyyyyMMdd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /****
     *
     * 格式化 yyyy-MM-dd. <br/>
     *
     * @param date
     * @return
     * @author Coollf
     * @date 2016年3月30日 下午4:21:46
     */
    public static String formatSimpleyyyyMMdd(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(date);
    }

    /**
     * 取当前日期的年月日部分
     *
     * @return
     */
    public static Date getDatePart(Date date) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
            String str = formatter.format(date);

            return formatter.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 取当前日期的年月日部分
     *
     * @return
     */
    public static Date getCurrentData() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD);
            String str = formatter.format(new Date());
            return formatter.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 取当前日期的年月日部分
     *
     * @return
     */
    public static Date getCurrentTime() {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
            String str = formatter.format(new Date());
            return formatter.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 取当前日期的年月日部分
     *
     * @return
     */
    public static Date getCurrentTime(String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            String str = formatter.format(new Date());
            return formatter.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取时间的小时
     *
     * @return
     */
    public static int getHour(Date date) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        return ca.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * @return
     */
    public static Date getDataWithHour(Date date) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        int hour = ca.get(Calendar.HOUR_OF_DAY);
        Date now = getCurrentData();

        Calendar canow = new GregorianCalendar();
        canow.setTime(now);
        canow.add(Calendar.HOUR_OF_DAY, hour);
        return canow.getTime();
    }


    /**
     * String 转化成 Long
     *
     * @param format
     * @return
     * @throws ParseException
     */
    /*public static Long dateFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.TimeFormatWrong);
        }
        return date.getTime() / 1000;
    }*/

    /**
     * String 转化成 Long
     *
     * @param format
     * @return
     * @throws ParseException
     */
    /*public static Long timeFormat(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = sdf.parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new ApiException(ErrorCodeEnum.TimeFormatWrong);
        }
        return date.getTime() / 1000;
    }*/

    /**
     * Formats the given date according to the RFC 1123 pattern.
     *
     * @param date The date to format.
     * @return An RFC 1123 formatted date string.
     * @see #PATTERN_RFC1123
     */
    public static String formatDate(Date date) {
        return formatDate(date, PATTERN_RFC1123);
    }

    /**
     * Formats the given date according to the specified pattern.  The pattern
     * must conform to that used by the {@link SimpleDateFormat simple date
     * format} class.
     *
     * @param date    The date to format.
     * @param pattern The pattern to use for formatting the date.
     * @return A formatted date string.
     * @throws IllegalArgumentException If the given date pattern is invalid.
     * @see SimpleDateFormat
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) throw new IllegalArgumentException("date is null");
        if (pattern == null) throw new IllegalArgumentException("pattern is null");

        SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.US);
        formatter.setTimeZone(GMT);
        return formatter.format(date);
    }

    /**
     * 格式化
     *
     * @return
     */
    public static int getDateOfMin(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.MINUTE);
    }

    public static void main(String[] args) {
        Date endDate = DateUtil.parse("2018-10-10 10:10:10",DateUtil.YYYY_MM_DD_HH_MM_SS);
        int min = DateUtil.getDateOfMin(endDate);
        System.out.println(min);
    }

    /**
     * 时间添加天数
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getDateAddDay(Date date, int day) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.DAY_OF_YEAR, day);
        return ca.getTime();
    }


    /**
     * 时间添加小时
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date getDateAddHour(Date date, int hour) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, hour);
        return ca.getTime();
    }

    /**
     * 时间添加分钟
     *
     * @param date
     * @param min
     * @return
     */
    public static Date getDateAddMin(Date date, int min) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        ca.add(Calendar.MINUTE, min);
        return ca.getTime();
    }

    /**
     * 比较时间大小
     *
     * @param date
     * @param date2
     * @return
     */
    public static int compareDate(Date date, Date date2) {
        if (date.getTime() > date2.getTime()) {
            return 1;
        } else if (date.getTime() < date2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    /**
     * 设置当前月份为某一天
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getDateThisMonthOfDay(Date date, int day) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH);
        ca.set(year, month, day);//设置日期，此时的日期是2013年11月30号

        return ca.getTime();
    }


    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate){
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));*/
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }



    /**
     * 计算两个日期之间相差的小时
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int hoursBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的分钟
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int minutesBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 60);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    /*public static int daysBetweenWithHour(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        long between_hours = (time2 - time1) % (1000 * 3600 * 24);
        if (between_hours > 0) {
            between_days = between_days + 1;
        }
        return Integer.parseInt(String.valueOf(between_days));
    }*/

    /**
     * 计算两个日期之间相差的月份
     *
     * @param start
     * @param end
     * @return
     */
    public static int getMonthBetween(Date start, Date end) {
        if (start.after(end)) {
            Date t = start;
            start = end;
            end = t;
        }
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        Calendar temp = Calendar.getInstance();
        temp.setTime(end);
        temp.add(Calendar.DATE, 1);

        int year = endCalendar.get(Calendar.YEAR)
                - startCalendar.get(Calendar.YEAR);
        int month = endCalendar.get(Calendar.MONTH)
                - startCalendar.get(Calendar.MONTH);

        if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month + 1;
        } else if ((startCalendar.get(Calendar.DATE) != 1)
                && (temp.get(Calendar.DATE) == 1)) {
            return year * 12 + month;
        } else if ((startCalendar.get(Calendar.DATE) == 1)
                && (temp.get(Calendar.DATE) != 1)) {
            return year * 12 + month;
        } else {
            return (year * 12 + month - 1) < 0 ? 0 : (year * 12 + month);
        }
    }

    /**
     * 计算两个日期之间相差的年数
     *
     * @param start 较小的时间
     * @param end   较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int yearBetween(Date start, Date end) {
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);
        int startYear = startCalendar.get(Calendar.YEAR);
        int endYear = endCalendar.get(Calendar.YEAR);
        return endYear - startYear;
    }

    /**
     * @param @param  dt
     * @param @param  strFormat
     * @param @return
     * @return String
     * @throws
     * @Title: dateToString
     * @Description: Date按指定格式转换为字符串
     */
    public static String dateToString(Date dt, String strFormat) {
        SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
        String str = "";
        try {
            str = sdFormat.format(dt);
        } catch (Exception e) {
            return "";
        }
        if (str.equals("1900-01-01 00:00")) {
            str = "";
        }

        return str;
    }

    //获得当天0点时间
    public static int getTimesMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    //获得当天24点时间
    public static Date getTimesNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 24);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获得本周一0点时间
    public static int getTimesWeekMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    //获得本周日24点时间
    public static int getTimesWeekNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return (int) ((cal.getTime().getTime() + (7 * 24 * 60 * 60 * 1000)) / 1000);
    }

    //获得本月第一天0点时间
    public static int getTimesMonthMorning() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
        return (int) (cal.getTimeInMillis() / 1000);
    }

    //获得本月最后一天24点时间
    public static int getTimesMonthNight() {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 24);
        return (int) (cal.getTimeInMillis() / 1000);
    }

    //将给定时间转换为当日0点0时0秒
    public static String parseToMinOfDaySecond(Date date){
        String s = format(YYYY_MM_DD,date);
        return parseToMinOfDaySecond(s);
    }

    //将给定时间转换为当日0点0时0秒
    public static String parseToMinOfDaySecond(String dateStr){

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(dateStr);
            cal.setTime(date);
        } catch (ParseException e) {
            return format(DateUtil.YYYY_MM_DD_HH_MM_SS,new Date(getTimesMorning()));
        }
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.HOUR_OF_DAY,0);
        String d = formatDefaultDate(cal.getTime());
        return d;

    }

    //将给定时间转换为当日23点59时59秒
    public static String parseToMaxOfDaySecond(String dateStr){

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(dateStr);
            cal.setTime(date);
        } catch (ParseException e) {
            return format(DateUtil.YYYY_MM_DD_HH_MM_SS,getTimesNight());
        }
        cal.set(Calendar.SECOND,59);
        cal.set(Calendar.MINUTE,59);
        cal.set(Calendar.HOUR_OF_DAY,23);
        String d = formatDefaultDate(cal.getTime());
        return d;

    }

    //将给定时间转换为当日23点59时59秒
    public static String parseToMaxOfDaySecond(Date date){
        String s = format(YYYY_MM_DD,date);
        return parseToMaxOfDaySecond(s);
    }

    public static String getToday(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String today = simpleDateFormat.format(calendar.getTime());
        return today;
    }

    // -------------------------- 时间操作 -----------------------
    public static List<String> getMonthBetween(String minDate, String maxDate) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");//格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }
        return result;
    }

    public static List<String> getDayBetween(String dBegin, String dEnd) {
        List<String> lDate = new ArrayList();
        lDate.add(dBegin.substring(0, 10));
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(DateUtil.parse(dBegin, DateUtil.YYYY_MM_DD));
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(DateUtil.parse(dEnd, DateUtil.YYYY_MM_DD));
        // 测试此日期是否在指定日期之后
        while (DateUtil.parse(dEnd, DateUtil.YYYY_MM_DD).after(calBegin.getTime())) {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(DateUtil.format(DateUtil.YYYY_MM_DD, calBegin.getTime()));
        }
        return lDate;
    }

    public static String getLastDayOfMonthEnd(int year, int month) {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR, year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(cal.getTime());
    }


    /*public static void main(String[] args) {

        System.out.print(DateUtil.format("MM-dd HH:mm",DateUtil.parse("2018-01-12 11:21:18",DateUtil.YYYY_MM_DD_HH_MM_SS)));


    }*/

}
