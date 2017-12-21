package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.internal.ObjectPlaceholder;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ListRandomizer<T> extends ParameterizedTypeAbstractRandomizer<List<T>, ListRandomizer> {

    private final int minSize;
    private final int maxSize;
    private final Type itemsType;

    public ListRandomizer(int minSize, int maxSize) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        itemsType = null;
    }

    private ListRandomizer(final ListRandomizer<T> prototype, final Type itemsType) {
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
            result.add((T) getRandom().nextObject(itemsType));
        }
        return result;
    }

    @Override
    public ListRandomizer cloneIfApplicable(final ObjectPlaceholder placeholder) {
        Class<?> type = placeholder.findClass();
        if (type.equals(List.class)) {
            List<Type> actualTypes = placeholder.findActualTypeArguments();
            Type actualType = actualTypes == null || actualTypes.isEmpty() ? Object.class : actualTypes.get(0);
            return new ListRandomizer(this, actualType);
        }
        return null;
    }
}
