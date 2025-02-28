package ru.academits.shestialtynov.vector;

import java.util.Arrays;

public class Vector {
    private double[] components;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1. Переданный размер = " + size);
        }

        components = new double[size];
    }

    public Vector(Vector vector) {
        components = Arrays.copyOf(vector.components, vector.components.length);
    }

    public Vector(double[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1, Переданный массив имеет размер = 0");
        }

        components = Arrays.copyOf(array, array.length);
    }

    public Vector(int size, double[] array) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1. Переданный размер = " + size);
        }

        components = Arrays.copyOf(array, size);
    }

    public int getSize() {
        return components.length;
    }

    public double getComponent(int index) {
        int vectorSize = components.length;

        if (index < 0 || index >= vectorSize) {
            throw new IndexOutOfBoundsException("Элемента с индексом " + index + " в векторе нет! Допустимый диапазон от 0 до " + (vectorSize - 1));
        }

        return components[index];
    }

    public void setComponent(int index, double component) {
        int vectorSize = components.length;

        if (index < 0 || index >= vectorSize) {
            throw new IndexOutOfBoundsException("Элемента с индексом " + index + " в векторе нет! Допустимый диапазон от 0 до " + (vectorSize - 1));
        }

        components[index] = component;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null || object.getClass() != getClass()) {
            return false;
        }

        return Arrays.equals(components, ((Vector) object).components);
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + Arrays.hashCode(components);

        return hash;
    }

    @Override
    public String toString() {
        StringBuilder componentsString = new StringBuilder("{");

        for (double component : components) {
            componentsString.append(component).append(", ");
        }

        int stringLength = componentsString.length();
        componentsString.delete(stringLength - 2, stringLength).append('}');

        return componentsString.toString();
    }

    public void add(Vector vector) {
        int vectorSize = vector.components.length;

        if (components.length < vectorSize) {
            components = Arrays.copyOf(components, vectorSize);
        }

        for (int i = 0; i < vectorSize; i++) {
            components[i] += vector.components[i];
        }
    }

    public void subtract(Vector vector) {
        int vectorSize = vector.components.length;

        if (components.length < vectorSize) {
            components = Arrays.copyOf(components, vectorSize);
        }

        for (int i = 0; i < vectorSize; i++) {
            components[i] -= vector.components[i];
        }
    }

    public void multiplyByScalar(double scalar) {
        int size = components.length;

        for (int i = 0; i < size; i++) {
            components[i] *= scalar;
        }
    }

    public void turn() {
        multiplyByScalar(-1);
    }

    public double getLength() {
        double squaredComponentsSum = 0;

        for (double v : components) {
            squaredComponentsSum += v * v;
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
        int vectorSize = Math.min(vector1.components.length, vector2.components.length);

        double scalarProduct = 0;

        for (int i = 0; i < vectorSize; i++) {
            scalarProduct += vector1.components[i] * vector2.components[i];
        }

        return scalarProduct;
    }
}