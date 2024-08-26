package ru.academits.shestialtynov.shapes;

public class Triangle implements Shape {
    public double x1;
    public double y1;
    public double x2;
    public double y2;
    public double x3;
    public double y3;

    public Triangle(double x1, double y1, double x2, double y2, double x3, double y3) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
    }

    @Override
    public double getWidth() {
        return Math.max(Math.max(x1, x2), x3) - Math.min(Math.min(x1, x2), x3);
    }

    @Override
    public double getHeight() {
        return Math.max(Math.max(y1, y2), y3) - Math.min(Math.min(y1, y2), y3);
    }

    @Override
    public double getArea() {
        return 0.5 * Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
    }

    @Override
    public double getPerimeter() {
        double sideABLength = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double sideBCLength = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double sideACLength = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
        return (sideABLength + sideBCLength + sideACLength) / 2;
    }

    @Override
    public String toString() {
        return "Треугольник с координатами вершин:" + System.lineSeparator() +
                "x1 - " + x1 + ", y1 - " + y1 + System.lineSeparator() +
                "x2 - " + x2 + ", y2 - " + y2 + System.lineSeparator() +
                "x3 - " + x3 + ", y3 - " + y3;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        Triangle t = (Triangle) o;

        return x1 == t.x1 && y1 == t.y1 && x2 == t.x2 && y2 == t.y2 && x3 == t.x3 && y3 == t.y3;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;
        hash = prime * hash + Double.hashCode(x1);
        hash = prime * hash + Double.hashCode(y1);
        hash = prime * hash + Double.hashCode(x2);
        hash = prime * hash + Double.hashCode(y2);
        hash = prime * hash + Double.hashCode(x3);
        hash = prime * hash + Double.hashCode(y3);

        return hash;
    }
}