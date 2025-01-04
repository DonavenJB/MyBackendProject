package com.bruce.backend.datetime;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.OptionalLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DateAndTime {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("MM/dd HH:mm:ss");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("MM/dd HH:mm");
    public static final DateTimeFormatter LOG_FORMAT = DateTimeFormatter.ofPattern("MM/dd HH:mm:ss zzz")
            .withZone(ZoneId.of("UTC"));
    private static final long MILLIS_PER_DAY = 86_400_000L;

    private DateAndTime() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static boolean isDate(String dateString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime.parse(dateString, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static long daysToMillis(int days) {
        return MILLIS_PER_DAY * days;
    }

    public static OptionalLong parseDate(String dateString, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
                    .withZone(ZoneId.systemDefault());
            ZonedDateTime zonedDateTime = ZonedDateTime.parse(dateString, formatter);
            return OptionalLong.of(zonedDateTime.toInstant().toEpochMilli());
        } catch (DateTimeParseException e) {
            LogSanity.logException("Could not parse '" + dateString + "' with '" + pattern + "'", e, false);
            return OptionalLong.empty();
        }
    }

    public static String formatDateAny(String pattern, long epochMilli) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern)
                .withZone(ZoneId.systemDefault());
        return formatter.format(Instant.ofEpochMilli(epochMilli));
    }

    public static String formatLogDate(long epochMilli) {
        return LOG_FORMAT.format(Instant.ofEpochMilli(epochMilli));
    }

    public static String formatDate(long epochMilli) {
        return DATE_FORMAT.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault()));
    }

    public static String formatTime(long epochMilli) {
        return TIME_FORMAT.format(LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), ZoneId.systemDefault()));
    }

    public static final class LogSanity {
        private static final Logger LOGGER = LoggerFactory.getLogger(LogSanity.class);

        private LogSanity() {
            throw new AssertionError("Cannot instantiate LogSanity utility class");
        }

        public static void logException(String message, Throwable throwable, boolean debug) {
            LOGGER.error(message, throwable);
            if (debug) {
                throwable.printStackTrace();
            }
        }
    }
}
