package com.sk89q.questpages.util.swing;

import com.sk89q.questpages.history.Change;

import java.util.AbstractList;
import java.util.List;

public class UniqueListChangeProxy<E> extends AbstractList<E> {

    private final List<Change> changes;
    private final List<E> list;

    private UniqueListChangeProxy(List<Change> changes, List<E> list) {
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
        E priorEntry = index > 0 ? list.get(index - 1) : null;
        changes.add(new ListChange<>(list, priorEntry, element, true));
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public E remove(int index) {
        E priorEntry = index > 0 ? list.get(index - 1) : null;
        E previousValue = list.remove(index);
        changes.add(new ListChange<>(list, priorEntry, previousValue, false));
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

    public static <E> UniqueListChangeProxy<E> createProxy(List<Change> changes, List<E> list) {
        return new UniqueListChangeProxy<>(changes, list);
    }

    private static class ListChange<E> implements Change {
        private final List<E> list;
        private final E priorEntry;
        private final E element;
        private final boolean addOperation;

        private ListChange(List<E> list, E priorEntry, E element, boolean addOperation) {
            this.list = list;
            this.priorEntry = priorEntry;
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
            if (priorEntry != null) {
                int index = list.indexOf(priorEntry);
                if (index > -1) {
                    list.add(index + 1, element);
                } else {
                    list.add(element);
                }
            } else {
                list.add(0, element);
            }
        }

        private void remove() {
            list.remove(element);
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
