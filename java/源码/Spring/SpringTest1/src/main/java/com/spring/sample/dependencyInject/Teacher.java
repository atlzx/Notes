package com.spring.sample.dependencyInject;

public class Teacher {
    private String name;
    private int age;
    private Course course;

    public Teacher() {

    }

    public Teacher(String name, int age, Course course) {
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Teacher{" +
            "name='" + name + '\'' +
            ", age=" + age +
            ", course=" + course +
            '}';
    }
}
