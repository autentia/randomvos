package com.autentia.randomvos.randomizer;

import java.util.List;

public abstract class ParameterizedTypeAbstractRandomizer<T, U extends ParameterizedTypeAbstractRandomizer> extends AbstractRandomizer<T> {

    public ParameterizedTypeAbstractRandomizer() {
        // Empty.
    }

    protected ParameterizedTypeAbstractRandomizer(U prototype) {
        init(prototype.getRandom());
    }

    public abstract U cloneIfApplicable(Class<?> type, List<Class<?>> actualTypes);
}
