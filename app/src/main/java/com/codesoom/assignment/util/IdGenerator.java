package com.codesoom.assignment.util;

import java.util.concurrent.atomic.AtomicLong;

public class IdGenerator {
    private static AtomicLong idCounter = new AtomicLong(); // default value is 0

    public static long next() {
        return idCounter.incrementAndGet();
    }
}
