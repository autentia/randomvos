package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.internal.ObjectPlaceholder;

public class EnumRandomizer<T extends Enum<T>> extends ParameterizedTypeAbstractRandomizer<Enum<T>, EnumRandomizer> {

    private final Class<Enum<T>> enumType;

    public EnumRandomizer() {
        enumType = null;
    }

    private EnumRandomizer(final EnumRandomizer<T> prototype, final Class<Enum<T>> enumType) {
        super(prototype);
        this.enumType = enumType;
    }

    @Override
    public T nextRandomValue() {
        Enum<T>[] enumValues = enumType.getEnumConstants();
        return (T) enumValues[getRandom().nextInt(enumValues.length)];
    }

    @Override
    public EnumRandomizer cloneIfApplicable(final ObjectPlaceholder placeholder) {
        Class<?> type = placeholder.findClass();
        return type.isEnum() ? new EnumRandomizer(this, type) : null;
    }
}
