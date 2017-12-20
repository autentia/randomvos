package com.autentia.randomvos.randomizer;

public class ShortRandomizer extends AbstractRandomizer<Short> {

    @Override
    public Short nextRandomValue() {
        return (short) getRandom().nextInt();
    }
}
