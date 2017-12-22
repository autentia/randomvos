package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

/**
 * Interface for generating random values of a specific type.
 *
 * @param <T> type of random values.
 */
public interface Randomizer<T> {

    /**
     * The {@link com.autentia.randomvos.RandomizerRegistry} will call this method once to pass the random generator
     * that must be used to produce required random values.
     *
     * @param random random generator to be used internally.
     */
    void init(final ExtendedRandom random);

    /**
     * Produces a new random value.
     *
     * @return the random value generated.
     */
    T nextRandomValue();
}
