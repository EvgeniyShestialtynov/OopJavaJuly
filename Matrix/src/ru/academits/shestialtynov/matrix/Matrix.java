package ru.academits.shestialtynov.matrix;

import ru.academits.shestialtynov.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] rows;

    public Matrix(int rowsCount, int columnsCount) {
        if (rowsCount < 1) {
            throw new IllegalArgumentException("Количество строк не может быть меньше 1, переданное количество строк:" + rowsCount);
        }

        if (columnsCount < 1) {
            throw new IllegalArgumentException("Количество столбцов не может быть меньше 1, переданное количество столбцов:" + columnsCount);
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount);
        }
    }

    public Matrix(Matrix matrix) {
        rows = new Vector[matrix.getRowsCount()];

        for (int i = 0; i < matrix.rows.length; i++) {
            rows[i] = new Vector(matrix.rows[i]);
        }
    }

    public Matrix(double[][] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Размер матрицы не может быть меньше чем 1 х 1, передан массив размером 0");
        }

        int rowsCount = array.length;
        int columnsCount = 0;

        for (double[] row : array) {
            columnsCount = Math.max(columnsCount, row.length);
        }

        if (columnsCount == 0) {
            throw new IllegalArgumentException("Размер матрицы не может быть меньще чем 1 х 1, передан массив размером 0");
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            rows[i] = new Vector(columnsCount, array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        int rowsCount = vectors.length;
        int columnsCount = 0;

        for (Vector vector : vectors) {
            columnsCount = Math.max(columnsCount, vector.getSize());
        }

        rows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            int vectorSize = vectors[i].getSize();

            if (vectorSize == columnsCount) {
                rows[i] = new Vector(vectors[i]);
            } else {
                rows[i] = new Vector(columnsCount);

                for (int j = 0; j < vectorSize; j++) {
                    rows[i].setComponent(j, vectors[i].getComponent(j));
                }
            }
        }
    }

    public int getRowsCount() {
        return rows.length;
    }

    public int getColumnsCount() {
        return rows[0].getSize();
    }

    public Vector getRow(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException("Строка с индексом " + rowIndex + " не существует! Допустимый диапазон от 0 до " + (rows.length - 1));
        }

        return new Vector(rows[rowIndex]);
    }

    public void setRow(Vector vector, int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rows.length) {
            throw new IndexOutOfBoundsException("Строка с индексом " + rowIndex + " не существует! Допустимый диапазон от 0 до " + (rows.length - 1));
        }

        if (vector.getSize() != getColumnsCount()) {
            throw new IllegalArgumentException("Размер вектора должен быть - " + getColumnsCount() + ". Размер переданного вектора - " + vector.getSize());
        }

        rows[rowIndex] = new Vector(vector);
    }

    public Vector getColumn(int columnIndex) {
        int columnSize = rows.length;

        if (columnIndex < 0 || columnIndex >= getColumnsCount()) {
            throw new IndexOutOfBoundsException("Столбец с индексом " + columnIndex + " не существует! Допустимый диапазон от 0 до " + (columnSize - 1));
        }

        Vector column = new Vector(columnSize);

        for (int i = 0; i < columnSize; i++) {
            column.setComponent(i, rows[i].getComponent(columnIndex));
        }

        return column;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector row : rows) {
            stringBuilder.append(row).append(", ");
        }

        int stringLength = stringBuilder.length();
        stringBuilder.delete(stringLength - 2, stringLength).append('}');

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        return Arrays.equals(rows, ((Matrix) o).rows);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Arrays.hashCode(rows);

        return hash;
    }

    public void transpose() {
        int columnsCount = getColumnsCount();

        Vector[] transposedMatrix = new Vector[columnsCount];

        for (int i = 0; i < columnsCount; i++) {
            transposedMatrix[i] = getColumn(i);
        }

        rows = transposedMatrix;
    }

    public void multiplyByScalar(double scalar) {
        for (Vector row : rows) {
            row.multiplyByScalar(scalar);
        }
    }

    public double getDeterminant() {
        int rowsCount = rows.length;

        if (rowsCount != getColumnsCount()) {
            throw new UnsupportedOperationException("Метод применяется к квадратным матрицам. Размер матрицы - " + rows.length + " x " + getColumnsCount());
        }

        if (rowsCount == 1) {
            return rows[0].getComponent(0);
        }

        if (rowsCount == 2) {
            return rows[0].getComponent(0) * rows[1].getComponent(1) - rows[1].getComponent(0) * rows[0].getComponent(1);
        }

        double determinant = 1;

        Vector[] triangleMatrixRows = new Vector[rowsCount];

        for (int i = 0; i < rowsCount; i++) {
            triangleMatrixRows[i] = new Vector(rows[i]);
        }

        for (int i = 0; i < rowsCount; i++) {
            for (int j = i + 1; j < rowsCount; j++) {
                if (triangleMatrixRows[j].getComponent(i) != 0) {
                    Vector subtractableVector = new Vector(triangleMatrixRows[i]);
                    subtractableVector.multiplyByScalar(triangleMatrixRows[j].getComponent(i) / triangleMatrixRows[i].getComponent(i));
                    triangleMatrixRows[j].subtract(subtractableVector);
                }
            }

            determinant *= triangleMatrixRows[i].getComponent(i);
        }

        return determinant;
    }

    public Vector multiplyByVectorColumn(Vector vector) {
        if (getColumnsCount() != vector.getSize()) {
            throw new IllegalArgumentException("Размер вектора должен быть - " + getColumnsCount() + ", размер переданного вектора - " + vector.getSize());
        }

        Vector row = new Vector(rows.length);

        for (int i = 0; i < rows.length; i++) {
            row.setComponent(i, Vector.getScalarProduct(rows[i], vector));
        }

        return row;
    }

    public void add(Matrix matrix) {
        if (rows.length != matrix.rows.length || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одного размера. Переданные матрицы имеют размеры: матрица 1 - " + rows.length + " х " + getColumnsCount() +
                    ", матрица 2 - " + matrix.rows.length + " х " + matrix.getColumnsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].add(matrix.rows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        if (rows.length != matrix.rows.length || getColumnsCount() != matrix.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одного размера. Переданные матрицы имеют размеры: матрица 1 - " + rows.length + " х " + getColumnsCount() +
                    ", матрица 2 - " + matrix.rows.length + " х " + matrix.getColumnsCount());
        }

        for (int i = 0; i < rows.length; i++) {
            rows[i].subtract(matrix.rows[i]);
        }
    }

    public static Matrix getSum(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.rows.length || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одного размера. Переданные матрицы имеют размеры: матрица  - " + matrix1.rows.length + " х " + matrix1.getColumnsCount() +
                    ", матрица 2 - " + matrix2.rows.length + " х " + matrix2.getColumnsCount());
        }

        Matrix sumMatrix = new Matrix(matrix1);

        sumMatrix.add(matrix2);

        return sumMatrix;
    }

    public static Matrix getDifference(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.rows.length || matrix1.getColumnsCount() != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Матрицы должны быть одного размера. Переданные матрицы имеют размеры: матрица 1 - " + matrix1.rows.length + " х " + matrix1.getColumnsCount() +
                    ", матрица 2 - " + matrix2.rows.length + " х " + matrix2.getColumnsCount());
        }

        Matrix differenceMatrix = new Matrix(matrix1);

        differenceMatrix.subtract(matrix2);

        return differenceMatrix;
    }

    public static Matrix getProduct(Matrix matrix1, Matrix matrix2) {
        if (matrix1.rows.length != matrix2.getColumnsCount()) {
            throw new IllegalArgumentException("Число строк матрицы 1 должно равняться числу столбцов матрицы 2. " +
                    "Число строк матрицы 1 = " + matrix1.rows.length + ", число столбцов матрицы 2 = " + matrix2.getColumnsCount());
        }

        int rowsCount = matrix1.rows.length;
        int columnsCount = matrix2.getColumnsCount();
        Matrix productMatrix = new Matrix(rowsCount, columnsCount);

        for (int i = 0; i < columnsCount; i++) {
            Vector column = matrix2.getColumn(i);

            for (int j = 0; j < rowsCount; j++) {
                productMatrix.rows[j].setComponent(i, Vector.getScalarProduct(matrix1.rows[j], column));
            }
        }

        return productMatrix;
    }
}