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
        if ((from <= range.from && range.from < to) || (range.from <= from && from < range.to)) {
            double rangesIntersectionFrom = Math.max(from, range.from);
            double rangesIntersectionTo = Math.min(to, range.to);

            return new Range(rangesIntersectionFrom, rangesIntersectionTo);
        }

        return null;
    }

    public Range[] getUnion(Range range) {
        if (isInside(range.from) || range.isInside(from)) {
            double minFrom = Math.min(from, range.from);
            double maxTo = Math.max(to, range.to);

            return new Range[]{new Range(minFrom, maxTo)};
        }

        return new Range[]{new Range(from, to), new Range(range.from, range.to)};
    }

    public Range[] getDifference(Range range) {

        if (from < range.to && range.from < to) {
            if (from < range.from) {
                if (range.to < to) {
                    return new Range[]{new Range(from, range.from), new Range(range.to, to)};
                }

                return new Range[]{new Range(from, range.from)};
            }

            if (range.to < to) {
                return new Range[]{new Range(range.to, to)};
            }

            return new Range[0];
        }

        return new Range[]{new Range(from, to)};
    }
}