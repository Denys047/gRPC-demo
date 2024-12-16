package com.example.utility;

public final class SumNumber {

    private long sum = 0;

    public SumNumber() {

    }

    public void addNumber(final long number) {
        sum += number;
    }

    public long getSum() {
        return sum;
    }

}
