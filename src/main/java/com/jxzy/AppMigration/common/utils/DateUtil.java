package com.jxzy.AppMigration.common.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: DateUtil
 * @Description: 日期操作工具类
 * @author Mars
 * @date 2017年3月10日 下午12:15:35
 *
 */
@SuppressWarnings("unchecked")
public class DateUtil {

    public static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    /**
     * 静态常量
     */
    public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";
    public static final String C_DATE_PATTON_DEFAULT_NOMIDLINE = "yyyyMMdd";
    public static final String C_DATE_PATTON_YEAR_MONTH = "yyyyMM";
    public static final String C_DATE_PATTON_MONTH_DAY = "MM/dd";

    public static final int C_ONE_SECOND = 1000;
    public static final int C_ONE_MINUTE = 60 * C_ONE_SECOND;
    public static final long C_ONE_HOUR = 60 * C_ONE_MINUTE;
    public static final long C_ONE_DAY = 24 * C_ONE_HOUR;

    public static final String DAYBEGIN = "00:00:00";
    public static final String DAYEND = "23:59:59";

    /**
     * 按日进行查询
     */
    public static final String SEARCH_TYPE_DAY = "1";

    /**
     * 按周进行查询
     */
    public static final String SEARCH_TYPE_WEEK = "2";

    /**
     * 按月进行查询
     */
    public static final String SEARCH_TYPE_MONTH = "3";

    /**
     * 月初1号
     */
    public static final int MONTH_BEGIN = 1;
    public static final int MONTH_MIDDLE = 15;
    protected static final Log log = LogFactory.getLog(DateUtil.class);

