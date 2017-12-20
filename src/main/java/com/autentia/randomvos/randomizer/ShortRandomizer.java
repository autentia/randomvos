package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public class ShortRandomizer extends AbstractRandomizer<Short> {

    public ShortRandomizer(ExtendedRandom random) {
        super(random);
    }

    @Override
    public Short nextRandomValue() {
        return (short) getRandom().nextInt();
    }
}
