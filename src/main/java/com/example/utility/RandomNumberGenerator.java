package com.example.utility;

import java.util.Random;

public final class RandomNumberGenerator {

    private static final Random RANDOM = new Random();

    private RandomNumberGenerator() {
    }

    public static long generateLong(long origin, long bound) {
        return RANDOM.nextLong(origin, bound);
    }

    public static int generateInt(int origin, int bound) {
        return RANDOM.nextInt(origin, bound);
    }

}
