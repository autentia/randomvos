package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public class ByteRandomizer extends AbstractRandomizer<Byte> {

    public ByteRandomizer(ExtendedRandom random) {
        super(random);
    }

    @Override
    public Byte nextRandomValue() {
        return (byte) getRandom().nextInt();
    }
}
