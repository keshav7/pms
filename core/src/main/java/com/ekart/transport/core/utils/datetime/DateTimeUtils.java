package com.ekart.transport.core.utils.datetime;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateTimeUtils {

    static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    static final String DATE_PATTERN = "yyyy-MM-dd";
    static final String DAY_PATTERN = "EEEE";
    static final String TIME_PATTERN = "HH:mm:ss";


    public static DateTime getISTDate(DateTime dateTime){
        if(dateTime!=null){
            DateTimeZone timeZone = DateTimeZone.forTimeZone(TimeZone.getTimeZone("IST"));
            return dateTime.toDateTime(timeZone);
        }
        return null;
    }

    //2016-04-26 13:12:11 --> 2016-04-26 00:00:00
    public static DateTime startOfDay(DateTime dateTime) {
        return dateTime.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }

    public static DateTime endOfDay(DateTime dateTime) {
        return dateTime.withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(59);
    }

    //2016-04-26 13:12:11 --> 2016-04-26
    public static String getDateToday(DateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_PATTERN);
        return formatter.print(dateTime);
    }

    //"2016-04-26 13:12:11" --> 2016-04-26 00:00:00
    public static DateTime startOfDay(String dateString) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_TIME_PATTERN);
        DateTime date = formatter.parseDateTime(dateString);
        return date.withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
    }

    public static int compareTime(String timeString1, String timeString2) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(TIME_PATTERN);
        DateTime time1 = formatter.parseDateTime(timeString1);
        DateTime time2 = formatter.parseDateTime(timeString2);
        return time1.compareTo(time2);
    }

    //"2016-04-26 13:12:11" --> 2016-04-26 13:12:11
    public static String getDateTimeString(DateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_TIME_PATTERN);
        return formatter.print(dateTime);
    }

    public static DateTime getDateTimeForString(String dateTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_TIME_PATTERN);
        return formatter.parseDateTime(dateTime);
    }

    public static DateTime getDateTimeForString(String dateTime, String pattern) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
        return formatter.parseDateTime(dateTime);
    }

    //"13:00:00" --> 2016-04-26 13:00:00
    public static DateTime getDateTimeForTimeString(String timeString) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(TIME_PATTERN);
        DateTime date = formatter.parseDateTime(timeString);
        DateTime currentDate = DateTime.now();
        return date.withYear(currentDate.getYear()).withMonthOfYear(currentDate.getMonthOfYear()).withDayOfMonth(currentDate.getDayOfMonth());
    }

    //2016-04-26 13:00:00 --> "13:00:00"
    public static String getTimeString(DateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(TIME_PATTERN);
        return formatter.print(dateTime);
    }

    //2016-04-26 13:00:00 --> Tuesday
    public static String getDay(DateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DAY_PATTERN).withLocale(Locale.ENGLISH);
        return formatter.print(dateTime);
    }

    public static boolean isNowBetweenRange(String startTime, String endTime) {
        if(StringUtils.isEmpty(startTime) || StringUtils.isEmpty(startTime)){
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormat.forPattern(DATE_TIME_PATTERN);
        DateTime time1 = formatter.parseDateTime(startTime);
        DateTime time2 = formatter.parseDateTime(endTime);
        return time1.isBeforeNow() && time2.isAfterNow();

    }

    public static String format(Date date) {
        if (date != null) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            String formattedDate = dateFormat.format(date);
            org.joda.time.format.DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            DateTime dateTime = dtf.parseDateTime(formattedDate.toString());
            return dateTime.toString();
        }
        return null;
    }

}