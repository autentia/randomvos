package com.autentia.randomvos.internal;

import java.lang.reflect.Type;
import java.util.List;

public interface RandomObjectCreator {

    <T> T create(final Type type);

    <T, B> T createFromBuilder(final Class<T> type, final Class<B> builderType);

    <T> List<T> createFromPrototype(final Class<T> type, final T prototype);
}
