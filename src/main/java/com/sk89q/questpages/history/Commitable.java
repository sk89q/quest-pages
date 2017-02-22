package com.sk89q.questpages.history;

public class Commitable<T extends Change> implements AutoCloseable {

    private final History history;
    private final T change;
    private boolean committed;

    public Commitable(History history, T change, boolean committed) {
        this.history = history;
        this.committed = committed;
        this.change = change;
    }

    public T getChange() {
        return this.change;
    }

    public void commit() {
        if (!committed) {
            history.commit(change);
            committed = true;
        }
    }

    @Override
    public void close() {
        commit();
    }

}
