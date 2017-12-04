package es.cdelhoyo.valueobjecttester;

import es.cdelhoyo.valueobjecttester.example.Company;

public class ValueObjectCompanyTest extends ValueObjectTest{

	@Override
	public Class<?> getValueObjecClass() throws Exception {
		return Company.class;
	}

	@Override
	public Class<?> getValueObjecBuilderClass() throws Exception {
		return Company.Builder.class;
	}

}
