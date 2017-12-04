package es.cdelhoyo.valueobjecttester.example;

import java.util.List;

public class Company extends Entity{

	private List<Employee> employees;

	public List<Employee> getEmployees() {
		return employees;
	}

	private void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((employees == null) ? 0 : employees.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (employees == null) {
			if (other.employees != null)
				return false;
		} else if (!employees.equals(other.employees))
			return false;
		return true;
	}

	public static class Builder extends Entity.Builder<Builder, Company> {

        public Builder() {
            entity = new Company();
        }

        public Builder withEmployees(final List<Employee> employees) {
            entity.setEmployees(employees);
            return this;
        }

        @Override
        public Company build() {
            return entity;
        }
    }
}
