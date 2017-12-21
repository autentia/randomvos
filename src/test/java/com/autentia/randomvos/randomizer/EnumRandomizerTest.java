package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;
import com.autentia.randomvos.example.Company;
import com.autentia.randomvos.example.CompanyType;
import com.autentia.randomvos.internal.ObjectPlaceholder;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class EnumRandomizerTest {

    private final ExtendedRandom random = mock(ExtendedRandom.class);

    @Test
    public void returnNullWhenNotApplicable() throws Exception {
        ObjectPlaceholder placeholder = ObjectPlaceholder.forField(Company.class.getDeclaredField("employees"));

        EnumRandomizer sut = new EnumRandomizer();
        sut.init(random);
        EnumRandomizer<CompanyType> result = sut.cloneIfApplicable(placeholder);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void returnNextRandomValue() throws Exception {
        doReturn(1).when(random).nextInt(anyInt());
        ObjectPlaceholder placeholder = ObjectPlaceholder.forField(Company.class.getDeclaredField("type"));

        EnumRandomizer prototype = new EnumRandomizer();
        prototype.init(random);
        EnumRandomizer<CompanyType> sut = prototype.cloneIfApplicable(placeholder);

        CompanyType result = sut.nextRandomValue();

        assertThat(result, is(CompanyType.INCORPORATED));
    }
}
