package com.autentia.randomvos;

import com.autentia.randomvos.internal.ObjectPlaceholder;
import com.autentia.randomvos.randomizer.BigIntegerRandomizer;
import com.autentia.randomvos.randomizer.BooleanRandomizer;
import com.autentia.randomvos.randomizer.ByteRandomizer;
import com.autentia.randomvos.randomizer.DoubleRandomizer;
import com.autentia.randomvos.randomizer.EnumRandomizer;
import com.autentia.randomvos.randomizer.FloatRandomizer;
import com.autentia.randomvos.randomizer.IntRandomizer;
import com.autentia.randomvos.randomizer.ListRandomizer;
import com.autentia.randomvos.randomizer.LongRandomizer;
import com.autentia.randomvos.randomizer.ParameterizedTypeAbstractRandomizer;
import com.autentia.randomvos.randomizer.Randomizer;
import com.autentia.randomvos.randomizer.ShortRandomizer;
import com.autentia.randomvos.randomizer.StringRandomizer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Used internally to manage all randomizers and the types, fields and methods where they apply.
 */
public class RandomizerRegistry {

    private final Map<RandomizerSelector, Randomizer<?>> randomizers;
    private final List<ParameterizedTypeAbstractRandomizer<?, ?>> prototypeRandomizers;

    public RandomizerRegistry() {
        randomizers = new LinkedHashMap<>(20);
        prototypeRandomizers = new ArrayList<>(5);
    }

    public void put(final RandomizerSelector selector, final Randomizer<?> randomizer) {
        randomizers.put(selector, randomizer);
    }

    public Randomizer<?> get(final ObjectPlaceholder placeholder) {
        for (Entry<RandomizerSelector, Randomizer<?>> entry: randomizers.entrySet()) {
            if (entry.getKey().matches(placeholder)) {
                return entry.getValue();
            }
        }

        for (ParameterizedTypeAbstractRandomizer<?, ?> prototype: prototypeRandomizers) {
            Randomizer<?> randomizer = prototype.cloneIfApplicable(placeholder);
            if (randomizer != null) {
                return randomizer;
            }
        }

        return null;
    }

    public void init(final ExtendedRandomSettings settings, final ExtendedRandom random) {
        ByteRandomizer byteRandomizer = new ByteRandomizer();
        ShortRandomizer shortRandomizer = new ShortRandomizer();
        IntRandomizer intRandomizer = new IntRandomizer();
        LongRandomizer longRandomizer = new LongRandomizer();
        FloatRandomizer floatRandomizer = new FloatRandomizer();
        DoubleRandomizer doubleRandomizer = new DoubleRandomizer();
        BigIntegerRandomizer bigIntegerRandomizer = new BigIntegerRandomizer();
        BooleanRandomizer boolRandomizer = new BooleanRandomizer();
        StringRandomizer stringRandomizer = new StringRandomizer(settings.getMinStringLength(), settings.getMaxStringLength());

        randomizers.put(RandomizerSelector.forType(Byte.TYPE), byteRandomizer);
        randomizers.put(RandomizerSelector.forType(Byte.class), byteRandomizer);
        randomizers.put(RandomizerSelector.forType(Short.TYPE), shortRandomizer);
        randomizers.put(RandomizerSelector.forType(Short.class), shortRandomizer);
        randomizers.put(RandomizerSelector.forType(Integer.TYPE), intRandomizer);
        randomizers.put(RandomizerSelector.forType(Integer.class), intRandomizer);
        randomizers.put(RandomizerSelector.forType(Long.TYPE), longRandomizer);
        randomizers.put(RandomizerSelector.forType(Long.class), longRandomizer);
        randomizers.put(RandomizerSelector.forType(Float.TYPE), floatRandomizer);
        randomizers.put(RandomizerSelector.forType(Float.class), floatRandomizer);
        randomizers.put(RandomizerSelector.forType(Double.TYPE), doubleRandomizer);
        randomizers.put(RandomizerSelector.forType(Double.class), doubleRandomizer);
        randomizers.put(RandomizerSelector.forType(BigInteger.class), bigIntegerRandomizer);
        randomizers.put(RandomizerSelector.forType(Boolean.TYPE), boolRandomizer);
        randomizers.put(RandomizerSelector.forType(Boolean.class), boolRandomizer);
        randomizers.put(RandomizerSelector.forType(String.class), stringRandomizer);

        prototypeRandomizers.add(new EnumRandomizer());
        prototypeRandomizers.add(new ListRandomizer(settings.getMinCollectionSize(), settings.getMaxCollectionSize()));

        init(randomizers.values(), random);
        init(prototypeRandomizers, random);
    }

    private void init(final Collection<? extends Randomizer> randomizers, final ExtendedRandom random) {
        for (Randomizer randomizer: randomizers) {
            randomizer.init(random);
        }
    }
}
