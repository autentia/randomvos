package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

public interface Randomizer<T> {

    void init(ExtendedRandom random);
    T nextRandomValue();
}
