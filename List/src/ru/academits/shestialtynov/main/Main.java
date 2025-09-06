package ru.academits.shestialtynov.main;

import ru.academits.shestialtynov.list.List;

public class Main {
    public static void main(String[] args) {
        List <Integer> list = new List<>();
        list.addToTop(1);
        list.addToTop(2);
        list.addToTop(3);
        list.addToTop(4);
        list.addToTop(5);
        System.out.println("Создан список 1: " + list);
        System.out.println("Размер списка 1 = " + list.getSize());

        int index = 3;
        System.out.println("Значение в списке  по индексу " + index + ": " + list.getData(index));

        List<Integer> list2 = list.copy();
        System.out.println("Создан список 2, копированием списка 1: " + list2);
        System.out.println("Размер списка 2 = " + list2.getSize());

        if (list.equals(list2)) {
            System.out.println("Список 1 и список 2 эквивалентны");
        } else {
            System.out.println("Список 1 и список 2 не эквивалентны");
        }

        list.add(4,6);
        System.out.println("В список 1 добавлен элемент. " + list);
        System.out.println("Из списка 1 удален элемент со значением " + list.remove(0));
        System.out.println("Списк 1: " + list);

        int deletedData = 4;

        if (list2.remove((Integer) deletedData)) {
            System.out.println("Из списка 2 удален элемент со значением " + deletedData);
        } else {
            System.out.println("В списке 2 нет элемента со значением" + deletedData);
        }

        System.out.println("Список 2: " + list2);

        System.out.println("В списке 2 по индексу 2 значение " + list2.setData(2, 9) + " заменено на 9.");
        System.out.println("Список 2: " + list2);

        list2.turn();
        System.out.println("Список 2 развернут.");
        System.out.println("Список 2: " + list2);
    }
}