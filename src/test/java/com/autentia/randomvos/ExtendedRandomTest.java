package com.autentia.randomvos;

import com.autentia.randomvos.example.Department;
import java.util.List;
import org.junit.Test;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Integration test!
 */
public class ExtendedRandomTest {

    @Test
    public void createRecursiveObject() {
        ExtendedRandom sut = new ExtendedRandomBuilder().build();

        Department result = sut.nextObject(Department.class);

        assertThat(result, is(not(nullValue())));
    }

    @Test
    public void createRecursiveObjectWithBuilder() {
        ExtendedRandom sut = new ExtendedRandomBuilder().build();

        Department result = sut.nextObjectFromBuilder(Department.class, Department.Builder.class);

        assertThat(result, is(not(nullValue())));
    }

    @Test
    public void createRecursiveObjectsFromPrototype() {
        ExtendedRandom sut = new ExtendedRandomBuilder().build();

        Department prototype = sut.nextObject(Department.class);
        List<Department> result = sut.nextObjectsFromPrototype(Department.class, prototype);

        assertThat(result, hasSize(3));
    }
}
