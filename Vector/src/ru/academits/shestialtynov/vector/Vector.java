package ru.academits.shestialtynov.vector;

import java.util.Arrays;

public class Vector {

    private double[] numbersArray;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1, Вы указали " + size);
        }

        numbersArray = new double[size];
    }

    public Vector(Vector vector) {
        numbersArray = Arrays.copyOf(vector.numbersArray, vector.numbersArray.length);
    }

    public Vector(double[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1");
        }

        numbersArray = Arrays.copyOf(array, array.length);
    }

    public Vector(int size, double[] array) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1, Вы указали " + size);
        }

        numbersArray = Arrays.copyOf(array, size);
    }

    public int getSize() {
        return numbersArray.length;
    }

    public double getComponent(int index) {
        if (index < 0 || index >= numbersArray.length) {
            throw new IllegalArgumentException("Элемента с индексом " + index + " в векторе нет!");
        }

        return numbersArray[index];
    }

    public void setComponent(int index, double componentValue) {
        if (index < 0 || index >= numbersArray.length) {
            throw new IllegalArgumentException("Элемента с индексом " + index + " в векторе нет!");
        }

        numbersArray[index] = componentValue;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null || object.getClass() != getClass()) {
            return false;
        }

        return Arrays.equals(numbersArray, ((Vector) object).numbersArray);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + numbersArray.length;
        hash = prime * hash + Arrays.hashCode(numbersArray);

        return hash;
    }

    @Override
    public String toString() {

        return Arrays.toString(numbersArray);
    }

    public void add(Vector vector) {
        int vectorLength = vector.numbersArray.length;

        if (numbersArray.length < vectorLength) {
            numbersArray = Arrays.copyOf(numbersArray, vectorLength);
        }

        for (int i = 0; i < vector.numbersArray.length; i++) {
            numbersArray[i] = numbersArray[i] + vector.numbersArray[i];
        }
    }

    public void subtract(Vector vector) {
        int vectorLength = vector.numbersArray.length;

        if (numbersArray.length < vectorLength) {
            numbersArray = Arrays.copyOf(numbersArray, vectorLength);
        }

        for (int i = 0; i < vector.numbersArray.length; i++) {
            numbersArray[i] = numbersArray[i] - vector.numbersArray[i];
        }
    }

    public void multiplyByScalar(double scalar) {
        int size = numbersArray.length;

        for (int i = 0; i < size; i++) {
            numbersArray[i] = numbersArray[i] * scalar;
        }
    }

    public void turn() {
        multiplyByScalar(-1);
    }

    public double getVectorLength() {
        double vectorLength = 0;

        for (double v : numbersArray) {
            vectorLength += Math.pow(v, 2);
        }

        return Math.sqrt(vectorLength);
    }

    public static Vector getSum(Vector vector1, Vector vector2) {
        Vector vectorsSum = new Vector(vector1);
        vectorsSum.add(vector2);

        return vectorsSum;
    }

    public static Vector getDifference(Vector vector1, Vector vector2) {
        Vector vectorsDifference = new Vector(vector1);
        vectorsDifference.subtract(vector2);

        return vectorsDifference;
    }

    public static double getScalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0;
        int vector1Length = vector1.numbersArray.length;
        int vector2Length = vector2.numbersArray.length;

        if (vector1Length > vector2Length) {
            vector2.numbersArray = Arrays.copyOf(vector2.numbersArray, vector1Length);
        }

        if (vector1Length < vector2Length) {
            vector1.numbersArray = Arrays.copyOf(vector1.numbersArray, vector2Length);
        }

        for (int i = 0; i < vector1Length; i++) {
            scalarProduct += vector1.numbersArray[i] * vector2.numbersArray[i];
        }

        return scalarProduct;
    }
}