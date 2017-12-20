package com.autentia.randomvos;

import com.autentia.randomvos.example.Company;
import com.autentia.randomvos.example.CompanyType;
import com.autentia.randomvos.internal.FieldInstance;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class FieldDescriptorTest {

    @Test
    public void newInstanceWithTypeOnly() {
        FieldDescriptor result = new FieldDescriptor<>(String.class, null, null);

        assertThat(result.getType(), is(equalTo((Class) String.class)));
        assertThat(result.getFieldName(), is(nullValue()));
        assertThat(result.getContainingClass(), is(nullValue()));
    }

    @Test
    public void newInstanceWithFieldNameOnly() {
        FieldDescriptor result = new FieldDescriptor<>(null, "test", null);

        assertThat(result.getType(), is(nullValue()));
        assertThat(result.getFieldName(), is("test"));
        assertThat(result.getContainingClass(), is(nullValue()));
    }

    @Test
    public void newInstanceWithContainingClassOnly() {
        FieldDescriptor result = new FieldDescriptor<>(null, null, String.class);

        assertThat(result.getType(), is(nullValue()));
        assertThat(result.getFieldName(), is(nullValue()));
        assertThat(result.getContainingClass(), is(equalTo((Class) String.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWithoutAnyInfo() {
        FieldDescriptor result = new FieldDescriptor<>(null, null, null);

        fail("Result: " + result);
    }

    @Test
    public void matchType() throws Exception {
        FieldDescriptor fieldDescriptor = new FieldDescriptor<>(CompanyType.class, null, null);
        FieldInstance field = new FieldInstance(CompanyType.class, "type", Company.class);

        boolean result = fieldDescriptor.matches(field);

        assertThat(result, is(true));
    }

    @Test
    public void matchName() throws Exception {
        FieldDescriptor fieldDescriptor = new FieldDescriptor<>(null, "type", null);
        FieldInstance field = new FieldInstance(CompanyType.class, "type", Company.class);

        boolean result = fieldDescriptor.matches(field);

        assertThat(result, is(true));
    }

    @Test
    public void matchContainingClass() throws Exception {
        FieldDescriptor fieldDescriptor = new FieldDescriptor<>(null, null, Company.class);
        FieldInstance field = new FieldInstance(CompanyType.class, "type", Company.class);

        boolean result = fieldDescriptor.matches(field);

        assertThat(result, is(true));
    }

    @Test
    public void dontMatchType() throws Exception {
        FieldDescriptor fieldDescriptor = new FieldDescriptor<>(String.class, null, null);
        FieldInstance field = new FieldInstance(CompanyType.class, "type", Company.class);

        boolean result = fieldDescriptor.matches(field);

        assertThat(result, is(false));
    }

    @Test
    public void dontMatchName() throws Exception {
        FieldDescriptor fieldDescriptor = new FieldDescriptor<>(null, "other", null);
        FieldInstance field = new FieldInstance(CompanyType.class, "type", Company.class);

        boolean result = fieldDescriptor.matches(field);

        assertThat(result, is(false));
    }

    @Test
    public void dontMatchContainingClass() throws Exception {
        FieldDescriptor fieldDescriptor = new FieldDescriptor<>(null, null, String.class);
        FieldInstance field = new FieldInstance(CompanyType.class, "type", Company.class);

        boolean result = fieldDescriptor.matches(field);

        assertThat(result, is(false));
    }
}
