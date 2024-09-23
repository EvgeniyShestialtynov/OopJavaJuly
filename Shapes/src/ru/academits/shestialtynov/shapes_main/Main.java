package ru.academits.shestialtynov.shapes_main;

import ru.academits.shestialtynov.shapes_comparator.ShapeAreaComparator;
import ru.academits.shestialtynov.shapes_comparator.ShapePerimeterComparator;
import ru.academits.shestialtynov.shapes.*;

import java.util.Arrays;

public class Main {
    public static Shape getMaxAreaShape(Shape[] shapes) {
        Arrays.sort(shapes, new ShapeAreaComparator());

        return shapes[shapes.length - 1];
    }

    public static Shape getSecondPerimeterShape(Shape[] shapes) {
        Arrays.sort(shapes, new ShapePerimeterComparator());

        return shapes[shapes.length - 2];
    }

    public static void main(String[] args) {
        Shape[] shapes = {
                new Circle(1),
                new Rectangle(2, 3),
                new Square(3),
                new Triangle(1, 1, 2, 7, 6, 4),
                new Square(2),
                new Circle(7),
                new Rectangle(3, 2)
        };

        System.out.println("Фигура с самой большой площадью - " + getMaxAreaShape(shapes));
        System.out.println("Фигура со вторым по величине периметром - " + getSecondPerimeterShape(shapes));
    }
}
