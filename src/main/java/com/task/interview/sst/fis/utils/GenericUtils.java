package com.task.interview.sst.fis.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GenericUtils {

    public static <T> List<T> fromArrayToUnmodifiableList(T[] array) {
        return Arrays.asList(array);
    }

    public static <T> T getRandomObjectFromList(List<T> list) {
        return list.get(ThreadLocalRandom.current().nextInt(0, list.size()));
    }

    public static <T> Set<T> generateRandomSubsetFromList(ThreadLocalRandom current, List<T> list, int bound) {
        int numberOfElements = current.nextInt(1, bound);

        return IntStream.range(0, numberOfElements)
                .mapToObj(j -> getRandomObjectFromList(list))
                .collect(Collectors.toSet());
    }

}
