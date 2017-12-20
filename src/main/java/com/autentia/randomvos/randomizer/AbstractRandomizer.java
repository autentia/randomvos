package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public abstract class AbstractRandomizer<T> implements Randomizer<T> {

    private final ExtendedRandom random;

    public AbstractRandomizer(ExtendedRandom random) {
        this.random = random;
    }

    protected ExtendedRandom getRandom() {
        return random;
    }
}
