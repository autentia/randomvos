package com.autentia.randomvos.internal;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class FieldInstance {

    private final Class<?> type;
    private final String name;
    private final Class<?> containingClass;
    private final List<Class<?>> actualTypeArguments;

    public FieldInstance(Field field) {
        type = field.getType();
        name = field.getName();
        containingClass = field.getDeclaringClass();
        actualTypeArguments = getActualTypeArguments(field.getGenericType());
    }

    public FieldInstance(Method method, int pos) {
        type = method.getParameterTypes()[pos];
        name = method.getName();
        containingClass = method.getDeclaringClass();
        actualTypeArguments = getActualTypeArguments(method.getGenericParameterTypes()[pos]);
    }

    public FieldInstance(Class<?> type, String name, Class<?> containingClass) {
        this.type = type;
        this.name = name;
        this.containingClass = containingClass;
        actualTypeArguments = null;
    }

    private List<Class<?>> getActualTypeArguments(Type type) {
        if (type instanceof ParameterizedType) {
            Type[] actualTypes = ((ParameterizedType) type).getActualTypeArguments();
            return (List) Arrays.asList(actualTypes); // A bit hacky...
        }
        return null;
    }

    public Class<?> getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getContainingClass() {
        return containingClass;
    }

    public List<Class<?>> getActualTypeArguments() {
        return actualTypeArguments;
    }
}
