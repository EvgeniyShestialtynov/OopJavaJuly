package ru.academits.shestialtynov.hashtable;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private final LinkedList<E>[] buckets;
    private int size;
    private int modCount;

    public HashTable() {
        //noinspection unchecked
        buckets = (LinkedList<E>[]) new LinkedList[10];
    }

    public HashTable(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Вместимость хэш-таблицы не может быть меньше 1, передана вместимость " + capacity);
        }

        //noinspection unchecked
        buckets = (LinkedList<E>[]) new LinkedList[capacity];
    }

    private int getIndex(Object object) {
        if (object == null) {
            return 0;
        }

        return Math.abs(object.hashCode() % buckets.length);
    }

    @Override
    public boolean add(E element) {
        int index = getIndex(element);

        if (buckets[index] == null) {
            buckets[index] = new LinkedList<>();
        }

        buckets[index].add(element);
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

        if (buckets[index] != null && buckets[index].remove(object)) {
            size--;
            modCount++;

            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isRemoved = false;

        for (LinkedList<E> bucket : buckets) {
            if (bucket != null) {
                int initialBucketSize = bucket.size();

                if (bucket.removeAll(collection)) {
                    size -= initialBucketSize - bucket.size();
                    isRemoved = true;
                }
            }
        }

        if (isRemoved) {
            modCount++;
        }

        return isRemoved;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isRemoved = false;

        for (LinkedList<E> bucket : buckets) {
            if (bucket != null) {
                int initialBucketSize = bucket.size();

                if (bucket.retainAll(collection)) {
                    size -= initialBucketSize - bucket.size();
                    isRemoved = true;
                }
            }
        }

        if (isRemoved) {
            modCount++;
        }

        return isRemoved;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (LinkedList<E> bucket : buckets) {
            if (bucket != null) {
                bucket.clear();
            }
        }

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
        int index = getIndex(object);

        return buckets[index] != null && buckets[index].contains(object);
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
        return Arrays.toString(buckets);
    }

    private class HashTableIterator implements Iterator<E> {
        private int elementsPassedCount;
        private int currentBucketIndex;
        private final int initialModCount = modCount;

        private Iterator<E> iterator = null;

        @Override
        public boolean hasNext() {
            return elementsPassedCount < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Хэш-таблица закончилась");
            }

            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("Хэш-таблица была изменена");
            }

            elementsPassedCount++;

            if (iterator == null || buckets[currentBucketIndex] == null || !iterator.hasNext()) {
                while (buckets[currentBucketIndex] == null || buckets[currentBucketIndex].isEmpty()) {
                    currentBucketIndex++;
                }

                iterator = buckets[currentBucketIndex].iterator();
            }

            return iterator.next();
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

        for (LinkedList<E> bucket : buckets) {
            if (bucket != null) {
                for (E element : bucket) {
                    array[i] = element;
                    i++;
                }
            }
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            //noinspection unchecked
            array = (T[]) Arrays.copyOf(array, size, array.getClass());
        }

        int i = 0;

        for (LinkedList<E> bucket : buckets) {
            if (bucket != null) {
                for (E element : bucket) {
                    //noinspection unchecked
                    array[i] = (T) element;
                    i++;
                }
            }
        }

        if (size < array.length) {
            array[size] = null;
        }

        return array;
    }
}