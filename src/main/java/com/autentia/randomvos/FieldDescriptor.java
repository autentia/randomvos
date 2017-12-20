package com.autentia.randomvos;

import com.autentia.randomvos.internal.FieldInstance;
import java.lang.reflect.Field;

public class FieldDescriptor<T, C> {

    private final Class<T> type;
    private final String fieldName;
    private final Class<C> containingClass;

    public FieldDescriptor(final Class<T> type, final String fieldName, final Class<C> containingClass) {
        if (type == null && fieldName == null && containingClass == null) {
            throw new IllegalArgumentException("At least one of type, fieldName or containingClass must be provided");
        }
        this.type = type;
        this.fieldName = fieldName;
        this.containingClass = containingClass;
    }

    public Class<T> getType() {
        return type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Class<C> getContainingClass() {
        return containingClass;
    }

    public boolean matches(Field field) {
        return type == null || type.equals(field.getType())
            && fieldName == null || fieldName.equals(field.getName())
            && containingClass == null || containingClass.equals(field.getDeclaringClass());
    }

    public boolean matches(FieldInstance field) {
        return type == null || type.equals(field.getType())
            && fieldName == null || fieldName.equals(field.getName())
            && containingClass == null || containingClass.equals(field.getContainingClass());
    }
}
