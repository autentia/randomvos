package com.autentia.randomvos.example;

import java.util.List;

public class Department extends Entity {

    private List<Department> subdepartments;

    public List<Department> getSubdepartments() {
        return subdepartments;
    }

    private void setSubdepartments(List<Department> subdepartments) {
        this.subdepartments = subdepartments;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((subdepartments == null) ? 0 : subdepartments.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Department other = (Department) obj;
        if (subdepartments == null) {
            if (other.subdepartments != null) {
                return false;
            }
        } else if (!subdepartments.equals(other.subdepartments)) {
            return false;
        }
        return true;
    }

    public static class Builder extends Entity.Builder<Builder, Department> {

        public Builder() {
            entity = new Department();
        }

        public Builder withSubdepartments(final List<Department> subdepartments) {
            entity.setSubdepartments(subdepartments);
            return this;
        }
    }
}
