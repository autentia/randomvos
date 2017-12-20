package com.autentia.randomvos.randomizer;

public class LongRandomizer extends AbstractRandomizer<Long> {

    @Override
    public Long nextRandomValue() {
        return getRandom().nextLong();
    }
}
