package ru.academits.shestialtynov.range_main;

import ru.academits.shestialtynov.range.Range;

public class Main {
    public static void main(String[] args) {
        Range range1 = new Range(2.8, 6.5);
        Range range2 = new Range(4.5, 7.8);

        System.out.println("Длина диапазона " + range1.getFrom() + " - " + range1.getTo() + " = " + range1.getLength());
        System.out.println("Длина диапазона " + range2.getFrom() + " - " + range2.getTo() + " = " + range2.getLength());

        double number = 3;

        if (range1.isInside(number)) {
            System.out.println("Число " + number + " входит в диапазон " + range1.getFrom() + " - " + range1.getTo());
        } else {
            System.out.println("Число " + number + " не входит в диапазон " + range1.getFrom() + " - " + range1.getTo());
        }

        Range range3 = range1.getRangesCrossing(range2);

        if (range3 == null) {
            System.out.println("Диапазоны " + range1.getFrom() + " - " + range1.getTo() + " и " + range2.getFrom() + " - " + range2.getTo() + " не пересекаются");
        } else {
            System.out.println("Пересечение диапазонов от " + range3.getFrom() + " до " + range3.getTo());
        }

        Range[] rangesCombining = range1.getRangesCombining(range2);
        System.out.println("Объединение диапазонов " + range1.getFrom() + " - " + range1.getTo() + " и " + range2.getFrom() + " - " + range2.getTo() + ":");

        for (Range e : rangesCombining) {
            System.out.println(e.getFrom() + " - " + e.getTo());
        }

        range2.setFrom(3.6);
        range2.setTo(5.3);

        Range[] rangesDifference = range1.getRangesDifference(range2);
        System.out.println("Разность диапазонов " + range1.getFrom() + " - " + range1.getTo() + " и " + range2.getFrom() + " - " + range2.getTo() + ":");

        if (rangesDifference == null) {
            System.out.println("0");
        } else {
            for (Range e : rangesDifference) {
                System.out.println(e.getFrom() + " - " + e.getTo());
            }
        }
    }
}