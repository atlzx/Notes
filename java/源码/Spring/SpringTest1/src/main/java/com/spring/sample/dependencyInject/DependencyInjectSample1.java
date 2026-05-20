package com.spring.sample.dependencyInject;

public class DependencyInjectSample1 {
    private int age;
    private String name;
    private String description;
    private char gender;

    public DependencyInjectSample1(){

    }

    public DependencyInjectSample1(int a, String n, String d, char g) {
        this.age = a;
        this.name = n;
        this.description = d;
        this.gender = g;
    }

    @Override
    public String toString() {
        return "DependencyInjectSample1{" +
            "age=" + age +
            ", name=\"" + name + "\"" +
            ", description=" + description +
            ", gender='" + gender +
            "'}";
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }
}
