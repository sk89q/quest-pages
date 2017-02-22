package com.sk89q.questpages.util.swing;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.util.List;

public class ForwardingListModel<E> extends AbstractListModel<E> implements ModifiableListModel<E> {

    @Getter
    @Setter
    private List<E> entries;

    public ForwardingListModel(List<E> entries) {
        this.entries = entries;
    }

    @Override
    public int getSize() {
        return entries.size();
    }

    @Override
    public E getElementAt(int index) {
        return entries.get(index);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addElement(int index, Object value) {
        entries.add(index, (E) value);
        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public void removeElement(int index) {
        entries.remove(index);
        fireContentsChanged(this, 0, getSize());
    }

    @Override
    public void fireContentsChanged() {
        fireContentsChanged(this, 0, getSize());
    }

}
