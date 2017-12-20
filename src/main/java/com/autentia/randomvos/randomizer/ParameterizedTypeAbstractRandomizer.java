package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;
import java.util.List;

public abstract class ParameterizedTypeAbstractRandomizer<T, U extends ParameterizedTypeAbstractRandomizer> extends AbstractRandomizer<T> {

    public ParameterizedTypeAbstractRandomizer(ExtendedRandom random) {
        super(random);
    }

    public ParameterizedTypeAbstractRandomizer(U prototype) {
        super(prototype.getRandom());
    }

    public abstract U cloneIfApplicable(Class<?> type, List<Class<?>> actualTypes);
}
