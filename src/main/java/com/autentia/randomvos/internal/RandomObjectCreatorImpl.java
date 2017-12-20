package com.autentia.randomvos.internal;

import com.autentia.randomvos.ExtendedRandomSettings;
import com.autentia.randomvos.ExtendedRandomUtils;
import com.autentia.randomvos.RandomizerRegistry;
import com.autentia.randomvos.randomizer.Randomizer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RandomObjectCreatorImpl implements RandomObjectCreator {

    private final RandomizerRegistry registry;
    private final DepthMeter depthMeter;

    public RandomObjectCreatorImpl(final ExtendedRandomSettings settings, final RandomizerRegistry registry) {
        this.registry = registry;
        depthMeter = new DepthMeter(settings.getDepth());
    }

    @Override
    public <T> T create(final Class<T> type) {
        if (depthMeter.tryEnter(type)) {
            try {
                Randomizer<? extends T> randomizer = registry.get(type);
                if (randomizer != null) {
                    return randomizer.nextRandomValue();
                }

                T result = type.newInstance();
                List<Field> fields = ExtendedRandomUtils.getFields(type);
                ExtendedRandomUtils.setAccesible(fields);
                for (Field field: fields) {
                    Object value = createFieldValue(new FieldInstance(field));
                    field.set(result, value);
                }
                return result;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new IllegalArgumentException("Cannot create instance of class " + type.getName());
            } finally {
                depthMeter.exit(type);
            }
        }
        return null;
    }

    @Override
    public <T, B> T createFromBuilder(final Class<T> type, final Class<B> builderType) {
        try {
            B builder = builderType.newInstance();
            List<Method> methods = getBuilderMethods(builderType);
            for (Method method: methods) {
                Object value = createFieldValue(new FieldInstance(method, 0));
                method.invoke(builder, value);
            }
            Method buildMethod = getBuildMethod(type, builderType);
            if (buildMethod == null) {
                throw new IllegalArgumentException("Cannot find build method in builder class " + builderType.getName());
            }
            return (T) buildMethod.invoke(builder);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new IllegalArgumentException("Cannot create instance of class " + type.getName());
        }
    }

    @Override
    public <T> List<T> createFromPrototype(Class<T> type, T prototype) {
        try {
            List<T> result = new ArrayList<>();

            List<Field> fields = ExtendedRandomUtils.getFields(type);
            ExtendedRandomUtils.setAccesible(fields);
            for (Field reference: fields) {
                T instance = type.newInstance();
                for (Field field: fields) {
                    Object orgValue = field.get(prototype);
                    Object value = reference.equals(field) ? createFieldValue(new FieldInstance(field), orgValue) : orgValue;
                    field.set(instance, value);
                }
                result.add(instance);
            }
            return result;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IllegalArgumentException("Cannot create instance of class " + type.getName());
        }
    }

    private Object createFieldValue(final FieldInstance field, Object antiValue) {
        for (int i = 0; i < 10; i++) { // Avoid infinite loop when the type has just a single value.
            Object result = createFieldValue(field);
            if (!Objects.equals(antiValue, result)) {
                return result;
            }
        }
        return null;
    }

    private Object createFieldValue(final FieldInstance field) {
        Randomizer randomizer = registry.get(field);
        if (randomizer != null) {
            return randomizer.nextRandomValue();
        }
        return create(field.getType());
    }

    private <T> List<Method> getBuilderMethods(final Class<T> builderType) {
        List<Method> result = new ArrayList<>();
        for (Class current = builderType; current != Object.class; current = current.getSuperclass()) {
            for (Method method: current.getDeclaredMethods()) {
                if (method.getReturnType().isAssignableFrom(builderType)) {
                    result.add(method);
                }
            }
        }
        return result;
    }

    private <T, B> Method getBuildMethod(Class<T> type, Class<B> builderType) {
        for (Class current = builderType; current != Object.class; current = current.getSuperclass()) {
            for (Method method: current.getDeclaredMethods()) {
                if (method.getReturnType().equals(type) && method.getName().equals("build")) {
                    return method;
                }
            }
        }
        return null;
    }
}
