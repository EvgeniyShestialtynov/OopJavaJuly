package ru.academits.shestialtynov.shapes_main;

import ru.academits.shestialtynov.shapes.*;

import java.util.Arrays;
import java.util.Comparator;

public class Main {
    public static Shape getLargestAreaShape(Shape[] shapes) {
        Comparator<Shape> comparator = Comparator.comparing(Shape::getArea);
        Arrays.sort(shapes, comparator);

        return shapes[shapes.length - 1];
    }

    public static Shape getSecondLargestPerimeterShape(Shape[] shapes) {
        Comparator<Shape> comparator = Comparator.comparing(Shape::getPerimeter);
        Arrays.sort(shapes, comparator);

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

        System.out.println("Фигура с самой большой площадью - " + getLargestAreaShape(shapes));
        System.out.println("Фигура со вторым по величине периметром - " + getSecondLargestPerimeterShape(shapes));
    }
}
