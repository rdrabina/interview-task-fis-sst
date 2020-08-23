package com.task.interview.sst.fis.utils;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

public class DateUtils {

    public static Date generateDateBetween(Date start, Date end) {
        return new Date(ThreadLocalRandom.current().nextLong(start.getTime(), end.getTime()));
    }

}
