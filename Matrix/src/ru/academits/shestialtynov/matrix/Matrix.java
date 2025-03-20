package ru.academits.shestialtynov.matrix;

import ru.academits.shestialtynov.vector.Vector;

import java.util.Arrays;

public class Matrix {
    private Vector[] matrixRows;

    public Matrix(int rows, int columns) {
        if (rows < 1) {
            rows = 1;
        }

        if (columns < 1) {
            columns = 1;
        }

        matrixRows = new Vector[rows];

        for (int i = 0; i < rows; i++) {
            matrixRows[i] = new Vector(columns);
        }
    }

    public Matrix(Matrix matrix) {
        matrixRows = Arrays.copyOf(matrix.matrixRows, matrix.matrixRows.length);
    }

    public Matrix(double[][] array) {
        int rows = array.length;
        int columns = 0;

        for (double[] doubles : array) {
            columns = Math.max(columns, doubles.length);
        }

        matrixRows = new Vector[rows];

        for (int i = 0; i < rows; i++) {
            matrixRows[i] = new Vector(columns, array[i]);
        }
    }

    public Matrix(Vector[] vectors) {
        int rows = vectors.length;
        int columns = 0;

        for (Vector vector : vectors) {
            columns = Math.max(columns, vector.getSize());
        }

        matrixRows = new Vector[rows];

        for (int i = 0; i < rows; i++) {
            int vectorSize = vectors[i].getSize();

            if (vectorSize == columns) {
                matrixRows[i] = new Vector(vectors[i]);
            } else {
                matrixRows[i] = new Vector(columns);

                for (int j = 0; j < vectorSize; j++) {
                    matrixRows[i].setComponent(j, vectors[i].getComponent(j));
                }
            }
        }
    }

    public int getRowsCount() {
        return matrixRows.length;
    }

    public int getColumnsCount() {
        return matrixRows[0].getSize();
    }

    public Vector getRow(int rowNumber) {
        if (rowNumber <= 0 || rowNumber > matrixRows.length - 1) {
            throw new IndexOutOfBoundsException("Строки с индексом " + rowNumber + " не существует! Допустимый диапазон от 0 до " + (matrixRows.length - 1));
        }

        return matrixRows[rowNumber];
    }

    public void setRow(Vector vector, int rowNumber) {
        if (vector.getSize() != matrixRows[0].getSize()) {
            throw new IllegalArgumentException("Размер вектора должен быть - " + matrixRows[0].getSize() + ". Размер переданного вектора - " + vector.getSize());
        }

        if (rowNumber <= 0 || rowNumber > matrixRows.length - 1) {
            throw new IndexOutOfBoundsException("Строки с индексом " + rowNumber + " не существует! Допустимый диапазон от 0 до " + (matrixRows.length - 1));
        }
        matrixRows[rowNumber] = vector;
    }

    public Vector getColumn(int columnNumber) {
        int columnSize = matrixRows.length;

        if (columnNumber <= 0 || columnNumber > matrixRows[0].getSize() - 1) {
            throw new IndexOutOfBoundsException("Колонки с индексом " + columnNumber + " не существует! Допустимый диапазон от 0 до " + (columnSize - 1));
        }

        Vector column = new Vector(columnSize);

        for (int i = 0; i < columnSize; i++) {
            column.setComponent(i, matrixRows[i].getComponent(columnNumber));
        }

        return column;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("{");

        for (Vector matrixRow : matrixRows) {
            stringBuilder.append(matrixRow).append(", ");
        }

        int stringLength = stringBuilder.length();
        stringBuilder.delete(stringLength - 2, stringLength).append('}');

        return stringBuilder.toString();
    }

    public void transpose() {
        int columns = matrixRows[0].getSize();

        Vector[] matrixTranspose = new Vector[matrixRows[0].getSize()];

        for (int i = 0; i < columns; i++) {
            matrixTranspose[i] = getColumn(i);
        }

        matrixRows = matrixTranspose;
    }

    public void multiplyByScalar(double scalar) {
        for (Vector matrixRow : matrixRows) {
            matrixRow.multiplyByScalar(scalar);
        }
    }

