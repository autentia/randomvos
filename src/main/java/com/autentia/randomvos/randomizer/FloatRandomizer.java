package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public class FloatRandomizer extends AbstractRandomizer<Float> {

    public FloatRandomizer(ExtendedRandom random) {
        super(random);
    }

    @Override
    public Float nextRandomValue() {
        return getRandom().nextFloat();
    }
}
