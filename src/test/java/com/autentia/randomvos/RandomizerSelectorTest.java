package com.autentia.randomvos;

import com.autentia.randomvos.example.Company;
import com.autentia.randomvos.example.CompanyType;
import com.autentia.randomvos.internal.ObjectPlaceholder;
import java.lang.reflect.Field;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class RandomizerSelectorTest {

    private static Field companyField;

    @BeforeClass
    public static void setup() {
        for (Field field: ExtendedRandomUtils.getFields(Company.class)) {
            if (field.getName().equals("type")) {
                companyField = field;
                break;
            }
        }
    }

    @Test
    public void newInstanceWithTypeOnly() {
        RandomizerSelector result = new RandomizerSelector(String.class, null, null);

        assertThat(result.getType(), is(equalTo((Class) String.class)));
        assertThat(result.getName(), is(nullValue()));
        assertThat(result.getContainingClass(), is(nullValue()));
    }

    @Test
    public void newInstanceWithFieldNameOnly() {
        RandomizerSelector result = new RandomizerSelector(null, "test", null);

        assertThat(result.getType(), is(nullValue()));
        assertThat(result.getName(), is("test"));
        assertThat(result.getContainingClass(), is(nullValue()));
    }

    @Test
    public void newInstanceWithContainingClassOnly() {
        RandomizerSelector result = new RandomizerSelector(null, null, String.class);

        assertThat(result.getType(), is(nullValue()));
        assertThat(result.getName(), is(nullValue()));
        assertThat(result.getContainingClass(), is(equalTo((Class) String.class)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failWithoutAnyInfo() {
        RandomizerSelector result = new RandomizerSelector(null, null, null);

        fail("Result: " + result);
    }

    @Test
    public void matchType() throws Exception {
        RandomizerSelector selector = new RandomizerSelector(CompanyType.class, null, null);
        ObjectPlaceholder field = ObjectPlaceholder.forField(companyField);

        boolean result = selector.matches(field);

        assertThat(result, is(true));
    }

    @Test
    public void matchName() throws Exception {
        RandomizerSelector selector = new RandomizerSelector(null, "type", null);
        ObjectPlaceholder field = ObjectPlaceholder.forField(companyField);

        boolean result = selector.matches(field);

        assertThat(result, is(true));
    }

    @Test
    public void matchContainingClass() throws Exception {
        RandomizerSelector selector = new RandomizerSelector(null, null, Company.class);
        ObjectPlaceholder field = ObjectPlaceholder.forField(companyField);

        boolean result = selector.matches(field);

        assertThat(result, is(true));
    }

    @Test
    public void dontMatchType() throws Exception {
        RandomizerSelector selector = new RandomizerSelector(String.class, null, null);
        ObjectPlaceholder field = ObjectPlaceholder.forField(companyField);

        boolean result = selector.matches(field);

        assertThat(result, is(false));
    }

    @Test
    public void dontMatchName() throws Exception {
        RandomizerSelector selector = new RandomizerSelector(null, "other", null);
        ObjectPlaceholder field = ObjectPlaceholder.forField(companyField);

        boolean result = selector.matches(field);

        assertThat(result, is(false));
    }

    @Test
    public void dontMatchContainingClass() throws Exception {
        RandomizerSelector selector = new RandomizerSelector(null, null, String.class);
        ObjectPlaceholder field = ObjectPlaceholder.forField(companyField);

        boolean result = selector.matches(field);

        assertThat(result, is(false));
    }
}
