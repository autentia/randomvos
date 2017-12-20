package com.autentia.randomvos;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class ExtendedRandomUtils {

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

    public static void setAccesible(final List<Field> fields) {
        for (Field field: fields) {
            field.setAccessible(true);
        }
    }

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
