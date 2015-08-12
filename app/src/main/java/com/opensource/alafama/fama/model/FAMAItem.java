package com.opensource.alafama.fama.model;

//Immutable
public final class FAMAItem
{
    private final String name;
    private final String grade;
    private final String unit;
    private final String max, average, min;

    public FAMAItem(final String name, final String grade, final String unit, final String max, final String average, final String min)
    {
        this.name = name;
        this.grade = grade;
        this.unit = unit;
        this.max = max;
        this.average = average;
        this.min = min;
    }
}
