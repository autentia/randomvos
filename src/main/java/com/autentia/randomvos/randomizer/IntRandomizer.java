package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public class IntRandomizer extends AbstractRandomizer<Integer> {

    public IntRandomizer(ExtendedRandom random) {
        super(random);
    }

    @Override
    public Integer nextRandomValue() {
        return getRandom().nextInt();
    }
}
