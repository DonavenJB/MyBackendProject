package com.bruce.backend.common;

import com.bruce.backend.dataconversion.DataConversion;

public class LogSanity {

    public static void logException(String message, Throwable throwable, boolean debug) {
        if (debug) {
            DataConversion.bString(("WARN: " + message + throwable.getMessage()).getBytes());
        } else {
            System.out.println("[ERROR] " + message + ": " + throwable.getMessage());
        }
    }
}
