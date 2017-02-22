package com.sk89q.questpages.history;

import java.util.ArrayList;
import java.util.List;

public class LinearHistory implements History {

    private final List<HistoryListener> listeners = new ArrayList<>();
    private final List<Change> changes = new ArrayList<>();
    private Change lastPendingChange;
    private int index = 0;

    @Override
    public void addHistoryListener(HistoryListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeHistoryListener(HistoryListener listener) {
        listeners.remove(listener);
    }

    @Override
    public synchronized Change getLastPendingChange() {
        return lastPendingChange;
    }

    @Override
    public synchronized void clearLastPendingChange() {
        lastPendingChange = null;
    }

    @Override
    public synchronized boolean canUndo() {
        return index > 0 && index <= changes.size();
    }

    @Override
    public synchronized boolean canRedo() {
        return index >= 0 && index < changes.size();
    }

    @Override
    public synchronized Change getNextUndo() {
        return canUndo() ? changes.get(index - 1) : null;
    }

    @Override
    public synchronized Change getNextRedo() {
        return canRedo() ? changes.get(index) : null;
    }

    @Override
    public synchronized boolean undo() {
        if (canUndo()) {
            clearLastPendingChange();
            index--;
            changes.get(index).undo();
            for (HistoryListener listener : listeners) {
                listener.undone();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized boolean redo() {
        if (canRedo()) {
            clearLastPendingChange();
            changes.get(index).redo();
            index++;
            for (HistoryListener listener : listeners) {
                listener.redone();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public synchronized void commit(Change change) {
        List<HistoryListener> listeners = this.listeners;
        if (change.containsChange()) {
            lastPendingChange = change;
            if (index < changes.size()) {
                changes.subList(index, changes.size()).clear();
            }
            index++;
            changes.add(change);
            for (HistoryListener listener : listeners) {
                listener.added();
            }
        }
    }

}
