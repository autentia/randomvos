package com.autentia.randomvos.internal;

import com.autentia.randomvos.internal.DepthMeter;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class DepthMeterTest {

    @Test
    public void enterWhenNotMaxDepth() {
        DepthMeter sut = new DepthMeter(1);

        boolean result = sut.tryEnter(getClass());

        assertThat(result, is(true));
    }

    @Test
    public void notEnterWhenMaxDepth() {
        DepthMeter sut = new DepthMeter(1);

        sut.tryEnter(getClass());
        boolean result = sut.tryEnter(getClass());

        assertThat(result, is(false));
    }

    @Test
    public void reenterAfterExit() {
        DepthMeter sut = new DepthMeter(1);

        sut.tryEnter(getClass());
        sut.exit(getClass());
        boolean result = sut.tryEnter(getClass());

        assertThat(result, is(true));

    }

    @Test(expected = IllegalStateException.class)
    public void failWhenExitWihoutEnter() {
        DepthMeter sut = new DepthMeter(1);

        sut.exit(getClass());

        fail();
    }
}
