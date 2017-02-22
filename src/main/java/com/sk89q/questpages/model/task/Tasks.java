package com.sk89q.questpages.model.task;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import lombok.extern.java.Log;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

@Log
public final class Tasks {

    private static final Map<String, Class<? extends Task>> TASK_TYPES;

    static {
        Map<String, Class<? extends Task>> taskTypes = new LinkedHashMap<>();
        JsonSubTypes subTypes = Task.class.getAnnotation(JsonSubTypes.class);
        for (Type type : subTypes.value()) {
            @SuppressWarnings("unchecked")
            Class<? extends Task> clazz = (Class<? extends Task>) type.value();
            try {
                String typeName = clazz.newInstance().getTypeName();
                taskTypes.put(typeName, clazz);
            } catch (InstantiationException | IllegalAccessException e) {
                log.log(Level.WARNING, "Failed to get class type name from " + clazz.getName(), e);
            }
        }
        TASK_TYPES = Collections.unmodifiableMap(taskTypes);
    }

    private Tasks() {
    }

    public static Map<String, Class<? extends Task>> getTaskTypes() {
        return TASK_TYPES;
    }

}
