package com.sk89q.questpages.history;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CollectionChangeProxy<E> extends AbstractCollection<E> {

    private final List<Change> changes;
    private final Collection<E> collection;

    public CollectionChangeProxy(List<Change> changes, Collection<E> collection) {
        this.changes = changes;
        this.collection = collection;
    }

    @Override
    public Iterator<E> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return collection.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean add(E e) {
        if (collection.add(e)) {
            changes.add(new Add((Collection<Object>) collection, e));
            return true;
        } else {
            return false;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean remove(Object o) {
        if (collection.remove(o)) {
            E e = (E) o;
            changes.add(new Remove((Collection<Object>) collection, o));
            return true;
        } else {
            return false;
        }
    }

    private static class Add implements Change {
        private final Collection<Object> collection;
        private final Object element;

        private Add(Collection<Object> collection, Object element) {
            this.collection = collection;
            this.element = element;
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public boolean containsChange() {
            return true;
        }

        @Override
        public void undo() {
            collection.remove(element);
        }

        @Override
        public void redo() {
            collection.add(element);
        }
    }

    private static class Remove implements Change {
        private final Collection<Object> collection;
        private final Object element;

        private Remove(Collection<Object> collection, Object element) {
            this.collection = collection;
            this.element = element;
        }

        @Override
        public String getName() {
            return "";
        }

        @Override
        public boolean containsChange() {
            return true;
        }

        @Override
        public void undo() {
            collection.add(element);
        }

        @Override
        public void redo() {
            collection.remove(element);
        }
    }

}
