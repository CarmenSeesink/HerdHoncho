package com.example.herdhoncho.Objects;

import com.example.herdhoncho.Objects.Range;

import java.util.Arrays;

public class Color {

    String name;
    int year;
    Range[] ranges;

    public Color(String name, int year, Range[] ranges) {
        this.name = name;
        this.year = year;
        this.ranges = ranges;
    }

    public Color() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Range[] getRanges() {
        return ranges;
    }

    public void setRanges(Range[] ranges) {
        this.ranges = ranges;
    }

    public boolean existsInRange(String value) {
        return existsInRange(Long.parseLong(value, 16));
    }

    public boolean existsInRange(long value) {
        for(Range item : ranges) {
            if(item.isInRange(value))
                return true;
        }
        return false;
    }
}
