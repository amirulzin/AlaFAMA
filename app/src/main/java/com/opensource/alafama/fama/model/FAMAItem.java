package com.opensource.alafama.fama.model;

/**
 * Immutable class for FAMA tabular rows.
 */
public final class FAMAItem
{
    private final String famaId;
    private final String name;
    private final String grade;
    private final String unit;
    private final String max, average, min;

    public FAMAItem(final String famaId, final String name, final String grade, final String unit, final String max, final String average, final String min)
    {
        this.famaId = famaId;
        this.name = name;
        this.grade = grade;
        this.unit = unit;
        this.max = max;
        this.average = average;
        this.min = min;
    }

    /**
     * Returns true if all fields aren't null. False otherwise.
     */
    public boolean isValid()
    {
        return famaId != null && name != null && grade != null && unit != null && max != null && average != null && min != null;
    }

    /**
     * Enums for column data located in FAMA price tables.
     */
    public enum RowData
    {
        NAME(0), GRADE(1), UNIT(2), MAX(3), AVERAGE(4), MIN(5);

        //Future proofing for easier move-arounds
        private final int value;

        RowData(final int value)
        {
            this.value = value;
        }

        /**
         * Gets the column value for the enum
         */
        public int getValue()
        {
            return value;
        }
    }
}
