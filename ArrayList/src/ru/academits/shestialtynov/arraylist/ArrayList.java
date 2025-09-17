package ru.academits.shestialtynov.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private E[] items;
    private int size = 0;
    private int modCount = 0;

    public ArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[10];
    }

    public ArrayList(int capacity) {
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public ArrayList(List<E> list) {
        size = list.size();
        //noinspection unchecked
        items = (E[]) list.toArray(new Object[size]);
    }

    private void checkIndex(int index) {
        if (size == 0) {
            throw new UnsupportedOperationException("Список пуст");
        }

        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Индекс некорректен, допустимый диапазон от 0 до " + (size - 1));
        }
    }

    private void checkIndexForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Индекс некорректен, допустимый диапазон от 0 до " + (size));
        }
    }

    @Override
    public boolean add(E element) {
        if (size >= items.length) {
            increaseCapacity();
        }

        items[size] = element;
        size++;
        modCount++;

        return true;
    }

    @Override
    public void add(int index, E element) {
        checkIndexForAdd(index);

        if (size >= items.length) {
            increaseCapacity();
        }

        for (int i = size; i > index; i--) {
            items[i] = items[i - 1];
        }

        items[index] = element;
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

        //noinspection unchecked
        System.arraycopy((E[]) collection.toArray(), 0, items, index, collectionSize);
        modCount++;
        size = size + collectionSize;

        return true;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }

        size = 0;
        modCount++;
    }

    @Override
    public boolean contains(Object object) {
        for (int i = 0; i < size; i++) {
            if (object == items[i]) {
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        boolean isContain;

        for (Object element : collection) {
            isContain = false;
            for (int j = 0; j < size; j++) {
                if (element == items[j]) {
                    isContain = true;
                    break;
                }
            }

            if (!isContain) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null || object.getClass() != getClass() || ((ArrayList<?>) object).size() != size) {
            return false;
        }

        for (int i = 0; i < size; i++) {
            if (!items[i].equals(((ArrayList<?>) object).get(i))) {
                return false;
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
    public E set(int index, E element) {
        checkIndex(index);

        E elementToDelete = items[index];
        items[index] = element;

        return elementToDelete;
    }

    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        for (E element : items) {
            hash = prime * hash + element.hashCode();
        }

        return hash;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "{}";
        }

        StringBuilder stringBuilder = new StringBuilder("{");

        for (int i = 0; i < size; i++) {
            stringBuilder.append(items[i]).append(", ");
        }

        int stringLength = stringBuilder.length();
        stringBuilder.delete(stringLength - 2, stringLength).append('}');

        return stringBuilder.toString();
    }

    @Override
    public int indexOf(Object object) {
        int result = -1;

        for (int i = 0; i < size; i++) {
            if (object == items[i]) {
                result = i;
                break;
            }
        }

        return result;
    }

    @Override
    public int lastIndexOf(Object object) {
        int result = -1;

        for (int i = size - 1; i >= 0; i--) {
            if (object == items[i]) {
                result = i;
                break;
            }
        }

        return result;
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

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class ListIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int listModCount = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Коллекция закончилась");
            }

            if (listModCount != modCount) {
                throw new ConcurrentModificationException("Размер коллекции был изменен");
            }

            currentIndex++;
            return items[currentIndex];
        }
    }

    public Iterator<E> iterator() {
        return new ListIterator();
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E elementToDelete = items[index];

        if (index < size - 1) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }

        items[size - 1] = null;
        size--;
        modCount++;
        return elementToDelete;
    }

    @Override
    public boolean remove(Object object) {
        int index = indexOf(object);
        if (index == -1) {
            return false;
        }

        if (index < size - 1) {
            System.arraycopy(items, index + 1, items, index, size - index - 1);
        }

        items[size - 1] = null;
        size--;
        modCount++;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        @SuppressWarnings("unchecked") E[] array = (E[]) collection.toArray();

        boolean listChanged = false;

        for (E element : array) {
            listChanged = remove(element);
        }

        return listChanged;
    }

    public boolean retainAll(Collection<?> collection) {
        boolean listChanged = false;

        for (int i = 0; i < size; i++) {
            if (collection.contains(items[i])) {
                continue;
            }

            listChanged = remove(items[i]);
            i--;
        }

        return listChanged;
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
        int arraySize = Math.max(size, array.length);
        //noinspection unchecked
        return (T[]) Arrays.copyOf(items, arraySize);
    }

    public void trimToSize() {
        items = Arrays.copyOf(items, size);
    }

    public void ensureCapacity(int capacity) {
        if (capacity > items.length) {
            items = Arrays.copyOf(items, capacity);
        }
    }
}