package com.bruce.backend;

import com.bruce.backend.datetime.DateAndTime;

public class App {
    public static void main(String[] args) {
        String currentDate = DateAndTime.formatDate(System.currentTimeMillis());
        System.out.println("🔹 Current Date (Local): " + currentDate);

        long fiveDaysInMillis = DateAndTime.days(5);
        System.out.println("🔹 Five Days in Milliseconds: " + fiveDaysInMillis);

        long parsedDate = DateAndTime.parseDate("01/03/2024 12:00:00", "MM/dd/yyyy HH:mm:ss");
        System.out.println("🔹 Parsed Date in Milliseconds: " + parsedDate);

        String logDate = DateAndTime.formatLogDate(parsedDate);
        System.out.println("🔹 Log Date (UTC): " + logDate);
    }
}