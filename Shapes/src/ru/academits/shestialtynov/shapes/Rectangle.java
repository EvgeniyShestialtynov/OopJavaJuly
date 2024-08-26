package ru.academits.shestialtynov.shapes;

public class Rectangle implements Shape {
    public double sideALength;
    public double sideBLength;

    public Rectangle(double sideALength, double sideBLength) {
        this.sideALength = sideALength;
        this.sideBLength = sideBLength;
    }

    @Override
    public double getWidth() {
        return sideBLength;
    }

    @Override
    public double getHeight() {
        return sideALength;
    }

    @Override
    public double getArea() {
        return sideALength * sideBLength;
    }

    @Override
    public double getPerimeter() {
        return (sideALength + sideBLength) * 2;
    }

    @Override
    public String toString() {
        return "Прямоугольник со сторонами: А = " + sideALength + ", B = " + sideBLength;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Rectangle r = (Rectangle) o;

        return sideALength == r.sideALength && sideBLength == r.sideBLength;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Double.hashCode(sideALength);
        hash = prime * hash + Double.hashCode(sideBLength);

        return hash;
    }
}