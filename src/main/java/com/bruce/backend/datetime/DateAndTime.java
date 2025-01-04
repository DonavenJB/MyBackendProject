package com.bruce.backend.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date; // Add this import
import java.util.logging.Level;
import java.util.logging.Logger;

public class DateAndTime {
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd HH:mm:ss");
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd HH:mm");
    private static final DateTimeFormatter LOG_FORMAT = DateTimeFormatter.ofPattern("MM/dd HH:mm:ss zzz").withZone(ZoneId.of("UTC"));
    private static final long MILLIS_PER_DAY = 86_400_000L;

    public static boolean isDate(String string1, String string2) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(string2);
            LocalDateTime.parse(string1, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static long days(int n) {
        return MILLIS_PER_DAY * n;
    }

    public static long parseDate(String string1, String string2) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(string2);
            LocalDateTime dateTime = LocalDateTime.parse(string1, formatter);
            return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        } catch (DateTimeParseException e) {
            LogSanity.logException("Could not parse '" + string1 + "' with '" + string2 + "'", e, false);
            return 0L;
        }
    }

    public static String formatDateAny(String string, long l) {
        Date date = new Date(l);
        java.text.SimpleDateFormat simpleDateFormat = new java.text.SimpleDateFormat(string);
        return simpleDateFormat.format(date);
    }

    public static String formatLogDate(long l) {
        return LOG_FORMAT.format(Instant.ofEpochMilli(l));
    }

    public static String formatDate(long l) {
        return DATE_FORMAT.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.systemDefault()));
    }

    public static String formatTime(long l) {
        return TIME_FORMAT.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.systemDefault()));
    }

    public static class LogSanity {
        private static final Logger LOGGER = Logger.getLogger(LogSanity.class.getName());
        public static void logException(String message, Throwable t, boolean debug) {
            LOGGER.log(Level.SEVERE, message, t);
            if (debug) {
                t.printStackTrace();
            }
        }
    }
}