package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;

/**
 * Return nulls always.
 * <p>
 * This is a Singleton.
 *
 * @param <T> type of null object.
 */
public class NullRandomizer<T> implements Randomizer<T> {

    /**
     * Single instance of this class.
     */
    public static final NullRandomizer INSTANCE = new NullRandomizer();

    private NullRandomizer() {
        // Empty.
    }

    @Override
    public void init(ExtendedRandom random) {
        // Empty.
    }

    @Override
    public T nextRandomValue() {
        return null;
    }

}
