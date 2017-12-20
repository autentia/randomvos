package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;
import java.util.ArrayList;
import java.util.List;

public class ListRandomizer<T> extends ParameterizedTypeAbstractRandomizer<List<T>, ListRandomizer> {

    private final int minSize;
    private final int maxSize;
    private final Class<T> itemsType;

    public ListRandomizer(ExtendedRandom random, int minSize, int maxSize) {
        super(random);
        this.minSize = minSize;
        this.maxSize = maxSize;
        itemsType = null;
    }

    private ListRandomizer(ListRandomizer<T> prototype, Class<T> itemsType) {
        super(prototype);
        minSize = prototype.minSize;
        maxSize = prototype.maxSize;
        this.itemsType = itemsType;
    }

    @Override
    public List<T> nextRandomValue() {
        int size = minSize + getRandom().nextInt(maxSize - minSize + 1);
        if (size < 0) {
            return null;
        }

        List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            result.add(getRandom().nextObject(itemsType));
        }
        return result;
    }

    @Override
    public ListRandomizer cloneIfApplicable(Class<?> type, List<Class<?>> actualTypes) {
        if (type.equals(List.class)) {
            Class<?> itemType = actualTypes == null || actualTypes.isEmpty() ? Object.class : actualTypes.get(0);
            return new ListRandomizer(this, itemType);
        }
        return null;
    }
}
