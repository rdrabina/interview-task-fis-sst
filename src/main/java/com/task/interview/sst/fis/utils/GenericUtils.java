package com.task.interview.sst.fis.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class GenericUtils {

    public static <T> List<T> fromArrayToUnmodifiableList(T[] array) {
        return Arrays.asList(array);
    }

    public static <T> T getRandomObjectFromList(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(0, list.size()));
    }

}
