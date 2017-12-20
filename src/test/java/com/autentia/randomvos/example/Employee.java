package com.autentia.randomvos.example;

public class Employee extends Entity {

    private int age;

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + age;
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
        Employee other = (Employee) obj;
        if (age != other.age) {
            return false;
        }
        return true;
    }

    public static class Builder extends Entity.Builder<Builder, Employee> {

        public Builder() {
            entity = new Employee();
        }

        public Builder withAge(final int age) {
            entity.setAge(age);
            return this;
        }

        @Override
        public Employee build() {
            return entity;
        }
    }
}
