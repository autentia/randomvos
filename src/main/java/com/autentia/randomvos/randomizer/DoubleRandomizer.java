package com.autentia.randomvos.randomizer;

public class DoubleRandomizer extends AbstractRandomizer<Double> {

    @Override
    public Double nextRandomValue() {
        return getRandom().nextDouble();
    }
}
