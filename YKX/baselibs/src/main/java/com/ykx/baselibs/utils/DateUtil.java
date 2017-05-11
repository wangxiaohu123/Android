package com.ykx.baselibs.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 功能描述：
 *
 * @author liping
 * @version 1.0
 * @Date Jul 19, 2008
 * @Time 9:47:53 AM
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {

    public static final long MILLIES_OF_ONE_DAY = 86400000l;

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_FORMAT_TO_DAY = "yyyy-MM-dd";

    public static Date date = null;

    public static DateFormat dateFormat = null;

    public static Calendar calendar = null;

    public static SimpleDateFormat formatToSecond = new SimpleDateFormat(DATE_FORMAT);

    public static SimpleDateFormat formatToDay = new SimpleDateFormat(DATE_FORMAT_TO_DAY);

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期
     * @param format  String 格式
     * @return Date 日期
     */
    public static Date parseDate(String dateStr, String format) {
        try {
            dateFormat = new SimpleDateFormat(format);
            String dt = dateStr.replaceAll("-", "/");
            if ((!dt.equals("")) && (dt.length() < format.length())) {
                dt += format.substring(dt.length()).replaceAll("[YyMmDdHhSs]", "0");
            }
            date = (Date) dateFormat.parse(dt);
        } catch (Exception e) {
        }
        return date;
    }

    /**
     * 功能描述：格式化日期
     *
     * @param dateStr String 字符型日期：YYYY-MM-DD 格式
     * @return Date
     */
    public static Date parseDate(String dateStr) {
        return parseDate(dateStr, "yyyy/MM/dd");
    }

    /**
     * 功能描述：格式化输出日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return 返回字符型日期
     */
    public static String format(Date date, String format) {
        String result = "";
        try {
            if (date != null) {
                dateFormat = new SimpleDateFormat(format);
                result = dateFormat.format(date);
            }
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 功能描述：
     *
     * @param date Date 日期
     * @return
     */
    public static String format(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回年份
     *
     * @param date Date 日期
     * @return 返回年份
     */
    public static int getYear(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月份
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 返回指定日期的周数
     *
     * @param date
     * @return
     */
    public static int getWeek(Date date) {
        calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取指定年周的第一天和最后一天
     *
     * @param year
     * @param week
     * @param flag true-返回第一天日期,false-返回最后一天日期
     * @return
     */
    public static Date getDayByWeek(int year, int week, boolean flag) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        if (!flag)
            cal.setTimeInMillis(cal.getTimeInMillis() + 6 * 24 * 60 * 60 * 1000);
        return cal.getTime();
    }

    /**
     * 功能描述：返回日份
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分钟
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫秒
     */
    public static long getMillis(Date date) {
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getTimeInMillis();
    }

    /**
     * 功能描述：返回字符型日期
     *
     * @param date 日期
     * @return 返回字符型日期 yyyy/MM/dd 格式
     */
    public static String getDate(Date date) {
        return format(date, "yyyy/MM/dd");
    }

    /**
     * 功能描述：返回字符型时间
     *
     * @param date Date 日期
     * @return 返回字符型时间 HH:mm:ss 格式
     */
    public static String getTime(Date date) {
        return format(date, "HH:mm:ss");
    }

    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy/MM/dd HH:mm:ss 格式
     */
    public static String getDateTime(Date date) {
        return format(date, "yyyy/MM/dd HH:mm:ss");
    }
    /**
     * 功能描述：返回字符型日期时间
     *
     * @param date Date 日期
     * @return 返回字符型日期时间 yyyy-MM-dd HH:mm:ss 格式
     */
    public static String getDateTimeWithFormart(Date date) {
        return format(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 功能描述：日期相加
     *
     * @param date Date 日期
     * @param day  int 天数
     * @return 返回相加后的日期
     */
    public static Date addDate(Date date, int day) {
        calendar = Calendar.getInstance();
        long millis = getMillis(date) + ((long) day) * 24 * 3600 * 1000;
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    /**
     * 功能描述：日期相减
     *
     * @param date  Date 日期
     * @param date1 Date 日期
     * @return 返回相减后的日期
     */
    public static int diffDate(Date date, Date date1) {
        return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
    }

    /**
     * 功能描述：取得指定月份的第一天
     *
     * @param strdate String 字符型日期
     * @return String yyyy-MM-dd 格式
     */
    public static String getMonthBegin(String strdate) {
        date = parseDate(strdate);
        return format(date, "yyyy-MM") + "-01";
    }

    /**
     * 功能描述：取得指定月份的最后一天
     *
     * @param strdate String 字符型日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String getMonthEnd(String strdate) {
        date = parseDate(getMonthBegin(strdate));
        calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return formatDate(calendar.getTime());
    }

    /**
     * 功能描述：常用的格式化日期
     *
     * @param date Date 日期
     * @return String 日期字符串 yyyy-MM-dd格式
     */
    public static String formatDate(Date date) {
        return formatDateByFormat(date, "yyyy-MM-dd");
    }

    /**
     * 功能描述：以指定的格式来格式化日期
     *
     * @param date   Date 日期
     * @param format String 格式
     * @return String 日期字符串
     */
    public static String formatDateByFormat(Date date, String format) {
        String result = "";
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                result = sdf.format(date);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @param time   时间字符串
     * @param format 时间字符串对应的时间格式
     * @return
     */

    public static Date stringToDate(String time, String format) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date s_date = (Date) sdf.parse(time);
            return s_date;
        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;
    }

    /**
     * 获取当前年份
     *
     * @return
     */
    public static int getCurrentYear() {
        return Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentMonth() {
        return Integer.parseInt(new SimpleDateFormat("yyyy-MM").format(new Date()).split("-")[1]);
    }

    /**
     * 获取当前周数
     *
     * @return
     */
    public static int getCurrentWeek() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.setTime(new Date());
        return calendar.get(Calendar.WEEK_OF_YEAR);

    }

    /**
     * 获取当前月份
     *
     * @return
     */
    public static int getCurrentDay() {
        return Integer.parseInt(new SimpleDateFormat("yyyy-MM-dd").format(new Date()).split("-")[2]);
    }

    /**
     * 获取当前年份指定月份的天数
     *
     * @param month 月份
     * @return
     */
    public static int getDateOfMonth(int month) {
        return getDateOfMonth(getCurrentYear(), month);
    }

    /**
     * 获取指定年份指定月份的天数
     *
     * @param month 月份
     * @return
     */
    public static int getDateOfMonth(int year, int month) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);// Java月份才0开始算
        int dateOfMonth = calendar.getActualMaximum(Calendar.DATE); // 获取当前月的天数
        return dateOfMonth;
    }

    /**
     * 取得指定日期所在周的第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
        return c.getTime();
    }

    /**
     * 取得指定日期所在周的最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfWeek(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setTime(date);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
        return c.getTime();
    }

    /**
     * 获取指定日期是星期几
     * 参数为null时表示获取当前日期是星期几
     *
     * @param time
     * @return
     */
    public static String getWeekOfDate(String time, String timeFormart) {
        Date date = stringToDate(time, timeFormart);
        String[] weekOfDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar calendar = Calendar.getInstance();
        if (date != null) {
            calendar.setTime(date);
        }
        int w = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0) {
            w = 0;
        }
        return weekOfDays[w];
    }

    public static String cutOffHMS(String time) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = formatToSecond.parse(time);
            calendar.setTime(date);
            return formatToDay.format(new Date(calendar.getTimeInMillis()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public static int[] getYearMonthAndDate(String time) {
        Calendar calendar = Calendar.getInstance();
        int[] result = new int[3];
        try {
            Date date = formatToSecond.parse(time);
            calendar.setTime(date);
            result[0] = calendar.get(Calendar.YEAR);
            result[1] = calendar.get(Calendar.MONTH) + 1;
            result[2] = calendar.get(Calendar.DATE);
        } catch (Exception e) {

        }
        return result;
    }

    public static String[] getYearMonthAndDateInString(String time) {
        int[] result = getYearMonthAndDate(time);
        return new String[]{String.valueOf(result[0]), result[1] > 10 ? String.valueOf(result[1]) : ("0" + result[1]), result[2] > 10 ? String.valueOf(result[2]) : ("0" + result[2])};
    }

    public static int computeDays(String time) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = formatToSecond.parse(time);
            int today = calendar.get(Calendar.DATE);
            calendar.setTime(date);
            int thatDay = calendar.get(Calendar.DATE);
            return today - thatDay;
        } catch (Exception e) {
            System.err.println("----compute days Error");
        }
        return 0;
    }

    public static String getTime(String time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(Long.valueOf(time));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    public static String getMD(String time) {

        Date date = stringToDate(time, DateUtil.DATE_FORMAT);

        if (date == null) {
            return "暂无";
        }

        return date.getMonth() + "月" + date.getDate() + "日";
    }


    public static String getTimeMessage(String time) {
        StringBuffer stringBuffer = new StringBuffer("");
        long lastTime = DateUtil.stringToDate(time, DateUtil.DATE_FORMAT).getTime();
        long durtion = (new Date().getTime() - lastTime) / 1000;
        if (durtion < 100) {
            stringBuffer.append((int) durtion).append("秒");
        } else {
            long m = durtion / 60;
            if (m < 100) {
                stringBuffer.append((int) m).append("分钟");
            } else {
                long h = m / 60;
                if (h < 100) {
                    stringBuffer.append((int) h).append("小时");
                } else {
                    long d = h / 24;
                    if (d < 100) {
                        stringBuffer.append((int) d).append("天");
                    } else {
                        long mm = d / 30;
                        if (mm < 12) {
                            stringBuffer.append((int) mm).append("月");
                        } else {
                            long y = mm / 12;
                            if (y < 100) {
                                stringBuffer.append((int) y).append("年");
                            }
                        }
                    }
                }
            }
        }

        return stringBuffer.append("前").toString();
    }


    public static void main(String[] args) {
        Date d = new Date();
        // System.out.println(d.toString());
        // System.out.println(formatDate(d).toString());
        // System.out.println(getMonthBegin(formatDate(d).toString()));
        // System.out.println(getMonthBegin("2008/07/19"));
        // System.out.println(getMonthEnd("2008/07/19"));
        System.out.println(addDate(d, 15).toString());

    }

}
