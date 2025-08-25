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

    @Override
    public String toString() {
        return "[" + from + " - " + to + "]";
    }

    public Range getIntersection(Range range) {
        double maxFrom = Math.max(from, range.from);
        double minTo = Math.min(to, range.to);

        if (minTo <= maxFrom) {
            return null;
        }

        return new Range(maxFrom, minTo);
    }

    public Range[] getUnion(Range range) {
        if (to < range.from || range.to < from) {
            return new Range[]{new Range(from, to), new Range(range.from, range.to)};
        }

        double minFrom = Math.min(from, range.from);
        double maxTo = Math.max(to, range.to);

        return new Range[]{new Range(minFrom, maxTo)};
    }

    public Range[] getDifference(Range range) {
        if (to < range.from || range.to < from) {
            return new Range[]{new Range(from, to)};
        }

        if (range.from <= from) {
            if (to <= range.to) {
                return new Range[0];
            }

            return new Range[]{new Range(range.to, to)};
        }

        if (to <= range.to) {
            return new Range[]{new Range(from, range.from)};
        }

        return new Range[]{new Range(from, range.from), new Range(range.to, to)};
    }
}