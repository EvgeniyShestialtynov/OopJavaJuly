package ru.academits.shestialtynov.hashtable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import java.util.List;

public class HashTable<E> implements Collection<E> {
    private List<E>[] table;
    private int size;

    public HashTable() {
        //noinspection unchecked
        table = (List<E>[]) new List[100];
    }

    public HashTable(int capacity) {
        //noinspection unchecked
        table = (List<E>[]) new List[capacity];
    }

    private int index(Object object) {
        return Math.abs(object.hashCode() % table.length);
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
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];
        int i = 0;
        for (E element : this) {
            array[i++] = element;
        }
        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E element) {
        int index = index(element);

        if (table[index].contains(element)) {
            return false;
        }

        table[index].addFirst(element);
        size++;

        return true;
    }

    @Override
    public boolean remove(Object object) {
        int index = index(object);

        //noinspection unchecked
        E element = (E) object;
        if (table[index].remove(element)) {
            size--;

            return true;
        }

        return false;
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
    public boolean addAll(Collection<? extends E> collection) {
        for (E element : collection) {
            add(element);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean removed = false;

        for (Object element : collection) {
            if (remove(element)) {
                removed = true;
            };
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
    }
}