package com.autentia.randomvos.randomizer;

import com.autentia.randomvos.ExtendedRandom;
import com.autentia.randomvos.example.Company;
import com.autentia.randomvos.example.Employee;
import com.autentia.randomvos.internal.ObjectPlaceholder;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class ListRandomizerTest {

    private final ExtendedRandom random = mock(ExtendedRandom.class);

    @Test
    public void returnNullWhenNotApplicable() throws Exception {
        ObjectPlaceholder placeholder = ObjectPlaceholder.forField(Company.class.getDeclaredField("type"));

        ListRandomizer sut = new ListRandomizer(1, 1);
        sut.init(random);
        ListRandomizer<Employee> result = sut.cloneIfApplicable(placeholder);

        assertThat(result, is(nullValue()));
    }

    @Test
    public void returnNextRandomValue() throws Exception {
        Employee employee = mock(Employee.class);
        ArgumentCaptor<Type> typeCaptor = ArgumentCaptor.forClass(Type.class);
        doReturn(0).when(random).nextInt(anyInt());
        doReturn(employee).when(random).nextObject(typeCaptor.capture());
        ObjectPlaceholder placeholder = ObjectPlaceholder.forField(Company.class.getDeclaredField("employees"));

        ListRandomizer prototype = new ListRandomizer(1, 1);
        prototype.init(random);
        ListRandomizer<Employee> sut = prototype.cloneIfApplicable(placeholder);

        List<Employee> result = sut.nextRandomValue();

        assertThat(result, contains(employee));
        assertThat(typeCaptor.getValue(), is(placeholder.findActualTypeArguments().get(0)));
    }
}
