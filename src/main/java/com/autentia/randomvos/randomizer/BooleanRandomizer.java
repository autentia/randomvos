package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public class BooleanRandomizer extends AbstractRandomizer<Boolean> {

    public BooleanRandomizer(ExtendedRandom random) {
        super(random);
    }

    @Override
    public Boolean nextRandomValue() {
        return getRandom().nextBoolean();
    }
}
