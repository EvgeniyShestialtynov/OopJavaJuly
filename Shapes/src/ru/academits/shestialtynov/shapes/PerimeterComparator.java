package ru.academits.shestialtynov.shapes;

import java.util.Comparator;

public class PerimeterComparator implements Comparator<Shape> {
    @Override
    public int compare (Shape o1, Shape o2) {
        return (int) (o1.getPerimeter() - o2.getPerimeter());
    }
}
