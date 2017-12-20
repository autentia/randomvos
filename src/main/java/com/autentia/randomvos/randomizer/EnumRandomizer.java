package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;
import java.util.List;

public class EnumRandomizer<T extends Enum<T>> extends ParameterizedTypeAbstractRandomizer<Enum<T>, EnumRandomizer> {

    private final Class<Enum<T>> enumType;

    public EnumRandomizer(ExtendedRandom random) {
        super(random);
        enumType = null;
    }

    private EnumRandomizer(EnumRandomizer<T> prototype, Class<Enum<T>> enumType) {
        super(prototype);
        this.enumType = enumType;
    }

    @Override
    public Enum<T> nextRandomValue() {
        Enum<T>[] enumValues = enumType.getEnumConstants();
        return enumValues[getRandom().nextInt(enumValues.length)];
    }

    @Override
    public EnumRandomizer cloneIfApplicable(Class<?> type, List<Class<?>> actualTypes) {
        return type.isEnum() ? new EnumRandomizer(this, type) : null;
    }
}
