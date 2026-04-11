package ru.academits.shestialtynov.hashtable;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private final LinkedList<E>[] baskets;
    private int size;
    private int modCount;

    public HashTable() {
        //noinspection unchecked
        baskets = (LinkedList<E>[]) new LinkedList[10];
    }

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимость хэш-таблицы не может быть меньше 1, передана вместимость " + capacity);
        }

        //noinspection unchecked
        baskets = (LinkedList<E>[]) new LinkedList[capacity];
    }

    private int getIndex(Object object) {
        return Math.abs(object.hashCode() % baskets.length);
    }

    @Override
    public boolean add(E element) {

        if (element == null) {
            return false;
        }

        int index = getIndex(element);

        if (baskets[index] == null) {
            baskets[index] = new LinkedList<>();
        }

        baskets[index].add(element);
        size++;
        modCount++;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        if (collection.isEmpty()) {
            return false;
        }

        for (E element : collection) {
            add(element);
        }

        return true;
    }

    @Override
    public boolean remove(Object object) {
        int index = getIndex(object);

        if (baskets[index].remove(object)) {
            size--;
            modCount++;

            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isRemoved = false;

        for (LinkedList<E> basket : baskets) {

            if (basket != null) {
                int basketSize = basket.size();

                if (basket.removeAll(collection)) {
                    size -= basketSize - basket.size();
                    isRemoved = true;
                }
            }
        }

        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isRemoved = false;

        for (LinkedList<E> basket : baskets) {

            if (basket != null) {
                int basketSize = basket.size();

                if (basket.retainAll(collection)) {
                    size -= basketSize - basket.size();
                    isRemoved = true;
                }
            }
        }

        return isRemoved;
    }

    @Override
    public void clear() {
        if (!this.isEmpty()) {
            for (LinkedList<E> basket : baskets) {
                if (basket != null) {
                    basket.clear();
                }
            }

            size = 0;
            modCount++;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object object) {
        int index = getIndex(object);

        return baskets[index] != null && baskets[index].contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        for (Object element : collection) {
            if (!contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return Arrays.toString(baskets);
    }

    private class HashTableIterator implements Iterator<E> {
        private int elementsPassedNumber;
        private int currentBasketIndex;
        private int currentListIndex = -1;

        private final int initialModCount = modCount;

        @Override
        public boolean hasNext() {
            return elementsPassedNumber < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Хэш-таблица закончилась");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("Хэш-таблица была изменена");
            }

            elementsPassedNumber++;
            currentListIndex++;

            if (currentListIndex >= baskets[currentBasketIndex].size()) {
                do {
                    currentBasketIndex++;
                }
                while (baskets[currentBasketIndex] == null);

                currentListIndex = 0;
            }

            return baskets[currentBasketIndex].get(currentListIndex);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;

        for (LinkedList<E> element : baskets) {
            if (element != null) {
                System.arraycopy(element.toArray(), 0, array, i, element.size());
                i += element.size();
            }
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            //noinspection unchecked
            array = (T[]) new Object[size];
        }

        int i = 0;

        for (LinkedList<E> element : baskets) {
            if (element != null) {
                //noinspection SuspiciousSystemArraycopy
                System.arraycopy(element.toArray(), 0, array, i, element.size());
                i += element.size();
            }
        }

        if (size < array.length) {
            array[size] = null;
        }

        return array;
    }
}