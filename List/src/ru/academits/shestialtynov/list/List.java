package ru.academits.shestialtynov.list;

import java.util.NoSuchElementException;
import java.util.Objects;

public class List<E> {
    private ListItem<E> head;
    private int size;

    public List() {
    }

    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " некорректен. Допустимый диапазон индексов от 0 до " + (size - 1));
        }
    }

    private void checkEmpty() {
        if (size == 0) {
            throw new NoSuchElementException("Список пуст");
        }
    }

    private ListItem<E> getItem(int index) {
        ListItem<E> item = head;

        for (int i = 0; i < index; i++) {
            item = item.getNext();
        }

        return item;
    }

    public int getSize() {
        return size;
    }

    public E getFirst() {
        checkEmpty();

        return head.getData();
    }

    public E get(int index) {
        checkIndex(index);

        return getItem(index).getData();
    }

    public E set(int index, E data) {
        checkIndex(index);

        ListItem<E> item = getItem(index);

        E oldData = item.getData();
        item.setData(data);
        return oldData;
    }

    public void addFirst(E data) {
        head = new ListItem<>(data, head);
        size++;
    }

    public void add(int index, E data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " некорректен. Допустимый диапазон индексов от 0 до " + size);
        }

        if (index == 0) {
            addFirst(data);
            return;
        }

        ListItem<E> previousItem = getItem(index - 1);
        previousItem.setNext(new ListItem<>(data, previousItem.getNext()));
        size++;
    }

    public E removeFirst() {
        checkEmpty();

        E removedData = head.getData();
        head = head.getNext();
        size--;

        return removedData;
    }

    public E remove(int index) {
        checkIndex(index);

        if (index == 0) {
            return removeFirst();
        }

        ListItem<E> previousItem = getItem(index - 1);
        ListItem<E> removedItem = previousItem.getNext();
        previousItem.setNext(removedItem.getNext());
        size--;

        return removedItem.getData();
    }

    public boolean remove(E data) {
        if (size == 0) {
            return false;
        }

        if (head.getData().equals(data)) {
            removeFirst();

            return true;
        }

        for (ListItem<E> currentItem = head.getNext(), previousItem = head; currentItem != null; previousItem = currentItem, currentItem = currentItem.getNext()) {
            if (Objects.equals(data, currentItem.getData())) {
                previousItem.setNext(currentItem.getNext());
                size--;

                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "{}";
        }

        StringBuilder stringBuilder = new StringBuilder("{");

        for (ListItem<E> item = head; item != null; item = item.getNext()) {
            stringBuilder.append(item.getData()).append(", ");
        }

        int stringLength = stringBuilder.length();
        stringBuilder.delete(stringLength - 2, stringLength).append('}');

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }

        if (object == null || object.getClass() != getClass()) {
            return false;
        }

        //noinspection unchecked
        List<E> list = (List<E>) object;

        if (list.size != size) {
            return false;
        }

        for (ListItem<E> item = head, listItem = list.head; item != null; item = item.getNext(), listItem = listItem.getNext()) {
            if (!Objects.equals(item.getData(), listItem.getData())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hash = 1;

        for (ListItem<E> item = head; item != null; item = item.getNext()) {
            hash = prime * hash + (item.getData() != null ? item.getData().hashCode() : 0);
        }

        return hash;
    }

    public void turn() {
        if (size <= 1) {
            return;
        }

        ListItem<E> item = head;
        ListItem<E> previousItem = null;

        while (item != null) {
            ListItem<E> nextItem = item.getNext();
            item.setNext(previousItem);
            previousItem = item;
            item = nextItem;
        }

        head = previousItem;
    }

    public List<E> copy() {
        checkEmpty();

        List<E> list = new List<>();
        list.head = new ListItem<>(head.getData());
        ListItem<E> previousListItem = list.head;
        list.size = size;

        for (ListItem<E> item = head.getNext(); item != null; item = item.getNext()) {
            ListItem<E> listItem = new ListItem<>(item.getData());
            previousListItem.setNext(listItem);
            previousListItem = listItem;
        }

        return list;
    }
}