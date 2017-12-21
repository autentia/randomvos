package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.internal.ObjectPlaceholder;

public abstract class ParameterizedTypeAbstractRandomizer<T, U extends ParameterizedTypeAbstractRandomizer> extends AbstractRandomizer<T> {

    public ParameterizedTypeAbstractRandomizer() {
        // Empty.
    }

    protected ParameterizedTypeAbstractRandomizer(final U prototype) {
        init(prototype.getRandom());
    }

    public abstract U cloneIfApplicable(final ObjectPlaceholder placeholder);
}
