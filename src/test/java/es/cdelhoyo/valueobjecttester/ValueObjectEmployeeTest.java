package es.cdelhoyo.valueobjecttester;

import es.cdelhoyo.valueobjecttester.example.Employee;

public class ValueObjectEmployeeTest extends ValueObjectTest{

	@Override
	public Class<?> getValueObjecClass() throws Exception {
		return Employee.class;
	}

	@Override
	public Class<?> getValueObjecBuilderClass() throws Exception {
		return Employee.Builder.class;
	}

}
