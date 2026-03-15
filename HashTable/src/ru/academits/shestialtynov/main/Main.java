package ru.academits.shestialtynov.main;

import ru.academits.shestialtynov.hashtable.HashTable;

public class Main {
    public static void main(String[] args) {
        HashTable<String> hashTable = new HashTable<>();
        System.out.println("Создана Хэш-таблица");

        String name1 = "Семен";
        String name2 = "Николай";
        String name3 = "Игорь";
        String name4 = "Ольга";
        String name5 = "Василий";
        String name6 = "Виктор";
        String name7 = "Пересвет";
        String name8 = "Василий";
        String name9 = "Александр";

        hashTable.add(name1);
        hashTable.add(name2);
        hashTable.add(name3);
        hashTable.add(name4);
        hashTable.add(name5);
        hashTable.add(name6);
        hashTable.add(name7);
        hashTable.add(name8);
        hashTable.add(name9);

        System.out.println("Хэш-таблица заполнена, содержит " + hashTable.size() + " элементов");
        System.out.println(hashTable);

        if (hashTable.contains(name1)) {
            System.out.println("Хэш-таблица содержит элемент \"" + name1 + "\"");
        } else {
            System.out.println("Хэш-таблица не содержит элемент \"" + name1 + "\"");
        }

        if (hashTable.remove(name2)) {
            System.out.println("Из хэш-таблицы удален \"" + name2 + "\"");
        } else {
            System.out.println("Хэш-таблица не содержит элемент \"" + name2 + "\"");
        }

        hashTable.clear();
        System.out.println("Хэш-таблица очищена");
        System.out.println(hashTable);
        }
    }