package es.cdelhoyo.valueobjecttester;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public abstract class ValueObjectTest {

	private Object a1;
	private Object a2;
	private List<Object> bs;
	private Object fromBuilder;

	private final ObjectFieldsFiller objectFieldsFiller = new ObjectFieldsFiller();
	private final RandomObjectFiller randomObjectFilter = new RandomObjectFiller(objectFieldsFiller);
	private final RandomBuilderFiller randomBuilderFiller = new RandomBuilderFiller(randomObjectFilter);

	private Class<?> valueObjecClass;
	private Class<?> valueObjecBuilderClass;
	
    public abstract Class<?> getValueObjecClass() throws Exception;
    public abstract Class<?> getValueObjecBuilderClass() throws Exception;
    
    @Before
    public void before() throws Exception {
		valueObjecClass = getValueObjecClass();
		valueObjecBuilderClass = getValueObjecBuilderClass();
		
    		List list = randomObjectFilter.createAndFillListWithTwoEqualsObjectsAndOthersWithOnlyOneDiferentField(valueObjecClass);
    		a1 = list.get(0);
    		list.remove(a1);
    		a2 = list.get(0);
    		list.remove(a2);
    		bs = list;
    		if(valueObjecBuilderClass!=null){
        		fromBuilder = randomBuilderFiller.createObjectWithBuilder(valueObjecClass, valueObjecBuilderClass);
    		}
    }

    @Test
    public void builderFillAllFields() throws Exception {
		if(fromBuilder!=null){
        		boolean result = objectFieldsFiller.objectHasAllFieldsFilled(fromBuilder);
        		assertThat(result, equalTo(true));
    		}
    }
    
    @Test
    public void equalObjects() {
        boolean result = a1.equals(a2);
        boolean instance = a1 == a2;
        int hashA1 = a1.hashCode();
        int hashA2 = a2.hashCode();

        assertThat("A1 equals A2", result, equalTo(true));
        assertThat("A2 not instace A1", instance, equalTo(false));
        assertThat("hashCode(A1) == hashCode(A2)", hashA1, equalTo(hashA2));
    }

    @Test
    public void notEqualsObjectsIfOneFieldIsDifferent() {
        int hashA = a1.hashCode();

        bs.add(new Object());
        bs.add(null);
        int i = 1;
        for (Object b: bs) {
            boolean result1 = a1.equals(b);
            boolean result2 = a2.equals(b);
            int hashB = b != null ? b.hashCode() : 0;

            assertThat(String.format("B%d equals A1", i), result1, equalTo(false));
            assertThat(String.format("B%d equals A2", i), result2, equalTo(false));
            assertThat(String.format("hashCode(B%d) == hashCode(Ax)", i), hashA, not(hashB));
            i++;
        }
    }
}
