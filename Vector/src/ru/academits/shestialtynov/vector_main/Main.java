package ru.academits.shestialtynov.vector_main;

import ru.academits.shestialtynov.vector.Vector;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        double[] array1 = {1, 2, 3, 4};
        Vector vector1 = new Vector(6, array1);
        double[] array2 = {2, 3, 4, 5, 6};
        Vector vector2 = new Vector(array2);

        System.out.println("Созданы векторы 1 и 2:");
        System.out.println(vector1);
        System.out.println(vector2);

        System.out.println("Результат сложения векторов 1 и 2: " + Vector.getSum(vector1, vector2));

        System.out.println(vector1);
        System.out.println(vector2);

        System.out.println("Результат вычитания вектора 1 из вектора 2: " + Vector.getDifference(vector2, vector1));

        vector2.setComponent(1, 4);

        System.out.println("В векторе 2 компонента № 1 заменена на " + vector2.getComponent(1) + ", результат: " + vector2);

        vector2.multiplyByScalar(5);

        System.out.println("Результат умножения вектора 2 на скаляр 5: " + vector2);

        System.out.println(vector1);
        System.out.println(vector2);

        System.out.println("Скалярное произведение векторов 1 и 2: " + Vector.getScalarProduct(vector1, vector2));

        vector2.turn();

        System.out.println("Разворот вектора 2: " + vector2);

        System.out.println("Длина вектора 1: " + vector1.getVectorLength());
    }
}