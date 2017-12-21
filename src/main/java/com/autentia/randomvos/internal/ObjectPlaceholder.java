package com.autentia.randomvos.internal;

import com.autentia.randomvos.ExtendedRandomUtils;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ObjectPlaceholder {

    private final Type type;
    private final String name;
    private final Class<?> containingClass;

    public static ObjectPlaceholder forType(Type type) {
        return new ObjectPlaceholder(type);
    }

    public static ObjectPlaceholder forField(Field field) {
        return new ObjectPlaceholder(field, null);
    }

    public static ObjectPlaceholder forField(Field field, Type containingType) {
        return new ObjectPlaceholder(field, containingType);
    }

    public static ObjectPlaceholder forParam(Method method, int pos) {
        return new ObjectPlaceholder(method, pos, null);
    }

    public static ObjectPlaceholder forParam(Method method, int pos, Type containingType) {
        return new ObjectPlaceholder(method, pos, containingType);
    }

    private ObjectPlaceholder(final Type type) {
        this.type = type;
        name = null;
        containingClass = null;
    }

    private ObjectPlaceholder(final Field field, final Type containingType) {
        type = extractType(field.getGenericType(), field.getType(), containingType);
        name = field.getName();
        containingClass = field.getDeclaringClass();
    }

    private ObjectPlaceholder(final Method method, final int pos, Type containingType) {
        type = extractType(method.getGenericParameterTypes()[pos], method.getParameterTypes()[pos], containingType);
        name = method.getName();
        containingClass = method.getDeclaringClass();
    }

    private Type extractType(final Type genericType, final Class<?> typeClass, final Type containingType) {
        if (genericType instanceof ParameterizedType) {
            return genericType;
        }
        if (genericType instanceof TypeVariable) {
            if (containingType instanceof ParameterizedType) {
                String varName = ((TypeVariable) genericType).getName();
                int pos = 0;
                for (TypeVariable var: ExtendedRandomUtils.resolve(containingType).getTypeParameters()) {
                    if (var.getName().equals(varName)) {
                        return ((ParameterizedType) containingType).getActualTypeArguments()[pos];
                    }
                    pos++;
                }
            }
        }
        return typeClass;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Class<?> getContainingClass() {
        return containingClass;
    }

    public Class<?> findClass() {
        return  ExtendedRandomUtils.resolve(type);
    }

    public List<Type> findActualTypeArguments() {
        if (type instanceof ParameterizedType) {
            return Arrays.asList(((ParameterizedType) type).getActualTypeArguments());
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ObjectPlaceholder)) {
            return false;
        }
        ObjectPlaceholder other = (ObjectPlaceholder) obj;
        return Objects.equals(type, other.type)
            && Objects.equals(name, other.name)
            && Objects.equals(containingClass, other.containingClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, name, containingClass);
    }

    @Override
    public String toString() {
        return String.format("{Type = %s, Name = %s, Containing class = %s}", type, name, containingClass);
    }


}
