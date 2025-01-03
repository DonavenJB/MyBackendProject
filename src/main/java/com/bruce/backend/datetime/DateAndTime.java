package com.bruce.backend.datetime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateAndTime {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd HH:mm:ss");

    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd HH:mm");

    private static final SimpleDateFormat logFormat = new SimpleDateFormat("MM/dd HH:mm:ss zzz");

    static {
        logFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public static final boolean isDate(String string1, String string2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(string2);
            simpleDateFormat.parse(string1).getTime();
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static final long days(int n) {
        return 86400000L * n;
    }

    public static final long parseDate(String string1, String string2) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(string2);
            return simpleDateFormat.parse(string1).getTime();
        } catch (Exception exception) {
            LogSanity.logException("Could not parse '" + string1 + "' with '" + string2 + "'", exception, false);
            return 0L;
        }
    }

    public static final String formatDateAny(String string, long l) {
        Date date = new Date(l);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(string);
        return simpleDateFormat.format(date);
    }

    public static final String formatLogDate(long l) {
        Date date = new Date(l);
        return logFormat.format(date);
    }

    public static final String formatDate(long l) {
        Date date = new Date(l);
        return dateFormat.format(date);
    }

    public static final String formatTime(long l) {
        Date date = new Date(l);
        return timeFormat.format(date);
    }
    
    public static class LogSanity {
        public static void logException(String message, Throwable t, boolean b) {
            
        }
    }
}
