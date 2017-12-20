package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public class DoubleRandomizer extends AbstractRandomizer<Double> {

    public DoubleRandomizer(ExtendedRandom random) {
        super(random);
    }

    @Override
    public Double nextRandomValue() {
        return getRandom().nextDouble();
    }
}
