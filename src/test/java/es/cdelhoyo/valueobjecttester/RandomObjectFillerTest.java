package es.cdelhoyo.valueobjecttester;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import es.cdelhoyo.valueobjecttester.example.Company;
import es.cdelhoyo.valueobjecttester.example.CompanyType;
import es.cdelhoyo.valueobjecttester.example.Employee;
import es.cdelhoyo.valueobjecttester.example.Entity;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

public class RandomObjectFillerTest {

	private final ObjectFieldsFiller objectFieldsFiller = mock(ObjectFieldsFiller.class);
	private final RandomObjectFiller sut = new RandomObjectFiller(objectFieldsFiller);
	
	@Before
	public void before() throws NoSuchFieldException, SecurityException {
		Field fieldAge = Employee.class.getDeclaredField("age");
		fieldAge.setAccessible(true);
		Field fieldId = Entity.class.getDeclaredField("id");
		fieldId.setAccessible(true);
		Field fieldName = Entity.class.getDeclaredField("name");
		fieldName.setAccessible(true);
		List<Field> empoyeeFields = Arrays.asList(fieldAge, fieldId, fieldName);
		doReturn(empoyeeFields).when(objectFieldsFiller).getAllFilledFields(Employee.class);
	}
	
	@Test
	public void createAndFillListWithTwoEqualsObjectsAndOthersWithOnlyOneDiferentFieldReturnsTheList() throws Exception {
		// ARRANGE
		
		// ACT
		List<Employee> employees = sut.createAndFillListWithTwoEqualsObjectsAndOthersWithOnlyOneDiferentField(Employee.class);
		
		// ASSERT
		assertThat(employees.get(0).getAge(), equalTo(employees.get(1).getAge()));
		assertThat(employees.get(0).getId(), equalTo(employees.get(1).getId()));
		assertThat(employees.get(0).getName(), equalTo(employees.get(1).getName()));
		assertThat(employees.get(0), equalTo(employees.get(1)));
		assertThat(employees.get(0) == employees.get(1), equalTo(false));
		
		assertThat(employees.get(2).getAge(), not(employees.get(0).getAge()));
		assertThat(employees.get(2).getId(), equalTo(employees.get(0).getId()));
		assertThat(employees.get(2).getName(), equalTo(employees.get(0).getName()));
		
		assertThat(employees.get(3).getAge(), equalTo(employees.get(0).getAge()));
		assertThat(employees.get(3).getId(), not(employees.get(0).getId()));
		assertThat(employees.get(3).getName(), equalTo(employees.get(0).getName()));
		
		assertThat(employees.get(4).getAge(), equalTo(employees.get(0).getAge()));
		assertThat(employees.get(4).getId(), equalTo(employees.get(0).getId()));
		assertThat(employees.get(4).getName(), not(employees.get(0).getName()));
	}

	@Test
	public void createOtherInstanceOfSameObjectReturnsOtherEqualsInstance() throws Exception {
		// ARRANGE
		Employee originalEmployee = new Employee.Builder()
			.withAge(25)
			.withId(1L)
			.withName("name")
			.build();
		
		// ACT
		Employee employee = sut.createOtherInstanceOfSameObject(Employee.class, originalEmployee);
		
		// ASSERT
		assertThat(employee.getAge(), equalTo(originalEmployee.getAge()));
		assertThat(employee.getId(), equalTo(originalEmployee.getId()));
		assertThat(employee.getName(), equalTo(originalEmployee.getName()));
		assertThat(employee, equalTo(originalEmployee));
		assertThat(employee == originalEmployee, equalTo(false));
		
	}
	

	@Test
	public void createListOfObjectsWithOneDifferentPropertyReturnsTheList() throws InstantiationException, IllegalAccessException, Exception {
		// ARRANGE
		Employee originalEmployee = new Employee.Builder()
			.withAge(25)
			.withId(1L)
			.withName("name")
			.build();
		
		// ACT
		List<Employee> employees = sut.createListOfObjectsWithOneDifferentProperty(Employee.class, originalEmployee);
		
		// ASSERT
		assertThat(employees.get(0).getAge(), not(originalEmployee.getAge()));
		assertThat(employees.get(0).getId(), equalTo(originalEmployee.getId()));
		assertThat(employees.get(0).getName(), equalTo(originalEmployee.getName()));
		
		assertThat(employees.get(1).getAge(), equalTo(originalEmployee.getAge()));
		assertThat(employees.get(1).getId(), not(originalEmployee.getId()));
		assertThat(employees.get(1).getName(), equalTo(originalEmployee.getName()));
		
		assertThat(employees.get(2).getAge(), equalTo(originalEmployee.getAge()));
		assertThat(employees.get(2).getId(), equalTo(originalEmployee.getId()));
		assertThat(employees.get(2).getName(), not(originalEmployee.getName()));
	}
	
