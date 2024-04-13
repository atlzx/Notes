package com.mybatis.example.hellomybatis.pojo;


import org.apache.ibatis.type.Alias;

@Alias(value = "mybatisteacher")
public class Teacher {
    private String teacherId;
    private String teacherName;
    private Integer teacherAge;

    @Override
    public String toString() {
        return "Teacher{" +
            "teacherId=" + teacherId +
            ", teacherName='" + teacherName + '\'' +
            ", teacherAge=" + teacherAge +
            '}';
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Integer getTeacherAge() {
        return teacherAge;
    }

    public void setTeacherAge(Integer teacherAge) {
        this.teacherAge = teacherAge;
    }
}
