package ru.academits.shestialtynov.matrix_main;

import ru.academits.shestialtynov.matrix.Matrix;
import ru.academits.shestialtynov.vector.Vector;

public class Main {
    public static void main(String[] args) {
        double[][] array1 = {{2.0, 4.0, 1.0, 1.0}, {0.0, 2.0, 1.0, 0.0}, {2.0, 1.0, 1.0, 3.0}, {4.0, 0.0, 2.0, 3.0}};
        Matrix matrix1 = new Matrix(array1);
        System.out.println("Создана матрица 1 размером " + matrix1.getRowsCount() + " х " + matrix1.getColumnsCount());
        System.out.println(matrix1);

        double determinant = matrix1.getDeterminant();
        System.out.println("Детерминант матрицы 1 = " + determinant);

        matrix1.transpose();

        System.out.println("Матрица 1 транспонирована:");
        System.out.println(matrix1);

        Matrix matrix2 = new Matrix(matrix1);
        System.out.println("Создана матрица 2 копированием матрицы 1");
        System.out.println(matrix2);

        double scalar = 3;

        matrix2.multiplyByScalar(scalar);
        System.out.println("Матрица 2 изменена путем умножения на скаляр " + scalar);
        System.out.println(matrix2);

        Matrix sumMatrix = Matrix.getSum(matrix1, matrix2);
        System.out.println("Сумма матриц 1 и 2:");
        System.out.println(sumMatrix);

        Matrix differenceMatrix = Matrix.getDifference(matrix1, matrix2);
        System.out.println("Разность матриц 1 и 2:");
        System.out.println(differenceMatrix);

        Vector column = matrix1.getColumn(2);
        System.out.println("Из матрицы 1 взят столбец 3:");
        System.out.println(column);

        Vector vectorColumn = matrix2.multiplyByVectorColumn(column);
        System.out.println("Матрица 2 умножена на столбец 3 матрицы 1:");
        System.out.println(vectorColumn);

        double[][] array2 = {{2, 4, 6}, {5, 6, 8}, {5, 3, 6}, {6, 0, 0}};
        Matrix matrix3 = new Matrix(array2);
        System.out.println("Создана матрица 3 размером " + matrix3.getRowsCount() + " х " + matrix3.getColumnsCount());
        System.out.println(matrix3);

        Matrix productMatrix = Matrix.getProduct(matrix1, matrix3);
        System.out.println("Произведение матриц 1 и 3");
        System.out.println(productMatrix);
    }
}