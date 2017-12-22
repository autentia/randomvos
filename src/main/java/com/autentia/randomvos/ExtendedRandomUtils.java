package com.autentia.randomvos;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods.
 */
public final class ExtendedRandomUtils {

    private ExtendedRandomUtils() {
        // Empty.
    }

    /**
     * Gets all fields declared by a class and its superclasses.
     *
     * @param type the class.
     * @return the list of fields.
     */
    public static List<Field> getFields(final Class<?> type) {
        List<Field> result = new ArrayList<>();
        for (Class current = type; current != Object.class; current = current.getSuperclass()) {
            Field[] fields = current.getDeclaredFields();
            for (Field field: fields) {
                if (!Modifier.isFinal(field.getModifiers())) {
                    result.add(field);
                }
            }
        }
        return result;
    }

    /**
     * Tries to resolve a type to a concrete class.
     *
     * @param type the type to resolve.
     * @return the class instance represented by the given type; null if it cannot be found.
     */
    public static Class<?> resolve(Type type) {
        if (type instanceof Class) {
            return (Class<?>) type;
        }
        if (type instanceof ParameterizedType) {
            return resolve(((ParameterizedType) type).getRawType());
        }
        // TODO: Handle GenericArrayType.
        return null;
    }

    /**
     * Changes accessibility of the given list of fields.
     * @param fields a list of fields.
     */
    public static void setAccesible(final List<Field> fields) {
        for (Field field: fields) {
            field.setAccessible(true);
        }
    }

    /**
     * Shallow copy of an instance of a class.
     *
     * @param <T> the type of the objects.
     * @param type the class of the objects.
     * @param prototype the prototype object to copy from.
     * @return a new instance where all fields have the same value as the prototype.
     */
    public static <T> T copyPrototype(final Class<T> type, final T prototype) {
        try {
            T result = type.newInstance();
            List<Field> fields = ExtendedRandomUtils.getFields(type);
            ExtendedRandomUtils.setAccesible(fields);
            for (Field field: fields) {
                field.set(result, field.get(prototype));
            }
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot create instance of class " + type.getName());
        }
    }
}
