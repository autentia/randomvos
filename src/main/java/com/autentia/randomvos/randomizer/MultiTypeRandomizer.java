package com.autentia.randomvos.randomizer;

/**
 * This randomizer should be used to build concrete implementations out of interfaces or abstract classes.
 * <p>
 * It should be manually registered as a "type" randomizer.
 *
 * @param <T> The interface or abstract class implemented/extended by all concrete classes.
 */
public class MultiTypeRandomizer<T> extends AbstractRandomizer<T> {

    private final Class<? extends T>[] types;

    public MultiTypeRandomizer(Class<? extends T>... types) {
        this.types = types;
    }

    @Override
    public T nextRandomValue() {
        int pos = getRandom().nextInt(types.length);
        return getRandom().nextObject(types[pos]);
    }
}
