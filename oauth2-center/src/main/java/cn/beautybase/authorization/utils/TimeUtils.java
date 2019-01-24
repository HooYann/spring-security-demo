package cn.beautybase.authorization.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Java8日期新包java.time
 * @author Yann
 * @date 2018-01-24
 */
public class TimeUtils {

    public static  final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static  final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static  final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    public static String format(LocalDate date, String format){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    /**
     * 格式化时间格式:yyyy-MM-dd HH:mm:ss
     * @param datetime
     * @return
     */
    public static String formatDateTime(LocalDateTime datetime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT);
        return datetime.format(formatter);
    }

    /**
     * 格式化日期格式:yyyy-MM-dd
     * @param date
     * @return
     */
    public static String formatDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return date.format(formatter);
    }

    /**
     * 格式化日期格式:yyyy-MM-dd
     * @param time
     * @return
     */
    public static String formatDate(LocalTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_TIME_FORMAT);
        return time.format(formatter);
    }


}

