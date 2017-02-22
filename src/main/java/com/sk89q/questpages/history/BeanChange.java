package com.sk89q.questpages.history;

import com.sk89q.questpages.util.ReflectionUtils;
import lombok.Getter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanChange<T> implements Change, MethodInterceptor {

    @Getter private final String name;
    private final T object;
    @Getter private final Map<String, ValueChange> values = new HashMap<>();
    @Getter private final T proxy;

    public BeanChange(String name, T object) {
        this.name = name;
        this.object = object;
        this.proxy = createProxy();
    }

    @Override
    public boolean containsChange() {
        return !values.isEmpty();
    }

    @Override
    public void undo() {
        for (Map.Entry<String, ValueChange> entry : values.entrySet()) {
            ReflectionUtils.callSetter(object, "set" + entry.getKey(), entry.getValue().firstValue);
        }
    }

    @Override
    public void redo() {
        for (Map.Entry<String, ValueChange> entry : values.entrySet()) {
            ReflectionUtils.callSetter(object, "set" + entry.getKey(), entry.getValue().newValue);
        }
    }

    @SuppressWarnings("unchecked")
    private T createProxy() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(object.getClass());
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        String methodName = method.getName();

        if (args.length == 1 && methodName.startsWith("set") && methodName.length() >= 4 && method.getReturnType() == void.class) {
            String property = methodName.substring(3);
            Object newValue = args[0];
            Object prevValue = ReflectionUtils.callGetter(object, (newValue instanceof Boolean ? "is" : "get") + property);

            ValueChange change = values.get(property);
            if (change != null) {
                change.newValue = newValue;
            } else {
                if (prevValue == null && newValue != null || prevValue != null && newValue == null || prevValue != null && !prevValue.equals(newValue)) {
                    change = new ValueChange(ReflectionUtils.callGetter(object, (newValue instanceof Boolean ? "is" : "get") + property), newValue);
                    values.put(property, change);
                }
            }
            methodProxy.invoke(object, args);
            return null;
        } else if (args.length == 0 && (methodName.startsWith("get") || methodName.startsWith("is"))) {
            return methodProxy.invoke(object, args);
        } else {
            throw new RuntimeException("Can't intercept bean change due to unknown method call: " + method.getName() + " on " + o.getClass().getName());
        }
    }

    private static class ValueChange {
        private final Object firstValue;
        private Object newValue;

        private ValueChange(Object firstValue, Object newValue) {
            this.firstValue = firstValue;
            this.newValue = newValue;
        }
    }

}
