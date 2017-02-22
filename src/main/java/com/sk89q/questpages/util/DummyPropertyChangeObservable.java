package com.sk89q.questpages.util;

import java.beans.PropertyChangeListener;

public interface DummyPropertyChangeObservable {

    default void addPropertyChangeListener(PropertyChangeListener listener) {
    }

    default void removePropertyChangeListener(PropertyChangeListener listener) {
    }

}
