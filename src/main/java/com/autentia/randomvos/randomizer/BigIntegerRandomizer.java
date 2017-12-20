package com.autentia.randomvos.randomizer;

import java.math.BigInteger;

public class BigIntegerRandomizer extends AbstractRandomizer<BigInteger> {

    @Override
    public BigInteger nextRandomValue() {
        return BigInteger.valueOf(getRandom().nextInt());
    }
}
