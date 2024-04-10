package com.example.annovalidation;

import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;

@Component
public class Person {
    @Email
    private String email;
    @Max(value = 150)
    @Min(value = 0)
    private int age;

    @NotNull
    private String name;

    @NotBlank
    private String hobby;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