	@Test
	public void getDifferentRandomValueForFieldRetunsDiferentValue() throws Exception {
		// ARRANGE
		int originalAge = 25;
		Field fieldAge = Employee.class.getDeclaredField("age");
		fieldAge.setAccessible(true);
		Map<Class, Integer> repetedClasses = new HashMap();
		
		// ACT
		int diferentAge = (int)sut.getDifferentRandomValueForField(fieldAge, 25, repetedClasses);
		
		// ASSERT
		assertThat(diferentAge, not(originalAge));
	}
	
	@Test
	public void createAndFillObjectCallsCreateAndFillWithNewHashMap() throws Exception {
		// ARRANGE
		RandomObjectFiller spySut = spy(sut);
		doReturn(null).when(spySut).createAndFill(Employee.class, new HashMap());
		
		// ACT
		spySut.createAndFillObject(Employee.class);
		
		// ASSERT
		verify(spySut).createAndFill(Employee.class, new HashMap());
	}
	
	@Test
	public void createAndFillRepeatClassReturnsNull() throws Exception {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		repetedClasses.put(Employee.class, 4);
		
		// ACT
		Object result = sut.createAndFill(Employee.class, repetedClasses);
		
		// ASSERT
		assertThat(result, nullValue());
	}

	@Test
	public void createAndFillSimpleClassReturnsThatObjectOfThatClass() throws Exception {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		
		// ACT
		Object result = sut.createAndFill(Integer.class, repetedClasses);
		
		// ASSERT
		assertThat(result instanceof Integer, equalTo(true));
	}

	@Test
	public void createAndFillComplexClassReturnsThatObjectOfThatClass() throws Exception {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		
		// ACT
		Object result = sut.createAndFill(Employee.class, repetedClasses);
		
		// ASSERT
		assertThat(result instanceof Employee, equalTo(true));
	}
	
	@Test
	public void getRandomValueForFieldReturnsValue() throws Exception {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		
		// ACT
		Object result = sut.getRandomValueForField(Employee.class.getDeclaredField("age"), repetedClasses);
		
		// ASSERT
		assertThat(result instanceof Integer, equalTo(true));
	}
	
	@Test
	public void getRandomValueForFieldReturnsListValue() throws Exception {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		
		// ACT
		Object result = sut.getRandomValueForField(Company.class.getDeclaredField("employees"), repetedClasses);
		
		// ASSERT
		assertThat(result instanceof List, equalTo(true));
		assertThat(((List)result).get(0) instanceof Employee, equalTo(true));
	}
	
	@Test
	public void getRandomValueForClassCallCreateAndFillWithComplexClasses() throws Exception {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		RandomObjectFiller spySut = spy(sut);
		
		// ACT
		Object result = spySut.getRandomValueForClass(Employee.class, repetedClasses);
		
		// ASSERT
		verify(spySut).createAndFill(Employee.class, repetedClasses);
		assertThat(result instanceof Employee, equalTo(true));
	}
	
	@Test
	public void getRandomValueForClassGenerateSimpleClassOrEnum() throws Exception {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		
		// ACT
		Object resultInt = sut.getRandomValueForClass(int.class, repetedClasses);
		Object resultInteger = sut.getRandomValueForClass(Integer.class, repetedClasses);
		Object resultLongPrim = sut.getRandomValueForClass(long.class, repetedClasses);
		Object resultLong = sut.getRandomValueForClass(Long.class, repetedClasses);
		Object resultDoublePrim = sut.getRandomValueForClass(double.class, repetedClasses);
		Object resultDouble = sut.getRandomValueForClass(Double.class, repetedClasses);
		Object resultFloatPrim = sut.getRandomValueForClass(float.class, repetedClasses);
		Object resultFloat = sut.getRandomValueForClass(Float.class, repetedClasses);
		Object resultBooleanPrim = sut.getRandomValueForClass(boolean.class, repetedClasses);
		Object resultBoolean = sut.getRandomValueForClass(Boolean.class, repetedClasses);
		Object resultString = sut.getRandomValueForClass(String.class, repetedClasses);
		Object resultBigInteger = sut.getRandomValueForClass(BigInteger.class, repetedClasses);
		Object resultCompanyType = sut.getRandomValueForClass(CompanyType.class, repetedClasses);
		
		// ASSERT
		assertThat(resultInt instanceof Integer , equalTo(true));
		assertThat(resultInteger instanceof Integer, equalTo(true));
		assertThat(resultLongPrim instanceof Long, equalTo(true));
		assertThat(resultLong instanceof Long, equalTo(true));
		assertThat(resultDoublePrim instanceof Double, equalTo(true));
		assertThat(resultDouble instanceof Double, equalTo(true));
		assertThat(resultFloatPrim instanceof Float, equalTo(true));
		assertThat(resultFloat instanceof Float, equalTo(true));
		assertThat(resultBooleanPrim instanceof Boolean, equalTo(true));
		assertThat(resultBoolean instanceof Boolean, equalTo(true));
		assertThat(resultString instanceof String, equalTo(true));
		assertThat(resultBigInteger instanceof BigInteger, equalTo(true));
		assertThat(resultCompanyType instanceof CompanyType, equalTo(true));
	}
	
