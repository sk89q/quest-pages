package com.sk89q.questpages.history;

import com.sk89q.questpages.util.ReflectionUtils;
import lombok.Getter;

public class PropertyChange implements Change {

    @Getter
    private final String name;
    private final Object object;
    private final String propertyName;
    private final Object oldValue;
    private final Object newValue;

    public PropertyChange(String name, Object object, String propertyName, Object newValue) {
        this.name = name;
        this.object = object;
        this.propertyName = propertyName;
        this.oldValue = ReflectionUtils.callGetter(object, getGetterName());
        this.newValue = newValue;
    }

    private String getGetterName() {
        return (newValue instanceof Boolean ? "is" : "get") + capitalize(propertyName);
    }

    private String getSetterName() {
        return "set" + capitalize(propertyName);
    }

    @Override
    public boolean containsChange() {
        return oldValue == null && newValue != null
                || oldValue != null && newValue == null
                || (oldValue != null && !oldValue.equals(newValue));
    }

    @Override
    public void undo() {
        ReflectionUtils.callSetter(object, getSetterName(), oldValue);
    }

    @Override
    public void redo() {
        ReflectionUtils.callSetter(object, getSetterName(), newValue);
    }

    private static String capitalize(String s) {
        if (s.isEmpty()) return s;
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

}
