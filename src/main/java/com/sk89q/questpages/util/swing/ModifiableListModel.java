package com.sk89q.questpages.util.swing;

import javax.swing.*;

public interface ModifiableListModel<E> extends ListModel<E> {

    void addElement(int index, Object value);

    void removeElement(int index);

    void fireContentsChanged();

}
