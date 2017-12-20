package com.autentia.randomvos.internal;

import com.autentia.randomvos.internal.FieldInstance;
import com.autentia.randomvos.example.Company;
import com.autentia.randomvos.example.CompanyType;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FieldInstanceTest {

    @Test
    public void createInstanceFromField() throws Exception {
        FieldInstance result = new FieldInstance(Company.class.getDeclaredField("type"));

        assertThat(result.getType(), is(equalTo((Class) CompanyType.class)));
        assertThat(result.getName(), is("type"));
        assertThat(result.getContainingClass(), is(equalTo((Class) Company.class)));
    }

    @Test
    public void createInstanceFromMethod() throws Exception {
        FieldInstance result = new FieldInstance(Company.Builder.class.getMethod("withType", CompanyType.class), 0);

        assertThat(result.getType(), is(equalTo((Class) CompanyType.class)));
        assertThat(result.getName(), is("withType"));
        assertThat(result.getContainingClass(), is(equalTo((Class) Company.Builder.class)));
    }
}
