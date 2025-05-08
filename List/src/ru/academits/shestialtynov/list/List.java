package ru.academits.shestialtynov.list;

import ru.academits.shestialtynov.listItem.ListItem;

import java.util.Arrays;

public class List<E> {
    private ListItem<E> head;
    private int count;

    public List() {
    }

    public int getSize() {
        return count;
    }

    public E getData() {
        return head.getData();
    }

    public E getData(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Нет элемента с индексом " + index + ". Допустимый диапазон индексов от 0 до " + (count - 1));
        }

        ListItem<E> listItem = head;

        for (int i = 0; i < index; i++) {
            listItem = listItem.getNext();
        }

        return listItem.getData();
    }

    public E setData(int index, E data) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Нет элемента с индексом " + index + ". Допустимый диапазон индексов от 0 до " + (count - 1));
        }

        ListItem<E> listItem = head;

        for (int i = 0; i < index - 1; i++) {
            listItem = listItem.getNext();
        }

        E oldData = listItem.getData();
        listItem.setData(data);
        return oldData;
    }

    public void add(E data) {
        head = new ListItem<E>(data, head);
        count++;
    }

    public void add(E data, int index) {
        if (index < 0 || index > count) {
            throw new IndexOutOfBoundsException("Нет элемента с индексом " + index + ". Допустимый диапазон индексов от 0 до " + count);
        }

        if (index == 0) {
            add(data);
        } else {

            ListItem<E> listItem = head;

            for (int i = 0; i < index - 1; i++) {
                listItem = listItem.getNext();
            }

            listItem.setNext(new ListItem<>(data, listItem.getNext()));
            count++;
        }
    }

    public E remove() {
        E dataToDelete = head.getData();
        head = head.getNext();
        count--;
        return dataToDelete;
    }

    public E remove(int index) {
        if (index < 0 || index >= count) {
            throw new IndexOutOfBoundsException("Нет элемента с индексом " + index + ". Допустимый диапазон индексов от 0 до " + (count - 1));
        }

        if (index == 0) {
            return remove();
        } else {
            ListItem<E> listItem = head.getNext();

            for (int i = 1; i < index - 1; i++) {
                listItem = listItem.getNext();
            }

            ListItem<E> itemToDelete = listItem.getNext();
            listItem.setNext(itemToDelete.getNext());
            count--;

            return itemToDelete.getData();
        }
    }

    public boolean remove(E data) {
        if (head.getData() == data) {
            head = head.getNext();
            count--;
            return true;
        }

        for (ListItem<E> currentItem = head.getNext(), previousItem = head; currentItem != null; previousItem = currentItem, currentItem = currentItem.getNext()) {
            if (currentItem.getData() == data) {
                previousItem.setNext(currentItem.getNext());
                count--;
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
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

        if (object == null || object.getClass() != getClass() || ((List<?>) object).count != count) {
            return false;
        }

        for (@SuppressWarnings("unchecked") ListItem<E> item = head, objectItem = (ListItem<E>) ((List<?>) object).head; item != null; item = item.getNext(), objectItem = objectItem.getNext()) {
            if (!item.getData().equals(objectItem.getData())) {
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
            hash = prime * hash + item.hashCode();
        }

        return hash;
    }

    public void turn() {
        ListItem<E> item = head;
        ListItem<E> previousItem = null;
        ListItem<E> nextItem = head.getNext();

        for (int i = 0; i < count - 1; i++) {
            item.setNext(previousItem);
            previousItem = item;
            item = nextItem;
            nextItem = nextItem.getNext();
        }
        item.setNext(previousItem);
        head = item;
    }

    public List<E> copy() {
        List<E> list = new List<>();
        ListItem<E> item = head;

        for (int i = 0; i < count; i++) {
            list.add(item.getData(), i);
            item = item.getNext();
        }
        return list;
    }
}