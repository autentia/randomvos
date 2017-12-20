package com.autentia.randomvos.randomizer;

public class ByteRandomizer extends AbstractRandomizer<Byte> {

    @Override
    public Byte nextRandomValue() {
        return (byte) getRandom().nextInt();
    }
}
