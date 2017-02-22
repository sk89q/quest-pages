package com.sk89q.questpages.history;

public abstract class UnnamedChange implements Change {

    @Override
    public String getName() {
        return "";
    }

    @Override
    public boolean containsChange() {
        return true;
    }

}
