package es.cdelhoyo.valueobjecttester;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;

import es.cdelhoyo.valueobjecttester.example.Company;
import es.cdelhoyo.valueobjecttester.example.Employee;
import es.cdelhoyo.valueobjecttester.example.Entity;

public class RandomBuilderFillerTest {

	private final RandomObjectFiller randomObjectFiller = mock(RandomObjectFiller.class);
	private final RandomBuilderFiller sut = new RandomBuilderFiller(randomObjectFiller);

	@Test
	public void createObjectWithBuilderShouldReturnACompany() throws Exception {
		// ARRANGE
		Employee employee = mock(Employee.class);
		Long companyId = 1L;
		String companyName = "name";
		doReturn(employee).when(randomObjectFiller).createAndFillObject(Employee.class);
		doReturn(companyId).when(randomObjectFiller).createAndFillObject(Long.class);
		doReturn(companyName).when(randomObjectFiller).createAndFillObject(String.class);
		
		// ACT
		Company company = sut.createObjectWithBuilder(Company.class, Company.Builder.class);
		
		// ASSERT
		assertThat(company.getEmployees(), contains(employee));
		assertThat(company.getId(), equalTo(companyId));
		assertThat(company.getName(), equalTo(companyName));
		
	}

	@Test
	public void createObjectWithBuilderShouldReturnAEmployee() throws Exception {
		// ARRANGE
		int age = 25;
		Long employeeId = 1L;
		String employeeName = "name";
		doReturn(age).when(randomObjectFiller).createAndFillObject(int.class);
		doReturn(employeeId).when(randomObjectFiller).createAndFillObject(Long.class);
		doReturn(employeeName).when(randomObjectFiller).createAndFillObject(String.class);
		
		// ACT
		Employee employee = sut.createObjectWithBuilder(Employee.class, Employee.Builder.class);
		
		// ASSERT
		assertThat(employee.getAge(), equalTo(age));
		assertThat(employee.getId(), equalTo(employeeId));
		assertThat(employee.getName(), equalTo(employeeName));
		
	}
	
	@Test
	public void createWithObjectShouldCreateList() throws NoSuchMethodException, SecurityException, Exception {
		// ARRANGE
		Employee employee = mock(Employee.class);
		doReturn(employee).when(randomObjectFiller).createAndFillObject(Employee.class);
		
		// ACT
		Object object = sut.createWithObject(Company.Builder.class.getMethod("withEmployees", List.class));
		
		// ASSERT
		assertThat(object instanceof List, equalTo(true));
		List<Employee> list = (List) object;
		assertThat(list, contains(employee));
	}
	
	@Test
	public void createWithObjectShouldCreateSimpleObject() throws NoSuchMethodException, SecurityException, Exception {
		int age = 25;
		doReturn(age).when(randomObjectFiller).createAndFillObject(int.class);
		
		// ACT
		Object object = sut.createWithObject(Employee.Builder.class.getMethod("withAge", int.class));
		
		// ASSERT
		assertThat((Integer)object, equalTo(age));
	}
	
	@Test
	public void getBuildMethodsShouldReturnCompanyBuildMethod() throws NoSuchMethodException, SecurityException {
		// ARRANGE
		
		// ACT
		Method buildMethods = sut.getBuildMethod(Company.class, Company.Builder.class);
		
		// ASSERT
		assertThat(buildMethods, equalTo(Company.Builder.class.getMethod("build")));
		
	}

	@Test
	public void getBuildMethodsShouldReturnEmployeeBuildMethod() throws NoSuchMethodException, SecurityException {
		// ARRANGE
		
		// ACT
		Method buildMethods = sut.getBuildMethod(Employee.class, Employee.Builder.class);
		
		// ASSERT
		assertThat(buildMethods, equalTo(Employee.Builder.class.getMethod("build")));
		
	}
	
	@Test
	public void getWithBuilderMethodsShouldReturnCompanyMethods() throws NoSuchMethodException, SecurityException {
		// ARRANGE
		
		// ACT
		List<Method> withMethods = sut.getWithBuilderMethods(Company.Builder.class);
		
		// ASSERT
		assertThat(withMethods, contains(
				Company.Builder.class.getMethod("withEmployees", List.class), 
				Entity.Builder.class.getMethod("withId", Long.class), 
				Entity.Builder.class.getMethod("withName", String.class)));
		
	}

	@Test
	public void getWithBuilderMethodsShouldReturnEmployeeMethods() throws NoSuchMethodException, SecurityException {
		// ARRANGE
		
		// ACT
		List<Method> withMethods = sut.getWithBuilderMethods(Employee.Builder.class);
		
		// ASSERT
		assertThat(withMethods, contains(
				Employee.Builder.class.getMethod("withAge", int.class), 
				Entity.Builder.class.getMethod("withId", Long.class), 
				Entity.Builder.class.getMethod("withName", String.class)));
		
	}

}
