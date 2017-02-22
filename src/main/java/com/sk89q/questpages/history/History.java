package com.sk89q.questpages.history;

public interface History {

    void addHistoryListener(HistoryListener listener);

    void removeHistoryListener(HistoryListener listener);

    Change getLastPendingChange();

    void clearLastPendingChange();

    boolean canUndo();

    boolean canRedo();

    Change getNextUndo();

    Change getNextRedo();

    boolean undo();

    boolean redo();

    void commit(Change change);

}
