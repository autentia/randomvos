package es.cdelhoyo.valueobjecttester;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import es.cdelhoyo.valueobjecttester.example.Company;
import es.cdelhoyo.valueobjecttester.example.Employee;
import es.cdelhoyo.valueobjecttester.example.Entity;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

public class ObjectFieldsFillerTest {

	private final ObjectFieldsFiller sut = new ObjectFieldsFiller();
	
	@Test
	public void getAllFilledFieldsShouldReturnAllCompanyFields() throws NoSuchFieldException, SecurityException {
		// ARRANGE
		
		// ACT
		List<Field> fields = sut.getAllFilledFields(Company.class);
		
		// ASSERT
		assertThat(fields, contains(
				Company.class.getDeclaredField("employees"), 
				Entity.class.getDeclaredField("id"), 
				Entity.class.getDeclaredField("name")));
		
	}

	@Test
	public void getAllFilledFieldsShouldReturnAllEmployeeFields() throws NoSuchFieldException, SecurityException {
		// ARRANGE
		
		// ACT
		List<Field> fields = sut.getAllFilledFields(Employee.class);
		
		// ASSERT
		assertThat(fields, contains(
				Employee.class.getDeclaredField("age"), 
				Entity.class.getDeclaredField("id"), 
				Entity.class.getDeclaredField("name")));
		
	}
	
	@Test
	public void objectHasAllFieldsFilledReturnsTrueWithAllCompanyFieldsFilled() throws IllegalArgumentException, IllegalAccessException {
		// ARRANGE
		Company company = new Company.Builder().withEmployees(new ArrayList<Employee>()).withId(1L).withName("name").build();
		
		// ACT
		boolean result = sut.objectHasAllFieldsFilled(company);
		
		// ASSERT
		assertThat(result, equalTo(true));
	}

	@Test
	public void objectHasAllFieldsFilledReturnsFalseWithIdCompanyNotFilled() throws IllegalArgumentException, IllegalAccessException {
		// ARRANGE
		Company company = new Company.Builder().withEmployees(new ArrayList<Employee>()).withName("name").build();
		
		// ACT
		boolean result = sut.objectHasAllFieldsFilled(company);
		
		// ASSERT
		assertThat(result, equalTo(false));
	}
}
