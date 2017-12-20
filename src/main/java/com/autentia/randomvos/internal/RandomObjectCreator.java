package com.autentia.randomvos.internal;

import java.util.List;

public interface RandomObjectCreator {

    <T> T create(Class<T> type);

    <T, B> T createFromBuilder(Class<T> type, Class<B> builderType);

    <T> List<T> createFromPrototype(Class<T> type, T prototype);
}
