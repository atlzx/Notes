package com.spring.sample.dependencyInject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Student {
    private String name;
    private List<Teacher> teacherList;
    private Teacher[] teachers;
    private Map<Teacher, Course> courseMap;

    public Student() {
    }

    public Student(String name, List<Teacher> teacherList, Teacher[] teachers, Map<Teacher, Course> courseMap) {
        this.name = name;
        this.teacherList = teacherList;
        this.teachers = teachers;
        this.courseMap = courseMap;
    }

    @Override
    public String toString() {
        return "Student{\n" +
            "name='" + name + "\n" +
            "teacherList=" + teacherList +
            "\nteachers=" + Arrays.toString(teachers) +
            "\ncourseMap=" + courseMap +
            '}';
    }

//    @Override
//    public String toString() {
//        String str1 = "studentName=" + this.name + "\n";
//        String str2 = "teacherList=[";
//        String str3 = "courseMap=[";
//        String str4 = "teachers=[";
//        for (var i : this.teacherList) {
//            str2 += i + ", ";
//        }
//        for (var i : this.courseMap.entrySet()) {
//            str3 += "{" + i.getKey() + ":" + i.getValue() + "}, ";
//        }
//        for (int i = 0; i < this.teachers.length; i++) {
//            str4 += this.teachers[i] + ", ";
//        }
//        str2 += "]\n";
//        str3 += "]\n";
//        str4 += "]\n";
//        return str1 + str2 + str3 + str4;
//    }
}
