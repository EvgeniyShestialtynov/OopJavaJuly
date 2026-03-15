package ru.academits.shestialtynov.hashtable;

import java.util.*;


public class HashTable<E> implements Collection<E> {
    private LinkedList<E>[] table;
    private int size;
    private int modCount;

    public HashTable() {
        //noinspection unchecked
        table = (LinkedList<E>[]) new LinkedList[100];
    }

    public HashTable(int capacity) {
        //noinspection unchecked
        table = (LinkedList<E>[]) new LinkedList[capacity];
    }

    private int index(Object object) {
        return Math.abs(object.hashCode() % table.length);
    }

    @Override
    public boolean add(E element) {
        int index = index(element);

        if (table[index] == null) {
            table[index] = new LinkedList<>();
        } else if (table[index].contains(element)) {
            return false;
        }

        table[index].add(element);
        size++;
        modCount++;

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        for (E element : collection) {
            add(element);
        }

        return true;
    }

    @Override
    public boolean remove(Object object) {
        int index = index(object);

        //noinspection unchecked
        E element = (E) object;

        if (table[index].remove(element)) {
            size--;
            modCount++;

            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean removed = false;

        for (Object element : collection) {
            if (remove(element)) {
                removed = true;
            }
        }

        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean removed = false;

        for (E element : this) {
            if (!collection.contains(element)) {
                remove(element);
                removed = true;
            }
        }

        return removed;
    }

    @Override
    public void clear() {
        Arrays.fill(table, null);
        size = 0;
        modCount++;
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
        int index = index(object);

        if (table[index] == null) {
            return false;
        }

        return table[index].contains(object);
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
        return Arrays.toString(table);
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null || object.getClass() != getClass()) {
            return false;
        }

        HashTable<?> hashTable = (HashTable<?>) object;

        if (hashTable.size != size) {
            return false;
        }

        for (int i = 0; i < table.length; i++) {
            if (!Objects.equals(table[i], hashTable.table[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        for (LinkedList<E> element : table) {
            hash = prime * hash + (element == null ? 0 : element.hashCode());
        }

        return hash;
    }

    private class HashTableIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int initialModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < table.length;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Хэш-таблица закончилась");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("Хэш-таблица была изменена");
            }
            currentIndex++;

            //noinspection unchecked
            return (E) table[currentIndex];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[][] array = new Object[table.length][];
        int i = 0;
        for (LinkedList<E> element : table) {
            array[i] = new LinkedList[]{element};
            i++;
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < table.length) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(table, table.length, array.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(table, 0, array, 0, size);

        if (table.length < array.length) {
            array[table.length] = null;
        }

        return array;
    }
}