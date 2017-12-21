package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public abstract class AbstractRandomizer<T> implements Randomizer<T> {

    private ExtendedRandom random;

    public AbstractRandomizer() {
        random = null;
    }

    protected ExtendedRandom getRandom() {
        return random;
    }

    @Override
    public void init(final ExtendedRandom random) {
        this.random = random;
    }
}
