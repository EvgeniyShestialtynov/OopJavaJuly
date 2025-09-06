package ru.academits.shestialtynov.list;

public class List<E> {
    private ListItem<E> head;
    private int size = 0;

    public List() {
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Индекс " + index + " недоступен. Допустимый диапазон индексов от 0 до " + (size - 1));
        }
    }

    private ListItem<E> getItem(int index) {
        ListItem<E> listItem = head;

        for (int i = 0; i < index; i++) {
            listItem = listItem.getNext();
        }

        return listItem;
    }

    public int getSize() {
        return size;
    }

    public E getFirstData() {
        return head.getData();
    }

    public E getData(int index) {
        indexCheck(index);

        ListItem<E> listItem = getItem(index);

        return listItem.getData();
    }

    public E setData(int index, E data) {
        indexCheck(index);

        ListItem<E> listItem = getItem(index);

        E oldData = listItem.getData();
        listItem.setData(data);
        return oldData;
    }

    public void addToTop(E data) {
        head = new ListItem<>(data, head);
        size++;
    }

    public void add(int index, E data) {
        indexCheck(index);
        size++;

        if (index == 0) {
            addToTop(data);
            return;
        }

        ListItem<E> listItem = getItem(index);
        listItem.setNext(new ListItem<>(data, listItem.getNext()));
    }

    public E removeFirst() {
        if (size == 0) {
            throw new UnsupportedOperationException("Список пуст");
        }

        E dataToDelete = head.getData();
        head = head.getNext();
        size--;

        return dataToDelete;
    }

    public E remove(int index) {
        indexCheck(index);

        if (index == 0) {
            return removeFirst();
        }

        ListItem<E> listItem = getItem(index - 1);
        ListItem<E> itemToDelete = listItem.getNext();
        listItem.setNext(itemToDelete.getNext());
        size--;

        return itemToDelete.getData();
    }

    public boolean remove(E data) {
        if (size == 0) {
            return false;
        }

        if (data.equals(head.getData())) {
            removeFirst();
            return true;
        }

        for (ListItem<E> currentItem = head.getNext(), previousItem = head; currentItem != null; previousItem = currentItem, currentItem = currentItem.getNext()) {
            if (data.equals(currentItem.getData())) {
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
            return "Список пуст";
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

        //noinspection unchecked
        List<E> list = (List<E>) object;

        if (object == null || object.getClass() != getClass() || list.size != size) {
            return false;
        }

        for (ListItem<E> item = head, listItem = list.head; item != null; item = item.getNext(), listItem = listItem.getNext()) {
            if (!item.getData().equals(listItem.getData())) {
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
            hash = prime * hash + item.getData().hashCode();
        }

        return hash;
    }

    public void turn() {
        if (size == 0) {
            throw new UnsupportedOperationException("Список пуст");
        }

        if (size == 1) {
            return;
        }

        ListItem<E> item;
        ListItem<E> nextItem;
        ListItem<E> previousItem = null;

        for (item = head; item != null; item = nextItem) {
            nextItem = item.getNext();
            item.setNext(previousItem);
            previousItem = item;
        }

        head = previousItem;
    }

    public List<E> copy() {
        List<E> list = new List<>();
        list.head = new ListItem<>(head.getData());
        ListItem<E> listItem = list.head;
        ListItem<E> listPreviousItem = listItem;
        ListItem<E> item = head.getNext();
        list.size++;

        for (int i = 1; i < size; i++) {
            listItem = new ListItem<>(item.getData());
            listPreviousItem.setNext(listItem);
            listPreviousItem = listItem;
            item = item.getNext();
            list.size++;
        }

        return list;
    }
}