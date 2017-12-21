package com.autentia.randomvos;

import com.autentia.randomvos.internal.RandomObjectCreator;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

/**
 * Main class that gives access to random objects.
 * <p>
 * This class is not thread-safe.
 */
public class ExtendedRandom extends Random {

    private final RandomObjectCreator creator;

    protected ExtendedRandom(final RandomObjectCreator creator) {
        this.creator = creator;
    }

    protected ExtendedRandom(final long seed, final RandomObjectCreator creator) {
        super(seed);
        this.creator = creator;
    }

    /**
     * Creates a new random object of the given type.
     * <p>
     * The implementation will choose the best strategy to construct and populate the object.
     *
     * @param <T> type of the object.
     * @param type type of the object.
     * @return the object created.
     */
    public <T> T nextObject(final Type type) {
        return creator.create(type);
    }

    /**
     * Creates a new random object by using a Builder class.
     * <p>
     * The Builder should have a default non-argument constructor and a "build" method that returns the object.
     *
     * @param <T> type of the object.
     * @param <B> type of the Builder.
     * @param type type of the object.
     * @param builderType type of the Builder.
     * @return the object created by the Builder.
     */
    public <T, B> T nextObjectFromBuilder(final Class<T> type, final Class<B> builderType) {
        return creator.createFromBuilder(type, builderType);
    }

    /**
     * Creates new random objects from a prototype object.
     * <p>
     * The new random objects are not exact copies; they differ in just one property, whose value is randomly chosen
     * (different from the original).<br>
     * This implies the list will have one object per property.
     *
     * @param <T> type of the object.
     * @param type type of the object.
     * @param prototype prototype object.
     * @return list of random objects, each differing from the prototype object in one property only.
     */
    public <T> List<T> nextObjectsFromPrototype(final Class<T> type, final T prototype) {
        return creator.createFromPrototype(type, prototype);
    }
}
