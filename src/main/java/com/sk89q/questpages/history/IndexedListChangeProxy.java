package com.sk89q.questpages.history;

import java.util.AbstractList;
import java.util.List;

public class IndexedListChangeProxy<E> extends AbstractList<E> {

    private final List<Change> changes;
    private final List<E> list;

    private IndexedListChangeProxy(List<Change> changes, List<E> list) {
        this.changes = changes;
        this.list = list;
    }

    @Override
    public E set(int index, E element) {
        E previousValue = remove(index);
        add(index, element);
        return previousValue;
    }

    @Override
    public boolean add(E e) {
        add(list.size(), e);
        return true;
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
        changes.add(new ListChange<>(list, index, element, true));
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public E remove(int index) {
        E previousValue = list.remove(index);
        changes.add(new ListChange<>(list, index, previousValue, false));
        return previousValue;
    }

    @Override
    public boolean remove(Object o) {
        int index = list.indexOf(o);
        if (index > -1) {
            remove(index);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    public static <E> IndexedListChangeProxy<E> createProxy(List<Change> changes, List<E> list) {
        return new IndexedListChangeProxy<>(changes, list);
    }

    private static class ListChange<E> implements Change {
        private final List<E> list;
        private int index;
        private final E element;
        private final boolean addOperation;

        private ListChange(List<E> list, int index, E element, boolean addOperation) {
            this.list = list;
            this.index = index;
            this.element = element;
            this.addOperation = addOperation;
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public boolean containsChange() {
            return true;
        }

        private void add() {
            if (index <= list.size()) {
                list.add(index, element);
            } else {
                index = list.size();
                list.add(element);
            }
        }

        private void remove() {
            E existing = list.get(index);
            if (existing == element) {
                list.remove(index);
            } else {
                int newIndex = list.indexOf(element);
                if (newIndex > -1) {
                    list.remove(newIndex);
                    index = newIndex;
                }
            }
        }

        @Override
        public void undo() {
            if (addOperation) {
                remove();
            } else {
                add();
            }
        }

        @Override
        public void redo() {
            if (addOperation) {
                add();
            } else {
                remove();
            }
        }
    }

}
