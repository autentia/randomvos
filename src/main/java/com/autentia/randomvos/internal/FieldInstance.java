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
    private final ParameterizedType genericType;

    public FieldInstance(Field field) {
        type = field.getType();
        name = field.getName();
        containingClass = field.getDeclaringClass();
        genericType = asParameterizedType(field.getGenericType());
    }

    public FieldInstance(Method method, int pos) {
        type = method.getParameterTypes()[pos];
        name = method.getName();
        containingClass = method.getDeclaringClass();
        genericType = asParameterizedType(method.getGenericParameterTypes()[pos]);
    }

    private ParameterizedType asParameterizedType(Type type) {
        return type instanceof ParameterizedType ? (ParameterizedType) type : null;
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

    public ParameterizedType getGenericType() {
        return genericType;
    }

    public List<Class<?>> getActualGenericTypeArguments() {
        if (genericType != null) {
            Type[] actualTypes = genericType.getActualTypeArguments();
            return (List) Arrays.asList(actualTypes); // A bit hacky...
        }
        return null;
    }
}
