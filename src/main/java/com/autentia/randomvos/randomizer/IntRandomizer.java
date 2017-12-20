package com.autentia.randomvos.randomizer;

public class IntRandomizer extends AbstractRandomizer<Integer> {

    @Override
    public Integer nextRandomValue() {
        return getRandom().nextInt();
    }
}
