package ru.academits.shestialtynov.vector;

import java.util.Arrays;

public class Vector {
    private double[] vectorComponents;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1. Переданный размер = " + size);
        }

        vectorComponents = new double[size];
    }

    public Vector(Vector vector) {
        vectorComponents = Arrays.copyOf(vector.vectorComponents, vector.vectorComponents.length);
    }

    public Vector(double[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1, Переданный массив имеет размер = 0");
        }

        vectorComponents = Arrays.copyOf(array, array.length);
    }

    public Vector(int size, double[] array) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1. Переданный размер = " + size);
        }

        vectorComponents = Arrays.copyOf(array, size);
    }

    public int getSize() {
        return vectorComponents.length;
    }

    public double getComponent(int index) {
        int vectorSize = vectorComponents.length;
        if (index < 0 || index >= vectorSize) {
            throw new IndexOutOfBoundsException("Элемента с индексом " + index + " в векторе нет! Допустимый диапазон от 0 до " + (vectorSize - 1));
        }

        return vectorComponents[index];
    }

    public void setComponent(int index, double componentValue) {
        int vectorSize = vectorComponents.length;
        if (index < 0 || index >= vectorSize) {
            throw new IndexOutOfBoundsException("Элемента с индексом " + index + " в векторе нет! Допустимый диапазон от 0 до " + (vectorSize - 1));
        }

        vectorComponents[index] = componentValue;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null || object.getClass() != getClass()) {
            return false;
        }

        return Arrays.equals(vectorComponents, ((Vector) object).vectorComponents);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Arrays.hashCode(vectorComponents);

        return hash;
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder("{");
        int vectorComponentsCount = vectorComponents.length - 1;

        for (int i = 0; i < vectorComponentsCount; i++) {
            string.append(vectorComponents[i]).append(", ");
        }

        string.append(vectorComponents[vectorComponentsCount]).append("}");

        return string.toString();
    }

    public void add(Vector vector) {
        int vectorSize = vector.vectorComponents.length;

        if (vectorComponents.length < vectorSize) {
            vectorComponents = Arrays.copyOf(vectorComponents, vectorSize);
        }

        for (int i = 0; i < vector.vectorComponents.length; i++) {
            vectorComponents[i] += vector.vectorComponents[i];
        }
    }

    public void subtract(Vector vector) {
        int vectorSize = vector.vectorComponents.length;

        if (vectorComponents.length < vectorSize) {
            vectorComponents = Arrays.copyOf(vectorComponents, vectorSize);
        }

        for (int i = 0; i < vector.vectorComponents.length; i++) {
            vectorComponents[i] -= vector.vectorComponents[i];
        }
    }

    public void multiplyByScalar(double scalar) {
        int size = vectorComponents.length;

        for (int i = 0; i < size; i++) {
            vectorComponents[i] *= scalar;
        }
    }

    public void turn() {
        multiplyByScalar(-1);
    }

    public double getLength() {
        double squaredComponentsSum = 0;

        for (double v : vectorComponents) {
            squaredComponentsSum += (v * v);
        }

        return Math.sqrt(squaredComponentsSum);
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
        int vectorSize = Math.max(vector1.vectorComponents.length, vector2.vectorComponents.length);

        double[] vector1Copy = Arrays.copyOf(vector1.vectorComponents, vectorSize);
        double[] vector2Copy = Arrays.copyOf(vector2.vectorComponents, vectorSize);

        double scalarProduct = 0;

        for (int i = 0; i < vectorSize; i++) {
            scalarProduct += vector1Copy[i] * vector2Copy[i];
        }

        return scalarProduct;
    }
}