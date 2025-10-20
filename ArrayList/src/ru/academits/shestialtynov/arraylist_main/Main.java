package ru.academits.shestialtynov.arraylist_main;

import ru.academits.shestialtynov.arraylist.ArrayList;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        System.out.println("Создан список 1: " + list1);

        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(4);
        list2.add(5);
        list2.add(6);
        list2.add(7);
        list2.add(8);
        System.out.println("Создан список 2: " + list2);

        int index = 5;
        list1.add(5, 7);
        System.out.println("В список 1 добавлен элемент по индексу " + index);
        System.out.println("Список 1: " + list1);

        index = 2;
        list1.addAll(2, list2);
        System.out.println("В список 1, с индекса " + index + ", добавлены элементы списка 2: " + list1);

        list1.removeAll(list2);
        System.out.println("Из списка 1 удалены элементы встречающиеся в списке 2");
        System.out.println("Список 1: " + list1);

        Integer[] array = new Integer[]{6, 5, 4, 3, 2, 1};
        list1.toArray(array);
        System.out.println("Элементы списка 1 добавлены в массив");
        System.out.println("Массив: " + Arrays.toString(array));

        Integer element = 3;

        if (list1.contains(element)) {
            System.out.println("Список 1 содержит элемент " + element);
        } else {
            System.out.println("Список 1 не содержит элемент " + element);
        }

        index = list1.indexOf(element);

        if (index >= 0) {
            System.out.println("Элемент " + element + " находится в списке 1 по индексу " + index);
        } else {
            System.out.println("Элемент " + element + " в списке 1 отсутствует");
        }

        if (list1.remove(element)) {
            System.out.println("Из списка 1 удален элемент " + element);
            System.out.println(list1);
        }

        index = 1;
        System.out.println("Из списка 1 удален элемент " + list1.remove(index) + " по индексу " + index);
        System.out.println("Список 1: " + list1);

        if (list1.retainAll(list2)) {
            System.out.println("Из списка 1 удалены элементы не встречающиеся в списке 2");
            System.out.println("Список 1: " + list1);
        } else {
            System.out.println("В списке 1 нет элементов не встречающихся в списке 2");
        }
    }
}