    /**
     * 返回字符串 yyyy-MM-dd
     * @return
     */
    public static String crutDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(crutDateTime());
    }

    public static String crutDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }
    /**
     * 返回时间日期数据(格式:yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static String currentDateTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(crutDateTime());
    }

    /**
     * 获取当前时间分钟
     * @Title: getRobotUnlockDateTime
     * @date:   2019年7月11日 下午1:05:13
     * @author: 曲绍备
     * @param: @param dateTime
     * @param: @return
     * @param: @throws ParseException
     * @return: long
     * @throws
     */
    public static long getRobotUnlockDateTime(String dateTime) throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(System.currentTimeMillis());
        Date parse = sdf.parse(dateTime);
        long time1 = (d.getTime() - parse.getTime());
        long maining = time1 / 1000 / 60;
        System.out.println("当前时间："+maining);
        return maining;
    }

    /**
     * 根据毫秒值来判断是否大于多少秒
     * @Title: millisecond
     * @date:   2019年7月11日 下午4:10:29
     * @author: 曲绍备
     * @param: @param dateTime 数据库时间
     * @param: @param setMillisecond 手动设置的秒数  50000=50秒
     * @param: @return
     * @param: @throws Exception
     * @return: boolean
     * @throws
     */
    public static boolean millisecond(String dateTime,long setMillisecond) throws Exception{
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String oldTime = "2019-07-11 14:02:00";
        Date old = formatter.parse(oldTime);
        boolean result = System.currentTimeMillis() - old.getTime()>setMillisecond;
        return result;
    }


    /**
     * 返回当前日期的开始日期（格式:yyyy-MM-dd 00:00:00）
     * @return
     */
    public static String currentDateBegainTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ymd = formatter.format(crutDateTime()).trim().substring(0, 10);
        String ymdhms = ymd + " " + DAYBEGIN;
        return ymdhms;
    }
    /**
     * 返回当前日期的结束日期（格式:yyyy-MM-dd 23:59:59）
     * @return
     */
    public static String currentDateEndTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String ymd = formatter.format(crutDateTime()).trim().substring(0, 10);
        String ymdhms = ymd + " " + DAYEND;
        return ymdhms;
    }




    public static String getliangnianTime(){
        Format f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        System.out.println("之前:" + f.format(c.getTime()));
        c.add(Calendar.YEAR, -2);
        System.out.println("之后:" + f.format(c.getTime()));
        return f.format(c.getTime());
    }

    /**
     * 返回时间日期数据(格式:yyyy-MM-dd HH:mm:ss)
     * @return
     */
    public static Date crutDateTime(){
        Date curtDate = new Date(System.currentTimeMillis());
        return curtDate;
    }

    /**
     * 失效时间
     * @param dd
     * @return
     * @throws ParseException
     */
    public static Date getExpiresDate(Long dd) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE,(int)(dd/60/60/24));
        Date date = calendar.getTime();
        return date;
    }

    /**
     *
     * @param day
     * @return
     * @throws ParseException
     */
    public static Date addDate(long day) throws ParseException {
        long time = crutDateTime().getTime();
        day = day * 24 * 60 * 60 * 1000;
        time += day;
        return new Date(time);
    }

    /**
     * 失效时间
     * @return Date
     */
    public static Date getExpiresDateYear() {
        String date = "2037-12-31 23:59:59";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = null;
        try {
            bdate = formatter.parse(date);
        } catch (ParseException e) {
            log.info(e.getMessage(), e);
        }
        return bdate;
    }


    /**
     * 失效时间
     * @param expiresDate
     * @param dd
     * @return
     */
    public static Date getExpiresDate(Date expiresDate,Long dd){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expiresDate);
        calendar.add(Calendar.DATE,-(int)(dd/60/60/24));
        Date date = calendar.getTime();
        return date;
    }

    public static long getTrialTime(Date tryEndDate){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1;
        Date d2;
        long days = 0;
        try {
            d1 = df.parse(df.format(tryEndDate));
            d2 = df.parse(currentDateTime());
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            log.info(e.getMessage(), e);
        }
        return days+1;
    }
    /**
     * 系统当前时间大于失效时间
     * @param expiresDate
     * @return
     */
    public static boolean sysTemMaxExpiresDate(Date expiresDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = formatter.format(crutDateTime());
        String expiresDateStr = formatter.format(expiresDate);
        int result = now.compareTo(expiresDateStr);
        if (result > 0) {
            return false;
        } else if (result == 0) {
            return false;
        } else {
            return true;
        }
    }
    /**
     *
     * @param ActivityEndDate 活动结束时间
     * @param dd   活动天数
     * @return 活动实效时间
     * @throws ParseException
     */
    public static Date returnActivityEndDate(Date ActivityEndDate, int dd) throws ParseException {
        if(compareTwoDate(crutDateTime(), ActivityEndDate)){
            int difference = caculate2Days(crutDateTime(),ActivityEndDate);
            if(difference > dd){
                return addDate(difference);
            }else{
                return addDate(difference - dd);
            }
        }
        return crutDateTime();
    }

    /**
     * 根据给出的时间区间得到所有的周
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static Map<String, String> getWeekMap(String startDate,String endDate)
            throws ParseException{
        Map<String, String> map = new LinkedHashMap<String, String>();
        String weekStart = null;
        String weekEnd = null;
        Date startDateTime = StringToDateX(startDate);
        Date endDateTime = StringToDateX(endDate);
        Calendar cal = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        cal.setTime(startDateTime);
        calEnd.setTime(endDateTime);

        //判断两个时间是否在同一周
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        int weekOfyear = cal.get(Calendar.WEEK_OF_YEAR);
        int weekOfyearEnd = calEnd.get(Calendar.WEEK_OF_YEAR);
        if(weekOfyear == weekOfyearEnd){
            map.put(DateYToString(cal.getTime())+(weekOfyear<10?"0"+weekOfyear:""+weekOfyear),
                    startDate+"-"+endDate);
            return map;
        }

        //如果不在同一周说明跨周了
        switch (dayOfWeek) {
            case 1:
                weekStart = DateYMDToStringX(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, -6);
                break;
            case 2:
                weekStart = DateYMDToStringX(cal.getTime());
                break;
            default:
                weekStart = DateYMDToStringX(cal.getTime());
                cal.add(Calendar.DAY_OF_YEAR, 2-dayOfWeek);
                break;
        }
        weekOfyear = cal.get(Calendar.WEEK_OF_YEAR);
        startDateTime = cal.getTime();
        cal.add(Calendar.DAY_OF_YEAR, 6);
        weekEnd = DateYMDToStringX(cal.getTime());
        map.put(DateYToString(cal.getTime())+(weekOfyear<10?"0"+weekOfyear:""+weekOfyear),
                weekStart+"-"+weekEnd);
        //如果加1周还小于endDate那么就一直加
        while(cal.getTime().before(endDateTime)){
            cal.add(Calendar.DAY_OF_YEAR, 1);
            weekOfyear = cal.get(Calendar.WEEK_OF_YEAR);
            weekStart = DateYMDToStringX(cal.getTime());
            cal.add(Calendar.DAY_OF_YEAR, 6);
            if(cal.getTime().after(endDateTime)){
                map.put(DateYToString(cal.getTime())+(weekOfyear<10?"0"+weekOfyear:""+weekOfyear),
                        weekStart+"-"+endDate);
                break;
            }
            weekEnd = DateYMDToStringX(cal.getTime());
            map.put(DateYToString(cal.getTime())+(weekOfyear<10?"0"+weekOfyear:""+weekOfyear),
                    weekStart+"-"+weekEnd);
        }
        return map;
    }
    /**
     * 根据给出的时间区间得到所有的月
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    @SuppressWarnings("deprecation")
    public static Map<String, String> getMonthMap(String startDate,String endDate)
            throws ParseException{
        Map<String, String> map = new LinkedHashMap<String, String>();
        Date startDateTime = StringToDateX(startDate);
        Date endDateTime = StringToDateX(endDate);
        Calendar cal = Calendar.getInstance();
        Calendar calEnd = Calendar.getInstance();
        cal.setTime(startDateTime);
        calEnd.setTime(endDateTime);
        int monthOfYear = cal.get(Calendar.MONTH);
        int monthOfYearEnd = calEnd.get(Calendar.MONTH);
        //同一月但不同年
        if((monthOfYear == monthOfYearEnd)&&(cal.get(Calendar.YEAR)==calEnd.get(Calendar.YEAR))){
            map.put(DateYToString(cal.getTime())+((monthOfYear+1)<10?"0"+(monthOfYear+1):""+(monthOfYear+1)),
                    startDate+"-"+endDate);
            return map;
        }
        while(cal.getTime().before(endDateTime)){
            String monthStartDay = DateYMDToStringX(cal.getTime());
            monthOfYear = cal.get(Calendar.MONTH);
            if((monthOfYear == monthOfYearEnd)&&
                    (cal.get(Calendar.YEAR)==calEnd.get(Calendar.YEAR))){
                monthStartDay = DateYMDToStringX(cal.getTime());
                map.put(DateYToString(cal.getTime())+((monthOfYear+1)<10?"0"+(monthOfYear+1):""+(monthOfYear+1)),
                        monthStartDay+"-"+endDate);
                break;
            }
            int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            Date lastDate = cal.getTime();
            lastDate.setDate(lastDay);
            String monthLastDay = DateYMDToStringX(lastDate);
            map.put(DateYToString(cal.getTime())+((monthOfYear+1)<10?"0"+(monthOfYear+1):""+(monthOfYear+1)),
                    monthStartDay+"-"+monthLastDay);
            cal.setTime(lastDate);
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        return map;
    }

    /**
     * 根据给出的时间区间得到所有的日期
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    public static Map<String, String> getDayMap(String startDate,String endDate)
            throws ParseException{
        Map<String, String> map = new LinkedHashMap<String, String>();
        SimpleDateFormat format=new SimpleDateFormat("yyyy/MM/dd");
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        start.setTime(format.parse(startDate));
        end.setTime(format.parse(endDate));
        while(start.before(end)||start.equals(end))
        {
            map.put(dateToString(start.getTime(),"yyyy-MM-dd"), format.format(start.getTime()));
            start.add(Calendar.DAY_OF_MONTH,1);
        }
        return map;
    }
    /**
     * 根据给出的时间区间和日期类型得到日区间周区间或者月区间
     * @param startDate
     * @param endDate
     * @param dateType 1日区间，2周区间，3月区间
     * @return
     * @throws ParseException
     */
    public static Map<String, String> getDateMap(String startDate,String endDate,String dateType) throws ParseException{
        Map<String, String> map = new LinkedHashMap<String, String>();
        if (dateType.equals("1")) {
            map= getDayMap(startDate,endDate);
        }else if (dateType.equals("2")) {
            map= getWeekMap(startDate,endDate);
        }else if (dateType.equals("3")) {
            map= getMonthMap(startDate,endDate);
        }
        return map;
    }

    /**
     * 将String类型的日期时间转换成java.util.Date类型的数据
     * @param dateTime
     * @return
     * @throws ParseException
     */
    public static Date StringToDateTime(String dateTime) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date bdate = formatter.parse(dateTime);
        return bdate;
    }

    /**
     *
     * @param dataTime
     * @return
     * @throws ParseException
     */
    public static String StringToHhMmSs(String dataTime) throws ParseException{
        //SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        //Date bdate = formatter.parse(dataTime);
        SimpleDateFormat form = new SimpleDateFormat("HH:mm:ss");
        return  form.format(dataTime);
    }

    /**
     * 将String类型的日期时间转换成java.util.Date类型的数据
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date StringToDate(String date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date bdate = formatter.parse(date);
        return bdate;
    }

    /**
     * 将String类型的日期时间转换成java.util.Date类型的数据
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date StringToDateX(String date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        Date bdate = formatter.parse(date);
        return bdate;
    }


    /**
     * 将java.util.Date类型的日期转换成String类型数据
     * @param date
     * @return
     * @throws ParseException
     */
    public static String DateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return  formatter.format(date);
    }

    public static String DateYMDToString(Date date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return  date != null ? formatter.format(date):"";
    }
    public static String DateYMDToStringX(Date date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return  date != null ? formatter.format(date):"";
    }
    public static String DateYToString(Date date) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return  date != null ? formatter.format(date):"";
    }

    public static java.util.Date convertUtilDateTime(String arg) {
        java.util.Date sqldate = null;
        try {
            if (arg != null && !arg.equals("")) {
                if (arg.trim().length() == 10)
                    arg = arg + " 00:00:00";
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                java.util.Date utildate = dateFormat.parse(arg);
                sqldate = new java.util.Date(utildate.getTime());
            }
        } catch (Exception e) {
            log.info("convertUtilDateTime:" + e);
        }
        return sqldate;
    }

    public static java.sql.Date convertSqlDate(String arg) {
        java.sql.Date sqldate = null;
        try {
            if (arg != null && !arg.equals("")) {

                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                java.util.Date utildate = dateFormat.parse(arg);
                sqldate = new java.sql.Date(utildate.getTime());
            }

        } catch (Exception e) {
            log.info("convertToSqlDate:" + e);
        }
        return sqldate;
    }

    /*
     * 名字：stringToDate（String str,String formatStyle） 功能：字符转换为Date 返回：Date
     */
    public static String dateToString(Date date, String formatStyle) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(formatStyle);
            return formatter.format(date);
        } catch (Exception e) {
            return date.toString();
        }
    }
    /**
     * 计算当前月份的最大天数
     *
     * @return
     */
    public static int findMaxDayInMonth() {
        return findMaxDayInMonth(0, 0);
    }

    /**
     * 计算指定日期月份的最大天数
     *
     * @param date
     * @return
     */
    public static int findMaxDayInMonth(Date date) {
        if (date == null) {
            return 0;
        }
        return findMaxDayInMonth(date2Calendar(date));
    }

    /**
     * 计算指定日历月份的最大天数
     *
     * @param calendar
     * @return
     */
    public static int findMaxDayInMonth(Calendar calendar) {
        if (calendar == null) {
            return 0;
        }
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 计算当前年某月份的最大天数
     *
     * @param month
     * @return
     */
    public static int findMaxDayInMonth(int month) {
        return findMaxDayInMonth(0, month);
    }

    /**
     * 计算某年某月份的最大天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int findMaxDayInMonth(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        if (year > 0) {
            calendar.set(Calendar.YEAR, year);
        }
        if (month > 0) {
            calendar.set(Calendar.MONTH, month - 1);
        }
        return findMaxDayInMonth(calendar);
    }

    /**
     * Calendar 转换为 Date
     *
     * @param calendar
     * @return
     */
    public static Date calendar2Date(Calendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.getTime();
    }

    /**
     * Date 转换为 Calendar
     *
     * @param date
     * @return
     */
    public static Calendar date2Calendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    /**
     * Date 转换为 Calendar
     *
     * @param date
     * @return
     */
    public static Date date2CalendarTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTime();
    }

    /**
     * 拿到默认格式的SimpleDateFormat
     *
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat() {
        return getSimpleDateFormat(null);
    }

    /**
     * 拿到指定输出格式的SimpleDateFormat
     *
     * @param format
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat(String format) {
        SimpleDateFormat sdf;
        if (format == null) {
            sdf = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
        } else {
            sdf = new SimpleDateFormat(format);
        }
        return sdf;
    }

    /**
     * 转换当前时间为默认格式
     *
     * @return
     */
    public static String formatDate2Str() {
        return formatDate2Str(new Date());
    }

    /**
     * 转换指定时间为默认格式
     *
     * @param date
     * @return
     */
    public static String formatDate2Str(Date date) {
        return formatDate2Str(date, C_TIME_PATTON_DEFAULT);
    }

    /**
     * 转换指定时间为指定格式
     *
     * @param date
     * @param format
     * @return
     */
    public static String formatDate2Str(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (format == null || format.equals("")) {
            format = C_TIME_PATTON_DEFAULT;
        }
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 转换日期类型为指定格式的日期类型
     *
     * @param date
     * @param format
     * @return
     */
    public static Date formatDate2Date(Date date, String format) {
        String str = formatDate2Str(date, format);
        return formatStr2Date(str, format);
    }

    /**
     * 转换默认格式的时间为Date
     *
     * @param dateStr
     * @return
     */
    public static Date formatStr2Date(String dateStr) {
        return formatStr2Date(dateStr, null);
    }

    /**
     * @param dateStr
     * @return
     * @描述 参数格式YYYY-MM-DD,返回yyyy-mm-dd hh:mm:ss
     */
    public static Date formatStr2Date2(String dateStr) {
        return formatStr2Date2(dateStr, null);
    }

    /**
     * 转换指定格式的时间为Date
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date formatStr2Date(String dateStr, String format) {
        if (dateStr == null) {
            return null;
        }
        if (format == null || format.equals("")) {
            format = C_TIME_PATTON_DEFAULT;
        }
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        return sdf.parse(dateStr, new ParsePosition(0));
    }

    public static Date formatStr2Date2(String dateStr, String format) {
        Date date = null;
        if (dateStr == null) {
            return null;
        }
        if (format == null || format.equals("")) {
            format = C_TIME_PATTON_DEFAULT;
        }
        SimpleDateFormat sdf = getSimpleDateFormat(format);

        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            format = C_DATE_PATTON_DEFAULT;
            sdf = getSimpleDateFormat(format);
            try {
                date = sdf.parse(dateStr);
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }

        return date;
    }

    /**
     * 转换默认格式的时间为指定格式时间
     *
     * @param dateStr
     * @param defineFormat
     * @return
     */
    public static String formatDefault2Define(String dateStr,
                                              String defineFormat) {
        return formatSource2Target(dateStr, C_TIME_PATTON_DEFAULT, defineFormat);
    }

    /**
     * 转换源格式的时间为目标格式时间
     *
     * @param dateStr
     * @param sourceFormat
     * @param targetFormat
     * @return
     */
    public static String formatSource2Target(String dateStr,
                                             String sourceFormat, String targetFormat) {
        Date date = formatStr2Date(dateStr, sourceFormat);
        return formatDate2Str(date, targetFormat);
    }

    /**
     * 计算当天是该年中的第几周
     *
     * @return
     */
    public static int findWeeksNoInYear() {
        return findWeeksNoInYear(new Date());
    }

    /**
     * 计算指定日期是该年中的第几周
     *
     * @param date
     * @return
     */
    public static int findWeeksNoInYear(Date date) {
        if (date == null) {
            return 0;
        }
        return findWeeksNoInYear(date2Calendar(date));
    }

    /**
     * 计算指定日历是该年中的第几周
     *
     * @param calendar
     * @return
     */
    public static int findWeeksNoInYear(Calendar calendar) {
        if (calendar == null) {
            return 0;
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 计算一年中的第几星期是几号
     *
     * @param year
     * @param weekInYear
     * @param dayInWeek
     * @return
     */
    public static Date findDateInWeekOnYear(int year, int weekInYear,
                                            int dayInWeek) {
        Calendar calendar = Calendar.getInstance();
        if (year > 0) {
            calendar.set(Calendar.YEAR, year);
        }
        calendar.set(Calendar.WEEK_OF_YEAR, weekInYear);
        calendar.set(Calendar.DAY_OF_WEEK, dayInWeek);
        return calendar.getTime();
    }

    /**
     * 计算指定星期几是月多少号
     *
     * @param dayOfWeek
     * @return
     */
    public static int findDayByDayOfWeek(int dayOfWeek) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 相对于当前日期的前几天(days < 0)或者后几天(days > 0)
     *
     * @param days
     * @return
     */
    public static Date dayBefore2Date(int days) {
        Date date = new Date();
        return dayBefore2Object(days, null, date);
    }

    /**
     * 相对于当前日期的前几天(days < 0)或者后几天(days > 0)
     *
     * @param days
     * @return
     */
    public static String dayBefore2Str(int days) {
        String string = formatDate2Str();
        return dayBefore2Object(days, null, string);
    }

    /**
     * 相对于当前日期的前几天(days < 0)或者后几天(days > 0)
     *
     * @param days
     * @param format
     * @param instance
     */
    public static <T extends Object> T dayBefore2Object(int days,
                                                        String format, T instance) {
        Calendar calendar = Calendar.getInstance();
        if (days == 0) {
            return null;
        }
        if (format == null || format.equals("")) {
            format = C_TIME_PATTON_DEFAULT;
        }
        calendar.add(Calendar.DATE, days);
        if (instance instanceof Date) {
            return (T) calendar.getTime();
        } else if (instance instanceof String) {
            return (T) formatDate2Str(calendar2Date(calendar), format);
        }
        return null;
    }

    /**
     * 相对于指定日期的前几天(days < 0)或者后几天(days > 0)
     *
     * @param date
     * @param days
     * @return
     */
    public static Date defineDayBefore2Date(Date date, int days) {
        Date dateInstance = new Date();
        return defineDayBefore2Object(date, days, null, dateInstance);
    }

    /**
     * 相对于指定日期的前几天(days < 0)或者后几天(days > 0)
     *
     * @param date
     * @param days
     * @return
     */
    public static String defineDayBefore2Str(Date date, int days) {
        String stringInstance = formatDate2Str();
        return defineDayBefore2Object(date, days, null, stringInstance);
    }

    /**
     * 相对于指定日期的前几天(days < 0)或者后几天(days > 0)
     *
     * @param <T>
     * @param date
     * @param days
     * @param format
     * @param instance
     * @return
     */
    public static <T extends Object> T defineDayBefore2Object(Date date,
                                                              int days, String format, T instance) {
        if (date == null) {
            return null;
        }
        if (format == null || format.equals("")) {
            format = C_TIME_PATTON_DEFAULT;
        }
        Calendar calendar = date2Calendar(date);
        calendar.add(Calendar.DATE, days);
        if (instance instanceof Date) {
            return (T) calendar.getTime();
        } else if (instance instanceof String) {
            return (T) formatDate2Str(calendar2Date(calendar), format);
        }
        return null;
    }

    /**
     * 相对于当前日期的前几月(months < 0)或者后几月(months > 0)时间
     *
     * @param months
     * @return
     */
    public static Date monthBefore2Date(int months) {
        Date date = new Date();
        return monthBefore2Object(months, null, date);
    }

    /**
     * 相对于当前日期的前几月(months < 0)或者后几月(months > 0)时间
     *
     * @param months
     * @return
     */
    public static String monthBefore2Str(int months) {
        String string = formatDate2Str();
        return monthBefore2Object(months, null, string);
    }

    /**
     * 相对于当前日期的前几月(months < 0)或者后几月(months > 0)时间
     *
     * @param <T>
     * @param months
     * @param format
     * @param instance
     */
    public static <T extends Object> T monthBefore2Object(int months,
                                                          String format, T instance) {
        if (months == 0) {
            return null;
        }
        if (format == null || format.equals("")) {
            format = C_TIME_PATTON_DEFAULT;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, months);
        if (instance instanceof Date) {
            return (T) calendar.getTime();
        } else if (instance instanceof String) {
            return (T) formatDate2Str(calendar2Date(calendar), format);
        }
        return null;
    }

    /**
     * 相对于指定日期的前几月(months < 0)或者后几月(months > 0)时间
     *
     * @param date
     * @param months
     * @return
     */
    public static Date defineMonthBefore2Date(Date date, int months) {
        Date dateInstance = new Date();
        return defineMonthBefore2Object(date, months, null, dateInstance);
    }

    /**
     * 相对于指定日期的前几月(months < 0)或者后几月(months > 0)时间
     *
     * @param date
     * @param months
     * @return
     */
    public static String defineMonthBefore2Str(Date date, int months) {
        String stringInstance = formatDate2Str();
        return defineMonthBefore2Object(date, months, null, stringInstance);
    }

    /**
     * 相对于指定日期的前几月(months < 0)或者后几月(months > 0)时间
     *
     * @param <T>
     * @param date
     * @param months
     * @param format
     * @param instance
     * @return
     */
    public static <T extends Object> T defineMonthBefore2Object(Date date,
                                                                int months, String format, T instance) {
        if (months == 0) {
            return null;
        }
        if (format == null || format.equals("")) {
            format = C_TIME_PATTON_DEFAULT;
        }
        Calendar calendar = date2Calendar(date);
        calendar.add(Calendar.MONTH, months);
        if (instance instanceof Date) {
            return (T) calendar.getTime();
        } else if (instance instanceof String) {
            return (T) formatDate2Str(calendar2Date(calendar), format);
        }
        return null;
    }

    /**
     * 计算两个日期直接差的天数
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static int caculate2Days(Date firstDate, Date secondDate) {

        /** modify by zyw 去除时分秒 begin **/
        firstDate = DateUtil.formatDate2Date(firstDate, "yyyy-MM-dd");
        secondDate = DateUtil.formatDate2Date(secondDate, "yyyy-MM-dd");
        /** modify by zyw end **/

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDate);
        long time1 = calendar.getTimeInMillis();
        calendar.setTime(secondDate);
        long time2 = calendar.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Math.abs(Integer.parseInt(String.valueOf(between_days)));
    }

    /**
     * 计算两个日期直接差的天数(前边小于 后边的日期 返回 负数 ) sjl
     *
     * @param firstDate
     * @param secondDate
     * @return
     */
    public static int between2Days(Date firstDate, Date secondDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(firstDate);
        long time1 = calendar.getTimeInMillis();
        calendar.setTime(secondDate);
        long time2 = calendar.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期直接差的天数
     *
     * @param firstCalendar
     * @param secondCalendar
     * @return
     */
    public static int caculate2Days(Calendar firstCalendar,
                                    Calendar secondCalendar) {
        if (firstCalendar.after(secondCalendar)) {
            Calendar calendar = firstCalendar;
            firstCalendar = secondCalendar;
            secondCalendar = calendar;
        }
        long calendarNum1 = firstCalendar.getTimeInMillis();
        long calendarNum2 = secondCalendar.getTimeInMillis();
        return Math.abs((int) ((calendarNum1 - calendarNum2) / C_ONE_DAY));
    }


    /**
     * 将日期转换成一天的开始日期时间
     *
     * @param date
     * @return
     */
    public static Date date2startDate(Date date) {
        String beginStr = formatDate2Str(date, C_DATE_PATTON_DEFAULT);
        String dayBeginStr = beginStr + " " + DAYBEGIN;
        return formatStr2Date(dayBeginStr);
    }

    /**
     * 将日期转换成一天的结束日期时间
     *
     * @param date
     * @return
     */
    public static Date date2endDate(Date date) {
        String endStr = formatDate2Str(date, C_DATE_PATTON_DEFAULT);
        String dayEndStr = endStr + " " + DAYEND;
        return formatStr2Date(dayEndStr);
    }

    /**
     * 计算两个日期相差月份
     *
     * @param sdate1
     * @param sdate2
     * @return
     * @throws ParseException
     */
    public static int getMonthSpace(String sdate1, String sdate2)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(sdate1);
        Date date2 = sdf.parse(sdate2);
        if (date1.after(date2)) {
            return 1;
        }
        int result = 0;
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(date1);
        c2.setTime(date2);
        result = c2.get(Calendar.MONDAY) - c1.get(Calendar.MONTH) + 12
                * (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR));
        return result;
    }

    /**
     * 计算两个日期相差月份
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getMonths(Date date1, Date date2) {
        int iMonth = 0;
        int flag = 0;
        try {
            Calendar objCalendarDate1 = Calendar.getInstance();
            objCalendarDate1.setTime(date1);

            Calendar objCalendarDate2 = Calendar.getInstance();
            objCalendarDate2.setTime(date2);

            if (objCalendarDate2.equals(objCalendarDate1))
                return 0;
            if (objCalendarDate1.after(objCalendarDate2)) {
                Calendar temp = objCalendarDate1;
                objCalendarDate1 = objCalendarDate2;
                objCalendarDate2 = temp;
            }
            if (objCalendarDate2.get(Calendar.DAY_OF_MONTH) < objCalendarDate1
                    .get(Calendar.DAY_OF_MONTH))
                flag = 1;

            if (objCalendarDate2.get(Calendar.YEAR) > objCalendarDate1
                    .get(Calendar.YEAR))
                iMonth = ((objCalendarDate2.get(Calendar.YEAR) - objCalendarDate1
                        .get(Calendar.YEAR))
                        * 12 + objCalendarDate2.get(Calendar.MONTH) - flag)
                        - objCalendarDate1.get(Calendar.MONTH);
            else
                iMonth = objCalendarDate2.get(Calendar.MONTH)
                        - objCalendarDate1.get(Calendar.MONTH) - flag;

        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return iMonth;
    }

    /**
     * 日期转换为时间戳
     *
     * @return
     * @throws ParseException
     */
    public static long getTimestamp(String date) throws ParseException {
        Date date1 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(date);
        Date date2 = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                .parse("1970/01/01 08:00:00");
        @SuppressWarnings("unused")
        long l = date1.getTime() - date2.getTime() > 0 ? date1.getTime()
                - date2.getTime() : date2.getTime() - date1.getTime();
        long rand = (int) (Math.random() * 1000);
        return rand;
    }

    /**
     * 时间戳转换为date 型
     *
     * @param timestamp
     *        时间戳
     */
    public static Date getDate(double timestamp) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            return format.parse(format.format(timestamp));
        } catch (Exception e) {
            log.info(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 获取当前月份
     *
     * @return 当前月份
     */
    public static int getCurrentMonth() {
        Calendar c = Calendar.getInstance();
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前月份1号是周几
     *
     * @return
     */
    public static int getCurrentMonthDayOne() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        if (Calendar.SUNDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return 7;
        } else if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return 6;
        } else if (Calendar.SATURDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return 5;
        } else if (Calendar.FRIDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return 4;
        } else if (Calendar.WEDNESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return 3;
        } else if (Calendar.TUESDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return 2;
        } else if (Calendar.MONDAY == c.get(Calendar.DAY_OF_WEEK)) {
            return 1;
        }
        return 0;
    }

    /**
     * 获取当前日期上月/下月月初、月末
     *
     * @param assignDate
     *        指定日期
     * @param month
     *        上月/下月
     * @param flag
     *        0:月初1:月末
     * @return
     */
    public static Date getAssignDateMonthBeginAndEnd(Date assignDate,
                                                     int month, int flag) {
        Calendar c = Calendar.getInstance();
        if (null == assignDate) {
            c.setTime(new Date());
        } else {
            c.setTime(assignDate);
        }
        c.add(Calendar.MONTH, month);
        if (flag == 0) {
            c.set(Calendar.DAY_OF_MONTH, MONTH_BEGIN);
            return c.getTime();
        } else {
            c.set(Calendar.DAY_OF_MONTH, c
                    .getActualMaximum(Calendar.DAY_OF_MONTH));
            return c.getTime();
        }
    }

    /**
     * 获取当前日期上月/下月指定几号
     *
     * @param assignDate
     *        指定日期
     * @param month
     *        上月/下月
     * @param day
     *        几号
     * @return
     */
    public static Date getAssignDateMonthDay(Date assignDate, int month, int day) {
        Calendar c = Calendar.getInstance();
        if (null == assignDate) {
            c.setTime(new Date());
        } else {
            c.setTime(assignDate);
        }
        c.add(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * 获取指定日期几号的日期
     *
     * @param assignDate
     *        指定日期
     * @param day
     *        指定月份中的几号
     * @return 指定日期几号后的日期
     */
    public static Date getAssignDate(Date assignDate, int day) {
        Calendar c = Calendar.getInstance();
        c.setTime(assignDate);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c.getTime();
    }

    /**
     * 比较两个日期大小
     *
     * @param date1
     *        前一个日期
     * @param date2
     *        后一个日期
     * @return false:前一个日期大于后一个日期;true:前一个日期小于后一个日期
     */
    public static boolean compareTwoDate(Date date1, Date date2) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("系统当前时间="+formatter.format(date1));
        logger.debug("购买失效时间="+formatter.format(date2));
        if (date1.getTime() > date2.getTime()) {
            logger.debug("系统当前时间大于购买失效时间");
            return false;
        }
        logger.debug("系统当前时间小于购买失效时间");
        return true;
    }

    /**
     * 比较两个日期大小
     *
     * @param date1
     *        前一个日期
     * @param date2
     *        后一个日期
     * @return 0:日期相等,1:前<后,2:前>后
     */
    public static int compare2Date(Date date1, Date date2) {
        if (date1.getTime() == date2.getTime()) {
            return 0;
        }
        if (date1.getTime() < date2.getTime()) {
            return 1;
        }
        if (date1.getTime() > date2.getTime()) {
            return 2;
        }
        return -1;
    }

    /**
     * 比较两个日期大小
     *
     * @param date1
     *        前一个日期
     * @param date2
     *        后一个日期
     * @return 0:日期相等,1:前<后,2:前>后
     */
    public static int compareTwoDateYYYYMMDD(Date date1, Date date2) {
        date1 = DateUtil.formatStr2Date(DateUtil.formatDate2Str(date1,
                DateUtil.C_DATE_PATTON_DEFAULT), DateUtil.C_DATE_PATTON_DEFAULT);
        date2 = DateUtil.formatStr2Date(DateUtil.formatDate2Str(date2,
                DateUtil.C_DATE_PATTON_DEFAULT), DateUtil.C_DATE_PATTON_DEFAULT);
        if (date1.getTime() == date2.getTime()) {
            return 0;
        }
        if (date1.getTime() < date2.getTime()) {
            return 1;
        }
        if (date1.getTime() > date2.getTime()) {
            return 2;
        }
        return -1;
    }

    /**
     * 比较两个日期大小
     *
     * @param date1
     *        前一个日期
     * @param date2
     *        后一个日期
     * @return 0:日期相等,1:前<后,2:前>后
     */
    public static int compareTwoDateYYYYMM(Date date1, Date date2) {
        date1 = DateUtil.formatStr2Date(DateUtil.formatDate2Str(date1,
                        DateUtil.C_DATE_PATTON_YEAR_MONTH),
                DateUtil.C_DATE_PATTON_YEAR_MONTH);
        date2 = DateUtil.formatStr2Date(DateUtil.formatDate2Str(date2,
                        DateUtil.C_DATE_PATTON_YEAR_MONTH),
                DateUtil.C_DATE_PATTON_YEAR_MONTH);
        if (date1.getTime() == date2.getTime()) {
            return 0;
        }
        if (date1.getTime() < date2.getTime()) {
            return 1;
        }
        if (date1.getTime() > date2.getTime()) {
            return 2;
        }
        return -1;
    }

    /**
     * 判断两个日期年月日是否相等
     *
     * @param date1
     *        比较日期
     * @param date2
     *        被比较日期
     * @return 相等 :true 不相等:false
     */
    public static boolean compare2DateYYYYMMDD(Date date1, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        int year2 = calendar2.get(Calendar.YEAR);
        int month2 = calendar2.get(Calendar.MONTH) + 1;
        int day2 = calendar2.get(Calendar.DAY_OF_MONTH);
        if (year == year2 && month == month2 && day == day2) {
            return true;
        }
        return false;
    }

    public static Date getNextMonth(Date date, int day) {
        if (null == date) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        String nextDay = String.valueOf(calendar.get(Calendar.YEAR)) + "-"
                + String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-"
                + String.valueOf(day);
        return DateUtil.formatStr2Date(nextDay, C_DATE_PATTON_DEFAULT);
    }

    /**
     * 获得与指定日期 月份相同，天数自定义的日期类型 add by zyw 使用：根据还款计划的应付日期，获得出账单日期
     *
     * @param date
     * @param day
     * @return
     */
    public static Date getAssignDayDate(Date date, int day) {
        if (null == date) {
            date = new Date();
        }
        date=DateUtil.formatDate2Date(date, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar2Date(calendar);
    }

    /**
     * 获取指定日期开始时间点
     *
     * @param beginDate
     *        指定日期
     * @return 指定日期一天开始时间点
     */
    public static String getBeginDateString(String beginDate) {
        if (null == beginDate || beginDate.length() == 0) {
            beginDate = formatDate2Str(new Date(), C_TIME_PATTON_DEFAULT);
        }
        String ymd = beginDate.trim().substring(0, 10);
        String ymdhms = ymd + " " + DAYBEGIN;
        return ymdhms;
    }

    /**
     * 获取指定日期结束时间点
     *
     * @param enDate
     *        指定日期
     * @return 指定日期一天结束时间点
     */
    public static String getEndDateString(String enDate) {
        if (null == enDate || enDate.length() == 0) {
            enDate = formatDate2Str(new Date(), C_TIME_PATTON_DEFAULT);
        }
        String ymd = enDate.trim().substring(0, 10);
        String ymdhms = ymd + " " + DAYEND;
        return ymdhms;
    }

    public static String getDateStrFromTimestamp(Object obj) {
        String strDate = "";
        if (obj == null || !(obj instanceof java.sql.Timestamp)) {
            return "";
        }
        Timestamp t = (Timestamp) obj;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        strDate = sdf.format(t);
        return strDate;
    }

    public static Date getDateFromTimestamp(Object obj) {
        Date date = new Date();
        if (obj == null || !(obj instanceof java.sql.Timestamp)) {
            return null;
        }
        Timestamp t = (Timestamp) obj;
        date = t;
        return date;
    }

    /**
     * 判断当前系统日期是否是星期几
     *
     * @param day
     * @return
     */
    public static boolean isAssigneDayOfMonth(int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (calendar.get(Calendar.DAY_OF_WEEK) == day) {
            return true;
        }
        return false;
    }

    /**
     * 获取上个月开始日期/结束日期
     *
     * @param flag
     *        0：开始日期;1：结束日期
     * @return
     */
    public static Date getPreviousMonthBeginOrEnd(int flag) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (flag == 0) {
            calendar.set(Calendar.DAY_OF_MONTH, MONTH_BEGIN);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, calendar
                    .getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        return calendar.getTime();
    }

    /**
     * 计算指定上周/下周星期几是多少号
     *
     * @param week
     * @param dayOfWeek
     * @return
     */
    public static int findPreviousOrNextSundayByDayOfWeek(Date date, int week,
                                                          int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        if (null == date) {
            cal.setTime(new Date());
        } else {
            cal.setTime(date);
        }
        // n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
        int n = week;
        cal.add(Calendar.DATE, n * 7);
        // 想周几，这里就传几Calendar.MONDAY（TUESDAY...）
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期上周周日
     *
     * @param date
     * @param dayOfWeek
     * @return
     */
    public static Date findAssignDateAfterSunday(Date date, int dayOfWeek) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        for (int i = 1; i <= 7; i++) {
            if (c.get(Calendar.DAY_OF_WEEK) == dayOfWeek) {
                return c.getTime();
            } else {
                c.add(Calendar.DAY_OF_MONTH, -1);
            }
        }
        return c.getTime();
    }

    /**
     * 计算指定上周/下周星期几是所属月份
     *
     * @param week
     * @param dayOfWeek
     * @return
     */
    public static int findPreviousOrNextSundayByDayOfWeekMonth(int week,
                                                               int dayOfWeek) {
        Calendar cal = Calendar.getInstance();
        int n = week;
        cal.add(Calendar.DATE, n * 7);
        cal.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        return cal.get(Calendar.MONTH);
    }

    /**
     * 获取指定日期的天
     *
     * @return 日期中的天
     */
    public static int getCurrentDayOfDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(null == date ? new Date() : date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 判断两个日期之间存在几个周六
     *
     * @return 周六MAP
     */
    public static Map<Integer, Date> getSaturdayMap(Date assignDate,
                                                    Date currentDate) {
        Map<Integer, Date> saturdayMap = null;
        if (null == assignDate) {
            return null;
        }
        saturdayMap = new HashMap<Integer, Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(assignDate);
        int i = 1;
        int j = 1;
        while (DateUtil.compare2DateYYYYMMDD(c.getTime(), currentDate)
                || DateUtil.compare2Date(c.getTime(), currentDate) == 1) {
            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                saturdayMap.put(i++, c.getTime());
            }
            c.add(Calendar.DAY_OF_MONTH, j++);
        }
        return saturdayMap;
    }

    /**
     * 判断两个日期之间是否存在1号
     *
     * @param assignOverDate1
     *        指定日期1
     * @param assignOverDate2
     *        指定日期2
     * @return 返回距指定日期1较近1号日期
     */
    public static Date getMonthOfOneDay(Date assignOverDate1,
                                        Date assignOverDate2) {
        if (null == assignOverDate1 || null == assignOverDate2) {
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(assignOverDate1);

        if (DateUtil.compareTwoDateYYYYMMDD(c.getTime(), assignOverDate2) == 1
                || DateUtil.compareTwoDateYYYYMMDD(c.getTime(), assignOverDate2) == 0) {
            while (DateUtil.compareTwoDateYYYYMMDD(c.getTime(),
                    assignOverDate2) == 1) {
                if (c.get(Calendar.DAY_OF_MONTH) == DateUtil.MONTH_BEGIN) {
                    break;
                }
                c.add(Calendar.DAY_OF_MONTH, 1);
            }
        } else if (DateUtil.compareTwoDateYYYYMMDD(c.getTime(),
                assignOverDate2) == 2) {
            while (DateUtil.compareTwoDateYYYYMMDD(c.getTime(),
                    assignOverDate2) == 2
                    || DateUtil.compareTwoDateYYYYMMDD(c.getTime(),
                    assignOverDate2) == 0) {
                if (c.get(Calendar.DAY_OF_MONTH) == DateUtil.MONTH_BEGIN) {
                    break;
                }
                c.add(Calendar.DAY_OF_MONTH, -1);
            }
        } else {
            return null;
        }
        return c.getTime();
    }

    /**
     * 判断两个日期之间是否存在1号
     *
     * @param assignOverDate
     *        较早日期
     * @param overLimitDate
     *        较晚日期
     * @return 1号日期数组
     */
    public static List<Date> getMonthOfOneDayList(Date assignOverDate,
                                                  Date overLimitDate) {
        if (null == assignOverDate || null == overLimitDate) {
            return null;
        }

        List<Date> oneOfMonthList = new ArrayList<Date>();
        Calendar c = Calendar.getInstance();
        c.setTime(assignOverDate);

        if (DateUtil.compareTwoDateYYYYMMDD(c.getTime(), overLimitDate) == 2) {
            return null;
        }

        while (DateUtil.compareTwoDateYYYYMMDD(c.getTime(), overLimitDate) == 1
                || DateUtil.compareTwoDateYYYYMMDD(c.getTime(), overLimitDate) == 0) {
            if (c.get(Calendar.DAY_OF_MONTH) == DateUtil.MONTH_BEGIN) {
                oneOfMonthList.add(c.getTime());
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        return oneOfMonthList;
    }

    /**
     * 判断两个日期之间是否存在1号
     *
     * @param assignOverDate
     *        较早日期
     * @param overLimitDate
     *        较晚日期
     * @return 0:不存在;1:存在
     */
    public static int getMonthFlag(Date assignOverDate, Date overLimitDate) {
        if (null == assignOverDate || null == overLimitDate) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(assignOverDate);
        if (DateUtil.compareTwoDateYYYYMMDD(c.getTime(), overLimitDate) == 2) {
            return 0;
        }

        int j = 0;
        while (DateUtil.compareTwoDateYYYYMMDD(c.getTime(), overLimitDate) == 1
                || DateUtil.compareTwoDateYYYYMMDD(c.getTime(), overLimitDate) == 0) {
            if (c.get(Calendar.DAY_OF_MONTH) == DateUtil.MONTH_BEGIN) {
                j = j + 1;
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        return j;
    }

    /**
     * 返回跨月的1号的日期数组,转换成1号生成的应收的15号日期
     *
     * @param dayOfMonthList
     * @return 15号应收日期数组
     */
    public static List<Date> getMonthOfMiddleDayList(List<Date> dayOfMonthList) {
        if (null == dayOfMonthList) {
            return null;
        }
        List<Date> middelOfMonthList = new ArrayList<Date>();

        for (Date date : dayOfMonthList) {
            middelOfMonthList.add(DateUtil.getAssignDate(date, 15));
        }
        return middelOfMonthList;
    }

    /**
     * 指定日期与当前日期相隔月份
     *
     * @param assignDate
     *        指定日期
     * @param currentDate
     *        当期日期
     * @return 相隔月份
     */
    public static int isPreviousOrAfterMonthNumber(Date assignDate,
                                                   Date currentDate) {
        Calendar current = Calendar.getInstance();
        current.setTime(currentDate);

        Calendar previousOrAfter = Calendar.getInstance();
        previousOrAfter.setTime(assignDate);

        /** 指定日期小于当前日期 */
        if (DateUtil.compareTwoDateYYYYMM(assignDate, currentDate) == 0) {
            return 0;
        } else if (DateUtil.compareTwoDateYYYYMM(assignDate, currentDate) == 1) {
            int i = 0;
            while (DateUtil
                    .compareTwoDateYYYYMM(current.getTime(), assignDate) == 2) {
                current.add(Calendar.MONTH, -1);
                i++;
            }
            return -i;
        } else {
            int j = 0;
            while (DateUtil
                    .compareTwoDateYYYYMM(current.getTime(), assignDate) == 1) {
                current.add(Calendar.MONTH, 1);
                j++;
            }
            return j;
        }
    }

    /**
     * @描述: 根据当前系统日期返回俩位年份的字符串<br>
     * @return
     */
    public static String getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        String yearPath = String.valueOf(calendar.get(Calendar.YEAR));
        yearPath = yearPath.substring(2);
        return yearPath;
    }

    /**
     * 获取租金计划指定日期的开始日期
     *
     * @param assignDate
     * @param payCycle
     * @return
     */
    public static Date getPayPlanBeginDay(Date assignDate, int payCycle) {
        Calendar c = Calendar.getInstance();
        c.setTime(assignDate);
        int day = 0;
        for (int i = 0; i < payCycle; i++) {
            c.add(Calendar.MONTH, -1);
            day += DateUtil.findMaxDayInMonth(c.getTime());
        }
        return DateUtil.defineDayBefore2Date(assignDate, -day + 1);
    }

    /**
     * 格式化日期
     *
     * @param dateStr
     *        被格式化日期字符串
     * @return YYYYMMDD格式日期
     */
    public static Date formatYYYYMMDDStr2Date(String dateStr) {
        if (null != dateStr && dateStr.trim().length() > 0) {
            String yyyy = dateStr.substring(0, 4);
            String mm = dateStr.substring(4, 6);
            String dd = dateStr.substring(6, 8);
            String formatDateStr = yyyy + "-" + mm + "-" + dd;
            return formatStr2Date(formatDateStr, C_DATE_PATTON_DEFAULT);
        }
        return null;
    }

    /**
     * 判断两个日期是否在同一个月里
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean judgeSameMonth(Date d1, Date d2) {

        Calendar c1 = Calendar.getInstance();
        c1.setTime(d1);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(d2);
        if (c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.MONTH) == c2.get(Calendar.MONTH)) {

            return true;
        }
        return false;
    }

    /**
     * 获取指定日期月末
     *
     * @return 日期中的天
     */
    public static Date getAssignDateEnd(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(null == date ? new Date() : date);
        c.set(Calendar.DAY_OF_MONTH, DateUtil.findMaxDayInMonth(new Date()));
        return c.getTime();
    }

    /**
     * @描述: 判断日期是否为月末最后一天<br>
     * @创建人: <a href="mailto:zsb20051212@126.com">zsb</a>
     * @创建时间：2013-4-25 下午10:04:18
     * @修改人：<a href="mailto:zsb20051212@126.com">zsb</a>
     * @修改时间：2013-4-25 下午10:04:18
     * @修改备注：
     * @param date
     * @return
     */
    public static boolean isMonthEndDate(Date date) {
        if (null == date) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if (c.get(Calendar.DAY_OF_MONTH) == findMaxDayInMonth(c)) {
            return true;
        }
        return false;
    }

    public static String getFirstDayOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        return first;
    }

    public static String getLastDayOfMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        // 获取当前月最后一天：
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH,
                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return last;
    }
    /**
     * 订单生成随机时间
     * @Title: crutDatess
     * @date:   2018年8月20日 下午4:54:05
     * @author: 曲绍备
     * @param: @return
     * @return: String
     * @throws
     */
    public static String crutDatess(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        return formatter.format(date);
    }

    /**
     * 判断是工作日还是周末
     * @Title: getWeekend
     * @date:   2019年7月16日 下午5:17:56
     * @author: 曲绍备
     * @param: @return
     * @return: String
     * @throws
     */
    public static boolean getWeekend() {
        Calendar cal=Calendar.getInstance();
        cal.setTime(new Date());
        if(cal.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
            return true;//为周六、周日
        }else {
            return false;//为周一至周五
        }
    }

    /**
     * 得到分钟
     * @Title: timeConversionMoney
     * @date:   2019年7月16日 下午5:38:17
     * @author: 曲绍备
     * @param: @param dateTime
     * @param: @return
     * @param: @throws Exception
     * @return: long
     * @throws
     */
    public static long timeConversionMoney(String dateTime) throws Exception{
        Date parse = DateUtil.StringToDateTime(dateTime);
        Date d = new Date(System.currentTimeMillis());
        long time1 = (d.getTime() - parse.getTime());
        long times =  time1 / 1000 / 60;
        return times;
    }

    /**
     *
     * @Title: belongCalendar
     * @date:   2020年3月17日 下午5:32:00
     * @author: 曲绍备
     * @param: @param nowTime
     * @param: @param beginTime
     * @param: @param endTime
     * @param: @return
     * @return: boolean
     * @throws
     */
    public static boolean belongCalendar(Date nowTime, Date beginTime,Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);

        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);

        Calendar end = Calendar.getInstance();
        end.setTime(endTime);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 判断是否在当天时间内
     * @Title: isToday
     * @date:   2019年8月18日 下午7:02:28
     * @author: 曲绍备
     * @param: @param inputJudgeDate
     * @param: @return
     * @return: boolean
     * @throws
     */
    public static boolean isToday(Date inputJudgeDate) {
        boolean flag = false;
        //获取当前系统时间
        long longDate = System.currentTimeMillis();
        Date nowDate = new Date(longDate);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(nowDate);
        String subDate = format.substring(0, 10);
        //定义每天的24h时间范围
        String beginTime = subDate + " 00:00:00";
        String endTime = subDate + " 23:59:59";
        Date paseBeginTime = null;
        Date paseEndTime = null;
        try {
            paseBeginTime = dateFormat.parse(beginTime);
            paseEndTime = dateFormat.parse(endTime);

        } catch (ParseException e) {
            log.error(e.getMessage());
        }
        if(inputJudgeDate.after(paseBeginTime) && inputJudgeDate.before(paseEndTime)) {
            flag = true;
        }
        return flag;
    }
    /**
     * 查询当前是时间是否在设定的时间段内
     * @Title: isEffectiveDate
     * @date:   2021年9月3日 下午3:00:09
     * @author: 曲绍备
     * @param: @param startTime
     * @param: @param endTime
     * @param: @return
     * @param: @throws ParseException
     * @return: boolean
     * @throws
     */
    public static boolean isEffectiveDate(String startTime, String endTime) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Date currentTime = new Date(System.currentTimeMillis());
        Calendar date = Calendar.getInstance();
        date.setTime(df.parse(df.format(currentTime)));

        Calendar begin = Calendar.getInstance();
        Date startTimes = df.parse(startTime);
        begin.setTime(startTimes);

        Calendar end = Calendar.getInstance();
        Date endTimes = df.parse(endTime);
        end.setTime(endTimes);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 判断上午或者下午
     * @Title: judgeMorningAndAfternoon
     * @date:   2021年9月3日 下午1:57:28
     * @author: 曲绍备
     * @param: @param time
     * @param: @return
     * @return: boolean
     * @throws
     */
    public static boolean judgeMorningAndAfternoon(){
        //判断小游的上班时间/景区各种原因停云
        Calendar calendar = Calendar.getInstance();
        String AM_PM = calendar.get(Calendar.AM_PM) == Calendar.AM?"1" : "2";
        if (AM_PM.equals("1")) {
            return true;
        }else {
            return false;
        }
    }


    /**
     * 曲绍备
     * @param startTime	开始时间
     * @param endTime   结束时间
     * @return
     * @author sunran   判断当前时间在时间区间内
     */
    public static boolean isEffectiveDates(String startTime, String endTime) throws ParseException{
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date startTimes = ft.parse(startTime);
        Date endTimes = ft.parse(endTime);
        Date nowTimes = new Date();

        if (nowTimes.getTime() == startTimes.getTime()
                || nowTimes.getTime() == endTimes.getTime()) {
            return true;
        }

        Calendar date = Calendar.getInstance();
        date.setTime(nowTimes);

        Calendar begin = Calendar.getInstance();
        begin.setTime(startTimes);

        Calendar end = Calendar.getInstance();
        end.setTime(endTimes);

        if (date.after(begin) && date.before(end)) {
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) throws ParseException{
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
        Date startTime = ft.parse("2021-10-05");
        Date endTime = ft.parse("2021-11-09");
        Date nowTime = new Date();
        boolean effectiveDate = isEffectiveDates("2021-10-05", "2021-11-09");
        System.out.println(effectiveDate);
        if (effectiveDate) {
            System.out.println("当前时间在范围内");
        }else {
            System.out.println("当前时间在不在范围内");
        }
    }


	/*public static void main(String[] args) throws ParseException {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date parse = simpleDateFormat.parse("2019-08-18 09:13:32");
		boolean today = isToday(parse);
		System.out.println("当前时间："+today);
	}*/

}
