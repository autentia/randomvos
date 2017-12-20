package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;
import java.math.BigInteger;

public class BigIntegerRandomizer extends AbstractRandomizer<BigInteger> {

    public BigIntegerRandomizer(ExtendedRandom random) {
        super(random);
    }

    @Override
    public BigInteger nextRandomValue() {
        return BigInteger.valueOf(getRandom().nextInt());
    }
}
