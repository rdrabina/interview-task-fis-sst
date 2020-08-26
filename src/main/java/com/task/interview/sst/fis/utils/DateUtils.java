package com.task.interview.sst.fis.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class DateUtils {

    public static Date generateDateBetween(Date start, Date end) {
        return new Date(ThreadLocalRandom.current().nextLong(start.getTime(), end.getTime()));
    }

    public static Date addDaysToDate(int days) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);

        return calendar.getTime();
    }

}
