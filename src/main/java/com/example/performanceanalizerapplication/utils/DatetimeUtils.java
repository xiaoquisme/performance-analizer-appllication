package com.example.performanceanalizerapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

public class DatetimeUtils {
    public static final Supplier<String> currentDay;
    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");
        currentDay = () -> simpleDateFormat.format(new Date());
    }
}
