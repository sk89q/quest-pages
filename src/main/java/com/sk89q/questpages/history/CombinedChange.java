package com.sk89q.questpages.history;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class CombinedChange implements Change {

    @Getter private final String name;
    private final List<? extends Change> changes;

    public CombinedChange(String name, Change... changes) {
        this.name = name;
        this.changes = Arrays.asList(changes);
    }

    public CombinedChange(String name, List<? extends Change> changes) {
        this.name = name;
        this.changes = changes;
    }

    @Override
    public boolean containsChange() {
        for (Change change : changes) {
            if (change.containsChange()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void undo() {
        if (changes.isEmpty()) return;
        for (int i = changes.size() - 1; i >= 0; i--) {
            changes.get(i).undo();
        }
    }

    @Override
    public void redo() {
        for (Change change : changes) {
            change.redo();
        }
    }
}
