package com.autentia.randomvos.internal;

import java.util.HashMap;
import java.util.Map;

public class DepthMeter {

    private final int maxDepth;
    private final Map<Class<?>, Integer> typeDepths;

    public DepthMeter(int maxDepth) {
        this.maxDepth = maxDepth;
        typeDepths = new HashMap<>();
    }

    public boolean tryEnter(Class<?> type) {
        Integer typeDepth = typeDepths.get(type);
        if (typeDepth == null) {
            typeDepth = 0;
        }

        boolean result = typeDepth < maxDepth;
        if (result) {
            typeDepths.put(type, typeDepth + 1);
        }
        return result;
    }

    public void exit(Class<?> type) {
        Integer typeDepth = typeDepths.get(type);
        if (typeDepth == null || typeDepth == 0) {
            throw new IllegalStateException("Cannot exit without entering type " + type.getName());
        }
        typeDepths.put(type, typeDepth - 1);
    }
}
