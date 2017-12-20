package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public class LongRandomizer extends AbstractRandomizer<Long> {

    public LongRandomizer(ExtendedRandom random) {
        super(random);
    }

    @Override
    public Long nextRandomValue() {
        return getRandom().nextLong();
    }
}
