package com.autentia.randomvos;

import com.autentia.randomvos.internal.RandomObjectCreator;
import com.autentia.randomvos.internal.RandomObjectCreatorImpl;
import com.autentia.randomvos.randomizer.MultiTypeRandomizer;
import com.autentia.randomvos.randomizer.Randomizer;
import java.util.Arrays;

/**
 * To grab an instance of {@link ExtendedRandom}, the client must go through this Builder.
 * <p>
 * The Builder must not be reused.
 */
public class ExtendedRandomBuilder {

    private final ExtendedRandomSettings settings;
    private final RandomizerRegistry registry;

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
     * Not yet implemented.
     *
     * @param values excluded fields.
     * @return this Builder.
     */
    public ExtendedRandomBuilder excludeFields(final FieldDescriptor... values) {
        settings.setExcludedFields(Arrays.asList(values));
        return this;
    }

    /**
     * Not yet implemented.
     *
     * @param values excluded types.
     * @return this Builder.
     */
    public ExtendedRandomBuilder excludeClasses(final Class... values) {
        settings.setExcludedClasses(Arrays.asList(values));
        return this;
    }

    /**
     * Register a custom randomizer that is chosen as source of random objects whenever the given type is encountered.
     * <p>
     * Specially useful is {@link MultiTypeRandomizer} to resolve implementations of interfaces and abstract classes.
     *
     * @param <T> type of random objects.
     * @param type type that triggers the randomizer.
     * @param value the randomizer.
     * @return this Builder.
     */
    public <T> ExtendedRandomBuilder addTypeRandomizer(final Class<T> type, final Randomizer<? extends T> value) {
        registry.putTypeRandomizer(type, value);
        return this;
    }

    /**
     * Register a custom randomizer that is chosen as source of random objects whenever the given field descriptor
     * matches.
     *
     * @param <T> type of random objects.
     * @param field field descriptor that is matched the a random object's field.
     * @param value the randomizer.
     * @return this Builder.
     */
    public <T> ExtendedRandomBuilder addFieldRandomizer(final FieldDescriptor<T, ?> field, final Randomizer<? extends T> value) {
        registry.putFieldRandomizer(field, value);
        return this;
    }

    /**
     * @return an {@link ExtendedRandom} that works according to the chosen settings.
     */
    public ExtendedRandom build() {
        RandomObjectCreator creator = new RandomObjectCreatorImpl(settings, registry);
        ExtendedRandom random = new ExtendedRandom(creator);
        registry.init(settings, random);
        return random;
    }
}
