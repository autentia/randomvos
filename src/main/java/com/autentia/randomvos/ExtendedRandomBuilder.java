package com.autentia.randomvos;

import com.autentia.randomvos.internal.RandomObjectCreator;
import com.autentia.randomvos.internal.RandomObjectCreatorImpl;
import com.autentia.randomvos.randomizer.MultiTypeRandomizer;
import com.autentia.randomvos.randomizer.Randomizer;

/**
 * To grab an instance of {@link ExtendedRandom}, the client must go through this Builder.
 * <p>
 * The Builder must not be reused.
 */
public class ExtendedRandomBuilder {

    private final ExtendedRandomSettings settings;
    private final RandomizerRegistry registry;

    private Long seed;

    /**
     * Creates a new Builder with default settings:
     * <ul>
     * <li>Depth: 2</li>
     * <li>Min collection size: -1</li>
     * <li>Max collection size: 5</li>
     * <li>Min string length: -1</li>
     * <li>Max string length: 10</li>
     * </ul>
     * -1 means it could be null.
     */
    public ExtendedRandomBuilder() {
        this(new ExtendedRandomSettings(), new RandomizerRegistry());
    }

    protected ExtendedRandomBuilder(final ExtendedRandomSettings settings, final RandomizerRegistry registry) {
        this.settings = settings;
        this.registry = registry;
    }

    /**
     * Sets the seed of the random generator.
     *
     * @param seed seed.
     * @return this Builder.
     */
    public ExtendedRandomBuilder withSeed(final long seed) {
        this.seed = seed;
        return this;
    }

    /**
     * The maximum depth per class to avoid infinite recursion with nested objects of the same class.
     *
     * @param value max depth.
     * @return this Builder.
     */
    public ExtendedRandomBuilder withDepth(final int value) {
        settings.setDepth(value);
        return this;
    }

    /**
     * The minimum collection size.
     *
     * @param value min collection size; -1 if a null value is accepted.
     * @return this Builder.
     */
    public ExtendedRandomBuilder withMinCollectionSize(final int value) {
        settings.setMinCollectionSize(value);
        return this;
    }

    /**
     * The maximum collection size.
     *
     * @param value max collection size.
     * @return this Builder.
     */
    public ExtendedRandomBuilder withMaxCollectionSize(final int value) {
        settings.setMaxCollectionSize(value);
        return this;
    }

    /**
     * The minimum string length.
     *
     * @param value min string length; -1 if a null value is accepted.
     * @return this Builder.
     */
    public ExtendedRandomBuilder withMinStringLength(final int value) {
        settings.setMinStringLength(value);
        return this;
    }

    /**
     * The maximum string length.
     *
     * @param value max string length.
     * @return this Builder.
     */
    public ExtendedRandomBuilder withMaxStringLength(final int value) {
        settings.setMaxStringLength(value);
        return this;
    }

    /**
     * Register a custom randomizer that is chosen as source of random objects whenever the given selector
     * matches an object placeholder.
     * <p>
     * Specially useful is {@link MultiTypeRandomizer} to resolve implementations of interfaces and abstract classes.
     *
     * @param selector randomizer selector that is matched a random object's placeholder.
     * @param value the randomizer.
     * @return this Builder.
     */
    public ExtendedRandomBuilder addRandomizer(final RandomizerSelector selector, final Randomizer<?> value) {
        registry.put(selector, value);
        return this;
    }

    /**
     * @return an {@link ExtendedRandom} that works according to the chosen settings.
     */
    public ExtendedRandom build() {
        RandomObjectCreator creator = new RandomObjectCreatorImpl(settings, registry);
        ExtendedRandom random = seed != null ? new ExtendedRandom(seed, creator) : new ExtendedRandom(creator);
        registry.init(settings, random);
        return random;
    }
}
