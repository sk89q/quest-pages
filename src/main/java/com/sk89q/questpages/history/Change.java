package com.sk89q.questpages.history;

public interface Change {

    String getName();

    boolean containsChange();

    void undo();

    void redo();

}
