package ru.academits.shestialtynov.vector;

import java.util.Arrays;

public class Vector {
    private final int size;
    private final double[] vector;

    public Vector(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1");
        }

        this.size = size;
        this.vector = new double[size];
    }

    public Vector(Vector vector) {
        size = vector.getSize();
        this.vector = new double[size];

        for (int i = 0; i < size; i++) {
            this.vector[i] = vector.getComponent(i);
        }
    }

    public Vector(double[] array) {
        this.size = array.length;
        this.vector = new double[size];

        System.arraycopy(array, 0, vector, 0, array.length);
    }

    public Vector(int size, double[] array) {
        if (size <= 0) {
            throw new IllegalArgumentException("Размер вектора не может быть меньше 1");
        }

        this.size = size;
        this.vector = new double[size];

        System.arraycopy(array, 0, vector, 0, array.length);
    }

    public double[] getVector() {
        return vector;
    }

    public int getSize() {
        return size;
    }

    public double getComponent(int componentNumber) {
        return vector[componentNumber];
    }

    public void setComponent(int componentNumber, double componentValue) {
        this.vector[componentNumber] = componentValue;
    }

    @Override
    public boolean equals(Object vector) {
        if (vector == this) {
            return true;
        }

        if (vector == null || vector.getClass() != getClass()) {
            return false;
        }

        if (size != ((Vector) vector).getSize()) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (this.vector[i] != ((Vector) vector).getComponent(i)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        hash = prime * hash + size;
        hash = prime * hash + Arrays.hashCode(vector);
        return hash;
    }

    @Override
    public String toString() {
        return Arrays.toString(vector);
    }

    public double[] add(Vector vector) {
        int vectorSize = vector.getSize();
        double[] sum;

        if (size == vectorSize) {
            sum = new double[size];

            for (int i = 0; i < size; i++) {
                sum[i] = this.vector[i] + vector.getComponent(i);
            }

        } else {
            double[] maxVector = (size > vectorSize) ? this.vector : vector.getVector();
            double[] minVector = (size < vectorSize) ? this.vector : vector.getVector();

            int maxVectorSize = maxVector.length;

            sum = new double[maxVectorSize];

            int i = 0;

            for (; i < minVector.length; i++) {
                sum[i] = maxVector[i] + minVector[i];
            }

            for (; i < maxVectorSize; i++) {
                sum[i] = maxVector[i];
            }
        }

        return sum;
    }

    public double[] subtract(Vector vector) {
        int vectorSize = vector.getSize();
        double[] difference;

        if (size == vectorSize) {
            difference = new double[size];

            for (int i = 0; i < size; i++) {
                difference[i] = this.vector[i] - vector.getComponent(i);
            }

        } else {
            int maxSize = Math.max(size, vectorSize);
            Vector vector1 = new Vector(maxSize, this.vector);
            Vector vector2 = new Vector(maxSize, vector.getVector());

            difference = new double[maxSize];

            for (int i = 0; i < maxSize; i++) {
                difference[i] = vector1.getComponent(i) - vector2.getComponent(i);
            }
        }

        return difference;
    }

    public double[] multiplyByScalar(double scalar) {
        double[] multiplyByScalar = new double[size];

        for (int i = 0; i < size; i++) {
            multiplyByScalar[i] = vector[i] * scalar;
        }
        return multiplyByScalar;
    }

    public double[] turn() {
        double[] turn = new double[size];

        for (int i = 0; i < size; i++) {
            turn[i] = vector[i] * -1;
        }
        return turn;
    }

    public static Vector sum(Vector vector1, Vector vector2) {
        return new Vector(vector1.add(vector2));
    }

    public static Vector difference(Vector vector1, Vector vector2) {
        return new Vector(vector1.subtract(vector2));
    }

    public static double scalarProduct(Vector vector1, Vector vector2) {
        double scalarProduct = 0;

        int vector1Size = vector1.getSize();
        int vector2Size = vector2.getSize();

        if (vector1Size == vector2Size) {
            for (int i = 0; i < vector1Size; i++) {
                scalarProduct = scalarProduct + vector1.getComponent(i) * vector2.getComponent(i);
            }
        } else {
            double[] maxArray = (vector1Size > vector2Size) ? vector1.getVector() : vector2.getVector();
            double[] minArray = (vector1Size < vector2Size) ? vector1.getVector() : vector2.getVector();

            int i = 0;

            for (; i < minArray.length; i++) {
                scalarProduct = scalarProduct + maxArray[i] * minArray[i];
            }

            for (; i < maxArray.length; i++) {
                scalarProduct = scalarProduct + maxArray[i];
            }
        }

        return scalarProduct;
    }
}