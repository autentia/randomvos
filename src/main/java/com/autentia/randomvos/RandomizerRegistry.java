package com.autentia.randomvos;

import com.autentia.randomvos.internal.FieldInstance;
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

public class RandomizerRegistry {

    private final Map<Class<?>, Randomizer<?>> typeRandomizers;
    private final Map<FieldDescriptor<?, ?>, Randomizer<?>> fieldRandomizers;
    private final List<ParameterizedTypeAbstractRandomizer<?, ?>> prototypeRandomizers;

    public RandomizerRegistry() {
        typeRandomizers = new LinkedHashMap<>(10);
        fieldRandomizers = new LinkedHashMap<>(10);
        prototypeRandomizers = new ArrayList<>(5);
    }

    public <T> void putTypeRandomizer(final Class<T> type, final Randomizer<? extends T> randomizer) {
        typeRandomizers.put(type, randomizer);
    }

    public <T> void putFieldRandomizer(final FieldDescriptor<T, ?> fieldDescriptor, final Randomizer<? extends T> randomizer) {
        fieldRandomizers.put(fieldDescriptor, randomizer);
    }

    public <T> Randomizer<? extends T> get(final Class<T> type) {
        return typeRandomizers.containsKey(type) ? typeRandomizers.get(type) : buildCustomRandomizer(type, null);
    }

    public <T> Randomizer<? extends T> get(final FieldInstance field) {
        for (Entry<FieldDescriptor<?, ?>, Randomizer<?>> entry: fieldRandomizers.entrySet()) {
            if (entry.getKey().matches(field)) {
                return (Randomizer<? extends T>) entry.getValue();
            }
        }
        return buildCustomRandomizer(field);
    }

    private Randomizer buildCustomRandomizer(final FieldInstance field) {
        Class<?> type = field.getType();
        List<Class<?>> itemsTypes = field.getActualGenericTypeArguments();
        return buildCustomRandomizer(type, itemsTypes);
    }

    private Randomizer buildCustomRandomizer(final Class<?> type, final List<Class<?>> itemsTypes) {
        for (ParameterizedTypeAbstractRandomizer prototype: prototypeRandomizers) {
            Randomizer randomizer = prototype.cloneIfApplicable(type, itemsTypes);
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

        typeRandomizers.put(Byte.TYPE, byteRandomizer);
        typeRandomizers.put(Byte.class, byteRandomizer);
        typeRandomizers.put(Short.TYPE, shortRandomizer);
        typeRandomizers.put(Short.class, shortRandomizer);
        typeRandomizers.put(Integer.TYPE, intRandomizer);
        typeRandomizers.put(Integer.class, intRandomizer);
        typeRandomizers.put(Long.TYPE, longRandomizer);
        typeRandomizers.put(Long.class, longRandomizer);
        typeRandomizers.put(Float.TYPE, floatRandomizer);
        typeRandomizers.put(Float.class, floatRandomizer);
        typeRandomizers.put(Double.TYPE, doubleRandomizer);
        typeRandomizers.put(Double.class, doubleRandomizer);
        typeRandomizers.put(BigInteger.class, bigIntegerRandomizer);
        typeRandomizers.put(Boolean.TYPE, boolRandomizer);
        typeRandomizers.put(Boolean.class, boolRandomizer);
        typeRandomizers.put(String.class, stringRandomizer);

        prototypeRandomizers.add(new EnumRandomizer());
        prototypeRandomizers.add(new ListRandomizer(settings.getMinCollectionSize(), settings.getMaxCollectionSize()));

        init(typeRandomizers.values(), random);
        init(fieldRandomizers.values(), random);
        init(prototypeRandomizers, random);
    }

    private void init(Collection<? extends Randomizer> randomizers, ExtendedRandom random) {
        for (Randomizer randomizer: randomizers) {
            randomizer.init(random);
        }
    }
}
