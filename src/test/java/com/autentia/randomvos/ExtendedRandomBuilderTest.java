package com.autentia.randomvos;

import com.autentia.randomvos.randomizer.Randomizer;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ExtendedRandomBuilderTest {

    private final ExtendedRandomSettings settings = new ExtendedRandomSettings();
    private final RandomizerRegistry registry = mock(RandomizerRegistry.class);

    private final ExtendedRandomBuilder sut = new ExtendedRandomBuilder(settings, registry);

    @Test
    public void changeSettings() {
        sut.withDepth(1001);
        sut.withMinCollectionSize(1002);
        sut.withMaxCollectionSize(1003);
        sut.withMinStringLength(1004);
        sut.withMaxStringLength(1005);

        assertThat(settings.getDepth(), is(1001));
        assertThat(settings.getMinCollectionSize(), is(1002));
        assertThat(settings.getMaxCollectionSize(), is(1003));
        assertThat(settings.getMinStringLength(), is(1004));
        assertThat(settings.getMaxStringLength(), is(1005));
    }

    @Test
    public void addRandomizer() {
        RandomizerSelector selector = mock(RandomizerSelector.class);
        Randomizer randomizer = mock(Randomizer.class);
        ArgumentCaptor<RandomizerSelector> selectorCaptor = ArgumentCaptor.forClass(RandomizerSelector.class);
        ArgumentCaptor<Randomizer> randomizerCaptor = ArgumentCaptor.forClass(Randomizer.class);

        sut.addRandomizer(selector, randomizer);

        verify(registry).put(selectorCaptor.capture(), randomizerCaptor.capture());
        assertThat(selectorCaptor.getValue(), is(selector));
        assertThat(randomizerCaptor.getValue(), is(randomizer));
    }
}
