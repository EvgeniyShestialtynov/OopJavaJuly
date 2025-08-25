package ru.academits.shestialtynov.range_main;

import ru.academits.shestialtynov.range.Range;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(4.7, 8.8);
        Range range2 = new Range(7.3, 9.2);

        System.out.println("Создан диапазон 1: " + range1 + ", длиной " + range1.getLength());
        System.out.println("Создан диапазон 2: " + range2 + ", длиной " + range2.getLength());

        double number = 5;

        if (range1.isInside(number)) {
            System.out.println("Число " + number + " входит в диапазон " + range1);
        } else {
            System.out.println("Число " + number + " не входит в диапазон " + range1);
        }

        Range intersection = range1.getIntersection(range2);

        if (intersection == null) {
            System.out.println("Пересечения диапазонов " + range1 + " и " + range2 + " нет");
        } else {
            System.out.println("Пересечение диапазонов " + range1 + " и " + range2 + " = " + intersection);
        }

        Range[] union = range1.getUnion(range2);
        System.out.println("Объединение диапазонов " + range1 + " и " + range2 + ":");

        for (Range range : union) {
            System.out.println(range);
        }

        range2.setFrom(3.6);
        range2.setTo(5.3);
        System.out.println("Диапазон 2 изменен: " + range2);

        Range[] difference = range1.getDifference(range2);
        System.out.println("Разность диапазонов " + range1 + " и " + range2 + ":");

        if (difference.length == 0) {
            System.out.println("Пустая разность");
        } else {
            for (Range range : difference) {
                System.out.println(range);
            }
        }
    }
}