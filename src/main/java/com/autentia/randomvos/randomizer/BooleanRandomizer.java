package com.autentia.randomvos.randomizer;

public class BooleanRandomizer extends AbstractRandomizer<Boolean> {

    @Override
    public Boolean nextRandomValue() {
        return getRandom().nextBoolean();
    }
}
