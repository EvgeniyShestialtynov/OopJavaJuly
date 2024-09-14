package ru.academits.shestialtynov.range;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public double getTo() {
        return to;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return from <= number && number <= to;
    }

    public Range getRangesCrossing(Range range) {
        if ((from <= range.from && range.from < to) || (from < range.to && range.to <= to)) {
            double rangesCrossingFrom = Math.max(from, range.from);
            double rangesCrossingTo = Math.min(to, range.to);
            return new Range(rangesCrossingFrom, rangesCrossingTo);
        }

        return null;
    }

    public Range[] getRangesCombining(Range range) {
        double minFrom = Math.min(from, range.from);
        double maxFrom = Math.max(from, range.from);
        double minTo = Math.min(to, range.to);
        double maxTo = Math.max(to, range.to);

        if (maxFrom == minTo) {
            return new Range[]{new Range(minFrom, maxTo)};
        }

        return new Range[]{new Range(minFrom, maxFrom), new Range(minTo, maxTo)};
    }

    public Range[] getRangesDifference(Range range) {
        if ((range.to <= from) || (to <= range.from)) {
            return new Range[]{new Range(from, to)};
        }

        if ((range.from <= from) && (from < range.to) && (range.to < to)) {
            return new Range[]{new Range(range.to, to)};
        }

        if (from < range.from) {
            if (range.to < to) {
                return new Range[]{new Range(from, range.from), new Range(range.to, to)};
            }

            return new Range[]{new Range(from, range.from)};
        }

        return new Range[0];
    }
}