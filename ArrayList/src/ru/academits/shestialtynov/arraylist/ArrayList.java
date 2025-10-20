package ru.academits.shestialtynov.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private E[] items;
    private int size;
    private int modCount;

    public ArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[10];
    }

    public ArrayList(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Вместимость массива не может быть меньше 1, передана вместимость: " + capacity);
        }

        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public ArrayList(List<E> list) {
        size = list.size();
        //noinspection unchecked
        items = (E[]) list.toArray(new Object[size]);
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс некорректен, допустимый диапазон от 0 до " + (size - 1) + ", передан индекс: " + index);
        }
    }

    private void checkIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс некорректен, допустимый диапазон от 0 до " + size + ", передан индекс: " + index);
        }
    }

    @Override
    public boolean add(E item) {
        if (size >= items.length) {
            increaseCapacity();
        }

        items[size] = item;
        size++;
        modCount++;

        return true;
    }

    @Override
    public void add(int index, E item) {
        checkIndexForAdd(index);

        if (size >= items.length) {
            increaseCapacity();
        }

        if (index == size) {
            add(item);
            return;
        }

        System.arraycopy(items, index, items, index + 1, size + 1);

        items[index] = item;
        size++;
        modCount++;
    }

    @Override
    public boolean addAll(Collection<? extends E> collection) {
        return addAll(size, collection);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> collection) {
        checkIndexForAdd(index);

        if (collection.isEmpty()) {
            return false;
        }

        int collectionSize = collection.size();
        ensureCapacity(size + collectionSize);

        if (index < size) {
            System.arraycopy(items, index, items, index + collectionSize, size - index);
        }

        int i = index;

        for (E element : collection) {
            items[i++] = element;
        }

        size += collectionSize;

        return true;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        Arrays.fill(items, null);

        size = 0;
        modCount++;
    }

    @Override
    public boolean contains(Object object) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(items[i], object)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {

        for (Object element : collection) {
            if (contains(element)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        for (E item : items) {
            hash = prime * hash + (item != null ? item.hashCode() : 0);
        }

        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null || object.getClass() != getClass()) {
            return false;
        }

        ArrayList<?> list = (ArrayList<?>) object;

        if (list.size() == size) {
            for (int i = 0; i < size; i++) {
                if (!Objects.equals(items[i], list.items[i])) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E item) {
        checkIndex(index);

        E oldItem = items[index];
        items[index] = item;

        return oldItem;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder stringBuilder = new StringBuilder("[");

        for (int i = 0; i < size; i++) {
            stringBuilder.append(items[i]).append(", ");
        }

        int stringLength = stringBuilder.length();
        stringBuilder.delete(stringLength - 2, stringLength).append(']');

        return stringBuilder.toString();
    }

    @Override
    public int indexOf(Object object) {

        for (int i = 0; i < size; i++) {
            if (Objects.equals(items[i], object)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object object) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(items[i], object)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public java.util.ListIterator<E> listIterator() {
        //noinspection DataFlowIssue
        return null;
    }

    @Override
    public java.util.ListIterator<E> listIterator(int index) {
        //noinspection DataFlowIssue
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        //noinspection DataFlowIssue
        return null;
    }

    private class MyListIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int startModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Коллекция закончилась");
            }

            if (startModCount != modCount) {
                throw new ConcurrentModificationException("Коллекция была изменена");
            }

            currentIndex++;
            return items[currentIndex];
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E itemToDelete = items[index];

        if (index < size - 1) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }

        items[size - 1] = null;
        size--;
        modCount++;
        return itemToDelete;
    }

    @Override
    public boolean remove(Object object) {
        int index = indexOf(object);

        if (index == -1) {
            return false;
        }

        remove(index);

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        boolean isChanged = false;

        for (int i = size - 1; i >= 0; i--) {
            if (collection.contains(items[i])) {
                remove(i);
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        boolean isChanged = false;

        for (int i = size - 1; i >= 0; i--) {
            if (!collection.contains(items[i])) {
                remove(i);
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            //noinspection unchecked
            items = (E[]) new Object[10];
        } else {
            items = Arrays.copyOf(items, items.length * 2);
        }
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T> T[] toArray(T[] array) {
        if (array.length < size) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, size, array.getClass());
        }

        //noinspection unchecked
        System.arraycopy((T[]) items, 0, array, 0, size);

        if (array.length > size) {
            array[size] = null;
        }

        return array;
    }

    public void trimToSize() {
        if (items.length == size) {
            return;
        }

        items = Arrays.copyOf(items, size);
    }

    public void ensureCapacity(int capacity) {
        if (capacity > items.length) {
            items = Arrays.copyOf(items, capacity);
        }
    }
}