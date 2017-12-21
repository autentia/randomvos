package com.autentia.randomvos.internal;

import com.autentia.randomvos.example.Company;
import com.autentia.randomvos.example.CompanyType;
import com.autentia.randomvos.example.Employee;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.Test;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class ObjectPlaceholderTest {

    @Test
    public void createInstanceFromType() {
        ObjectPlaceholder result = ObjectPlaceholder.forType(Integer.TYPE);

        assertThat(result.getName(), is(nullValue()));
        assertThat(result.getContainingClass(), is(nullValue()));
        assertThat(result.findClass(), is(equalTo((Class) Integer.TYPE)));
        assertThat(result.findActualTypeArguments(), is(nullValue()));
    }

    @Test
    public void createInstanceFromParamType() throws Exception {
        ObjectPlaceholder result = ObjectPlaceholder.forType(Company.class.getDeclaredField("employees").getGenericType());

        assertThat(result.getName(), is(nullValue()));
        assertThat(result.getContainingClass(), is(nullValue()));
        assertThat(result.findClass(), is(equalTo((Class) List.class)));
        assertThat(result.findActualTypeArguments(), contains((Type) Employee.class));
    }

    @Test
    public void createInstanceFromField() throws Exception {
        ObjectPlaceholder result = ObjectPlaceholder.forField(Company.class.getDeclaredField("type"));

        assertThat(result.getName(), is("type"));
        assertThat(result.getContainingClass(), is(equalTo((Class) Company.class)));
        assertThat(result.findClass(), is(equalTo((Class) CompanyType.class)));
        assertThat(result.findActualTypeArguments(), is(nullValue()));
    }

    @Test
    public void createInstanceFromParam() throws Exception {
        ObjectPlaceholder result = ObjectPlaceholder.forParam(Company.Builder.class.getDeclaredMethod("withType", CompanyType.class), 0);

        assertThat(result.getName(), is("withType"));
        assertThat(result.getContainingClass(), is(equalTo((Class) Company.Builder.class)));
        assertThat(result.findClass(), is(equalTo((Class) CompanyType.class)));
        assertThat(result.findActualTypeArguments(), is(nullValue()));
    }

    @Test
    public void equalsForType() {
        ObjectPlaceholder one = ObjectPlaceholder.forType(Integer.TYPE);
        ObjectPlaceholder two = ObjectPlaceholder.forType(Integer.TYPE);

        boolean result = one.equals(two);

        assertThat(result, is(true));
    }

    @Test
    public void equalsForField() throws Exception {
        ObjectPlaceholder one = ObjectPlaceholder.forField(Company.class.getDeclaredField("type"));
        ObjectPlaceholder two = ObjectPlaceholder.forField(Company.class.getDeclaredField("type"));

        boolean result = one.equals(two);

        assertThat(result, is(true));
    }

    @Test
    public void equalsForParam() throws Exception {
        ObjectPlaceholder one = ObjectPlaceholder.forParam(Company.Builder.class.getDeclaredMethod("withType", CompanyType.class), 0);
        ObjectPlaceholder two = ObjectPlaceholder.forParam(Company.Builder.class.getDeclaredMethod("withType", CompanyType.class), 0);

        boolean result = one.equals(two);

        assertThat(result, is(true));
    }
}