	@Test
	public void checkRepetedClassAndAddOneHas4AtMostAndReturnsTrue() {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		repetedClasses.put(Employee.class, 4);
		
		// ACT
		boolean result = sut.checkRepetedClassAndAddOne(Employee.class, repetedClasses);
		
		// ASSERT
		assertThat(result, equalTo(true));
	}

	@Test
	public void checkRepetedClassAndAddOneHasAddOneIfItHasLessThan4AndReturnFalse() {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		repetedClasses.put(Employee.class, 3);
		
		// ACT
		boolean result = sut.checkRepetedClassAndAddOne(Employee.class, repetedClasses);
		
		// ASSERT
		assertThat(result, equalTo(false));
		assertThat(repetedClasses.get(Employee.class), equalTo(4));
	}

	@Test
	public void checkRepetedClassAndAddOneHasAddClassIfItHasntAndReturnFalse() {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		
		// ACT
		boolean result = sut.checkRepetedClassAndAddOne(Employee.class, repetedClasses);
		
		// ASSERT
		assertThat(result, equalTo(false));
		assertThat(repetedClasses.get(Employee.class), equalTo(1));
	}

	@Test
	public void checkRepetedClassAndAddOneDontAddSimpleClassOrListOrEnumAndReturnFalse() {
		// ARRANGE
		Map<Class, Integer> repetedClasses = new HashMap();
		
		// ACT
		boolean resultInt = sut.checkRepetedClassAndAddOne(int.class, repetedClasses);
		boolean resultInteger = sut.checkRepetedClassAndAddOne(Integer.class, repetedClasses);
		boolean resultLongPrim = sut.checkRepetedClassAndAddOne(long.class, repetedClasses);
		boolean resultLong = sut.checkRepetedClassAndAddOne(Long.class, repetedClasses);
		boolean resultDoublePrim = sut.checkRepetedClassAndAddOne(double.class, repetedClasses);
		boolean resultDouble = sut.checkRepetedClassAndAddOne(Double.class, repetedClasses);
		boolean resultFloatPrim = sut.checkRepetedClassAndAddOne(float.class, repetedClasses);
		boolean resultFloat = sut.checkRepetedClassAndAddOne(Float.class, repetedClasses);
		boolean resultBooleanPrim = sut.checkRepetedClassAndAddOne(boolean.class, repetedClasses);
		boolean resultBoolean = sut.checkRepetedClassAndAddOne(Boolean.class, repetedClasses);
		boolean resultString = sut.checkRepetedClassAndAddOne(String.class, repetedClasses);
		boolean resultBigInteger = sut.checkRepetedClassAndAddOne(BigInteger.class, repetedClasses);
		boolean resultList = sut.checkRepetedClassAndAddOne(List.class, repetedClasses);
		boolean resultEnum = sut.checkRepetedClassAndAddOne(CompanyType.class, repetedClasses);
		
		// ASSERT
		assertThat(resultInt, equalTo(false));
		assertThat(repetedClasses.containsKey(int.class), equalTo(false));
		assertThat(resultInteger, equalTo(false));
		assertThat(repetedClasses.containsKey(Integer.class), equalTo(false));
		assertThat(resultLongPrim, equalTo(false));
		assertThat(repetedClasses.containsKey(long.class), equalTo(false));
		assertThat(resultLong, equalTo(false));
		assertThat(repetedClasses.containsKey(Long.class), equalTo(false));
		assertThat(resultDoublePrim, equalTo(false));
		assertThat(repetedClasses.containsKey(double.class), equalTo(false));
		assertThat(resultDouble, equalTo(false));
		assertThat(repetedClasses.containsKey(Double.class), equalTo(false));
		assertThat(resultFloatPrim, equalTo(false));
		assertThat(repetedClasses.containsKey(double.class), equalTo(false));
		assertThat(resultFloat, equalTo(false));
		assertThat(repetedClasses.containsKey(Float.class), equalTo(false));
		assertThat(resultBooleanPrim, equalTo(false));
		assertThat(repetedClasses.containsKey(double.class), equalTo(false));
		assertThat(resultBoolean, equalTo(false));
		assertThat(repetedClasses.containsKey(Boolean.class), equalTo(false));
		assertThat(resultString, equalTo(false));
		assertThat(repetedClasses.containsKey(String.class), equalTo(false));
		assertThat(resultBigInteger, equalTo(false));
		assertThat(repetedClasses.containsKey(BigInteger.class), equalTo(false));
		assertThat(resultList, equalTo(false));
		assertThat(repetedClasses.containsKey(List.class), equalTo(false));
		assertThat(resultEnum, equalTo(false));
		assertThat(repetedClasses.containsKey(Enum.class), equalTo(false));
	}
}
