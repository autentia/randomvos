package com.autentia.randomvos.internal;

import com.autentia.randomvos.ExtendedRandomSettings;
import com.autentia.randomvos.RandomizerRegistry;
import com.autentia.randomvos.example.Employee;
import com.autentia.randomvos.randomizer.Randomizer;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class RandomObjectCreatorImplTest {

    private final Randomizer<Integer> intRandomizer = mock(Randomizer.class);
    private final Randomizer<Long> longRandomizer = mock(Randomizer.class);
    private final Randomizer<String> stringRandomizer = mock(Randomizer.class);
    private final ExtendedRandomSettings settings = mock(ExtendedRandomSettings.class);
    private final RandomizerRegistry registry = mock(RandomizerRegistry.class);

    @Before
    public void setupTest() {
        doReturn(2).when(settings).getDepth();
        doReturn(intRandomizer).when(registry).get(ObjectPlaceholder.forType(Integer.TYPE));
        doReturn(intRandomizer).when(registry).get(ObjectPlaceholder.forType(Integer.class));
        doReturn(longRandomizer).when(registry).get(ObjectPlaceholder.forType(Long.TYPE));
        doReturn(longRandomizer).when(registry).get(ObjectPlaceholder.forType(Long.class));
        doReturn(stringRandomizer).when(registry).get(ObjectPlaceholder.forType(String.class));

        doReturn(10).doReturn(11).when(intRandomizer).nextRandomValue();
        doReturn(20L).doReturn(21L).when(longRandomizer).nextRandomValue();
        doReturn("30").doReturn("31").when(stringRandomizer).nextRandomValue();
    }

    @Test
    public void createSimple() {
        RandomObjectCreatorImpl sut = new RandomObjectCreatorImpl(settings, registry);

        int result = sut.create(Integer.TYPE);

        assertThat(result, is(10));
    }

    @Test
    public void createObject() {
        RandomObjectCreatorImpl sut = new RandomObjectCreatorImpl(settings, registry);

        Employee result = sut.create(Employee.class);

        assertThat(result.getAge(), is(10));
        assertThat(result.getId(), is(20L));
        assertThat(result.getName(), is("30"));
    }

    @Test
    public void createFromBuilder() {
        RandomObjectCreatorImpl sut = new RandomObjectCreatorImpl(settings, registry);

        Employee result = sut.createFromBuilder(Employee.class, Employee.Builder.class);

        assertThat(result.getAge(), is(10));
        assertThat(result.getId(), is(20L));
        assertThat(result.getName(), is("30"));
    }

    @Test
    public void createFromPrototype() {
        Employee employee = new Employee.Builder().withId(20L).withName("30").withAge(10).build();
        RandomObjectCreatorImpl sut = new RandomObjectCreatorImpl(settings, registry);

        List<Employee> result = sut.createFromPrototype(Employee.class, employee);

        assertThat(result, containsInAnyOrder(
            new Employee.Builder().withId(21L).withName("30").withAge(10).build(),
            new Employee.Builder().withId(20L).withName("31").withAge(10).build(),
            new Employee.Builder().withId(20L).withName("30").withAge(11).build()
        ));
    }
}
