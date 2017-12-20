package com.autentia.randomvos.randomizer;

public class FloatRandomizer extends AbstractRandomizer<Float> {

    @Override
    public Float nextRandomValue() {
        return getRandom().nextFloat();
    }
}