    public double getDeterminant() {
        int rowsCount = matrixRows.length;

        if (rowsCount != matrixRows[0].getSize()) {
            throw new IllegalArgumentException("Матрица должна быть квадратной. Передана матрица размером " + matrixRows.length + " x " + matrixRows[0].getSize());
        }

        if (rowsCount == 2) {
            return matrixRows[0].getComponent(0) * matrixRows[1].getComponent(1) - matrixRows[1].getComponent(0) * matrixRows[0].getComponent(1);
        }

        if (rowsCount == 3) {
            double determinant = 0;
            int columnsCount = matrixRows[0].getSize();

            for (int i = 0; i < rowsCount; i++) {
                double product = 1;

                for (int j = 0; j < columnsCount; j++) {
                    product *= matrixRows[(i + j) % columnsCount].getComponent(j);

                    System.out.println(matrixRows[(i + j) % columnsCount].getComponent(j));
                }

                determinant += product;
                System.out.println();

                product = 1;

                for (int j = (columnsCount - 1), k = 0; j >= 0; j--, k++) {
                    product *= matrixRows[(i + k) % columnsCount].getComponent(j);

                    System.out.println(matrixRows[(i + k) % columnsCount].getComponent(j));
                }

                determinant -= product;
                System.out.println();
            }

            return determinant;
        }

        double determinant = 1;

        Vector[] triangleMatrixRows = Arrays.copyOf(matrixRows, rowsCount);

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

    public void multiplyByVectorColumn(Vector vector) {
        if (matrixRows[0].getSize() != vector.getSize()) {
            throw new IllegalArgumentException("Размер вектора должен быть - " + matrixRows[0].getSize() + ", размер переданного вектора - " + vector.getSize());
        }

        for (int i = 0; i < matrixRows.length; i++) {
            double[] matrixRow = {Vector.getScalarProduct(matrixRows[i], vector)};
            matrixRows[i] = new Vector(matrixRow);
        }
    }

    public void multiplyByVectorRow(Vector vector) {
        if (matrixRows[0].getSize() != 1) {
            throw new IllegalArgumentException("Матрица умножаемая на вектор-строку должна быть размером в 1 колонку." +
                    " Количество колонок в матрице = " + matrixRows[0].getSize());
        }

        if (matrixRows.length != vector.getSize()) {
            throw new IllegalArgumentException("Размер вектора должен быть - " + matrixRows.length + ", размер переданного вектора - " + vector.getSize());
        }

        for (int i = 0; i < matrixRows.length; i++) {
            Vector matrixRow = new Vector(vector);
            matrixRow.multiplyByScalar(matrixRows[i].getComponent(0));

            matrixRows[i] = matrixRow;
        }
    }

    public void add(Matrix matrix) {
        if (matrixRows.length != matrix.matrixRows.length || matrixRows[0].getSize() != matrix.matrixRows[0].getSize()) {
            throw new IllegalArgumentException("В прибавляемой матрице должно быть строк - " + matrixRows.length + ", колонок - " + matrixRows[0].getSize() +
                    ". Количество строк в переданной матрице - " + matrix.matrixRows.length + ", колонок - " + matrix.matrixRows[0].getSize());
        }

        for (int i = 0; i < matrixRows.length; i++) {
            matrixRows[i].add(matrix.matrixRows[i]);
        }
    }

    public void subtract(Matrix matrix) {
        if (matrixRows.length != matrix.matrixRows.length || matrixRows[0].getSize() != matrix.matrixRows[0].getSize()) {
            throw new IllegalArgumentException("В вычитаемой матрице должно быть строк - " + matrixRows.length + ", колонок - " + matrixRows[0].getSize() +
                    ". Количество строк в переданной матрице - " + matrix.matrixRows.length + ", колонок - " + matrix.matrixRows[0].getSize());
        }

        for (int i = 0; i < matrixRows.length; i++) {
            matrixRows[i].subtract(matrix.matrixRows[i]);
        }
    }

    public static Matrix getSum(Matrix matrixA, Matrix matrixB) {
        if (matrixA.matrixRows.length != matrixB.matrixRows.length || matrixA.matrixRows[0].getSize() != matrixB.matrixRows[0].getSize()) {
            throw new IllegalArgumentException("Матрицы должны быть одного размера. Переданные матрицы имеют размеры: " +
                    "матрица A - " + matrixA.matrixRows.length + " х " + matrixA.matrixRows[0].getSize() + ", матрица B - " + matrixB.matrixRows.length + " х " + matrixB.matrixRows[0].getSize());
        }

        Matrix matrixSum = new Matrix(matrixA);

        matrixSum.add(matrixB);

        return matrixSum;
    }

    public static Matrix getDifference(Matrix matrixA, Matrix matrixB) {
        if (matrixA.matrixRows.length != matrixB.matrixRows.length || matrixA.matrixRows[0].getSize() != matrixB.matrixRows[0].getSize()) {
            throw new IllegalArgumentException("Матрицы должны быть одного размера. Переданные матрицы имеют размеры: " +
                    "матрица A - " + matrixA.matrixRows.length + " х " + matrixA.matrixRows[0].getSize() + ", матрица B - " + matrixB.matrixRows.length + " х " + matrixB.matrixRows[0].getSize());
        }

        Matrix matrixDifference = new Matrix(matrixA);

        matrixDifference.subtract(matrixB);

        return matrixDifference;
    }

    public static Matrix multiply(Matrix matrixA, Matrix matrixB) {
        if (matrixA.matrixRows[0].getSize() != matrixB.matrixRows.length) {
            throw new IllegalArgumentException("Число столбцов матрицы A должно равняться числу строк матрицы B. " +
                    "Число столбцов матрицы A = " + matrixA.matrixRows[0].getSize() + ", число строк матрицы B = " + matrixB.matrixRows.length);
        }

        int matrixRowsCount = matrixA.matrixRows.length;
        int matrixColumnsCount = matrixB.matrixRows[0].getSize();
        Matrix matrixProduct = new Matrix(matrixRowsCount, matrixColumnsCount);

        for (int i = 0; i < matrixColumnsCount; i++) {
            Vector column = new Vector(matrixB.getColumn(i));

            for (int j = 0; j < matrixRowsCount; j++) {
                matrixProduct.matrixRows[j].setComponent(i, Vector.getScalarProduct(matrixA.matrixRows[j], column));
            }
        }

        return matrixProduct;
    }
}