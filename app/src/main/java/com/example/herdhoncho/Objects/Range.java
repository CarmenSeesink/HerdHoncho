package com.example.herdhoncho.Objects;

public class Range {

    long min;
    long max;
    double variance = 0;

    public Range(long min, long max, double variance) {
        this.min = min;
        this.max = max;
        this.variance = variance;
    }

    public Range() {
        min = 0;
        max = 0;
    }

    public long getMax() {
        return max;
    }

    public void setMax(long max) {
        this.max = max;
    }

    public double getVariance() {
        return variance;
    }

    public void setVariance(double variance) {
        this.variance = variance;
    }

    public long getMin() {
        return min;
    }

    public void setMin(long min) {
        this.min = min;
    }

    public boolean isInRange(String hexValue) {
        return this.isInRange(Long.parseLong(hexValue, 16));
    }

    public boolean isInRange(long value) {
        return
                value >= (getMin() * (1 - getVariance()))
                        &&
                        value <= (getMax() * (1 + getVariance()));
    }
}