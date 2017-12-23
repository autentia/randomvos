package com.autentia.randomvos;

import com.autentia.randomvos.internal.ObjectPlaceholder;
import java.util.Objects;

/**
 * A template to decide whether a {@link com.autentia.randomvos.randomizer.Randomizer} can be applied to either all
 * appearances of a type, a field in one or more classes or a method parameter.
 */
public class RandomizerSelector {

    private final Class<?> type;
    private final String name;
    private final Class<?> containingClass;

    public static RandomizerSelector forType(final Class<?> value) {
        return new RandomizerSelector(value, null, null);
    }

    public static RandomizerSelector forName(final String value) {
        return new RandomizerSelector(null, value, null);
    }

    public static RandomizerSelector forContainingClass(final Class<?> value) {
        return new RandomizerSelector(null, null, value);
    }

    /**
     * At least one of the parameters must be non-null.
     *
     * @param type match against this concrete type.
     * @param name match against a concrete field name or method name (not parameter name).
     * @param containingClass match against a field or method contained in the given class.
     */
    public RandomizerSelector(final Class<?> type, final String name, final Class<?> containingClass) {
        if (type == null && name == null && containingClass == null) {
            throw new IllegalArgumentException("At least one of type, fieldName or containingClass must be provided");
        }
        this.type = type;
        this.name = name;
        this.containingClass = containingClass;
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

    /**
     * Checks if this selector matches the given instance placeholder.
     *
     * @param placeholder placeholder to match against this selector.
     * @return true if the given placeholder matches; false otherwise.
     */
    public boolean matches(final ObjectPlaceholder placeholder) {
        return (type == null || type.equals(placeholder.getType()))
            && (name == null || name.equals(placeholder.getName()))
            && (containingClass == null || containingClass.equals(placeholder.getContainingClass()));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RandomizerSelector)) {
            return false;
        }
        RandomizerSelector other = (RandomizerSelector) obj;
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
