package com.autentia.randomvos;

import com.autentia.randomvos.randomizer.Randomizer;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExtendedRandomBuilderTest {

    private final ExtendedRandomSettings settings = new ExtendedRandomSettings();
    private final RandomizerRegistry registry = mock(RandomizerRegistry.class);

    @Test
    public void changeSettings() {
        FieldDescriptor field = new FieldDescriptor(Object.class, "class", null);
        ExtendedRandomBuilder sut = new ExtendedRandomBuilder(settings, registry);

        sut.withDepth(1001);
        sut.withMinCollectionSize(1002);
        sut.withMaxCollectionSize(1003);
        sut.withMinStringLength(1004);
        sut.withMaxStringLength(1005);
        sut.excludeFields(field);
        sut.excludeClasses(Object.class);

        assertThat(settings.getDepth(), is(1001));
        assertThat(settings.getMinCollectionSize(), is(1002));
        assertThat(settings.getMaxCollectionSize(), is(1003));
        assertThat(settings.getMinStringLength(), is(1004));
        assertThat(settings.getMaxStringLength(), is(1005));
        assertThat(settings.getExcludedFields(), contains(field));
        assertThat(settings.getExcludedClasses(), contains((Class) Object.class));
    }

    @Test
    public void addFieldRandomizer() {
        FieldDescriptor field = new FieldDescriptor(Object.class, "class", null);
        Randomizer randomizer = mock(Randomizer.class);
        ArgumentCaptor<FieldDescriptor> fieldCaptor = ArgumentCaptor.forClass(FieldDescriptor.class);
        ArgumentCaptor<Randomizer> randomizerCaptor = ArgumentCaptor.forClass(Randomizer.class);
        ExtendedRandomBuilder sut = new ExtendedRandomBuilder(settings, registry);

        sut.addFieldRandomizer(field, randomizer);

        verify(registry).putFieldRandomizer(fieldCaptor.capture(), randomizerCaptor.capture());
        assertThat(fieldCaptor.getValue(), is(field));
        assertThat(randomizerCaptor.getValue(), is(randomizer));
    }

    @Test
    public void addTypeRandomizer() {
        Randomizer randomizer = mock(Randomizer.class);
        ArgumentCaptor<Class> typeCaptor = ArgumentCaptor.forClass(Class.class);
        ArgumentCaptor<Randomizer> randomizerCaptor = ArgumentCaptor.forClass(Randomizer.class);
        ExtendedRandomBuilder sut = new ExtendedRandomBuilder(settings, registry);

        sut.addTypeRandomizer(Object.class, randomizer);

        verify(registry).putTypeRandomizer(typeCaptor.capture(), randomizerCaptor.capture());
        assertThat(typeCaptor.getValue(), is(equalTo((Class) Object.class)));
        assertThat(randomizerCaptor.getValue(), is(randomizer));
    }
}
