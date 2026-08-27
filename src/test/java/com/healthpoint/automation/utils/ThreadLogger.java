package com.healthpoint.automation.utils;

public class ThreadLogger {

    private ThreadLogger() {
    }

    public static void logCurrentThread(String testName) {
        System.out.println(
                "[THREAD] " + testName +
                " -> " + Thread.currentThread().getName()
        );
    }
}
