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

    @Override
    public String toString() {
        return "[" + from + " - " + to + "]";
    }

    public double getLength() {
        return to - from;
    }

    public boolean isInside(double number) {
        return from <= number && number <= to;
    }

    public Range getIntersection(Range range) {
        if (isInside(range.from) || range.isInside(from)) {
            double rangesIntersectionFrom = Math.max(from, range.from);
            double rangesIntersectionTo = Math.min(to, range.to);

            if (rangesIntersectionFrom == rangesIntersectionTo) {
                return null;
            }

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

        return new Range[]{new Range(this.from, this.to), new Range(range.from, range.to)};
    }

    public Range[] getDifference(Range range) {

        if (range.from == from && range.to == to) {
            return new Range[0];
        }

        if (isInside(range.from) && range.from != to || range.isInside(from) && from != range.to) {
            double minFrom = Math.min(from, range.from);
            double maxFrom = Math.max(from, range.from);
            double minTo = Math.min(to, range.to);
            double maxTo = Math.max(to, range.to);

            if (minFrom == maxFrom) {
                return new Range[]{new Range(minTo, maxTo)};
            }

            if (minTo == maxTo) {
                return new Range[]{new Range(minFrom, maxFrom)};
            }

            return new Range[]{new Range(minFrom, maxFrom), new Range(minTo, maxTo)};
        }

        return new Range[]{new Range(this.from, this.to), new Range(range.from, range.to)};
    }
}