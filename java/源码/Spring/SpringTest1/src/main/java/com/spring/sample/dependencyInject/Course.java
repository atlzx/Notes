package com.spring.sample.dependencyInject;

public class Course {
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Course{" +
            "name='" + name + '\'' +
            '}';
    }
}
