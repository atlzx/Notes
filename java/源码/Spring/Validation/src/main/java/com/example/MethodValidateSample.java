package com.example;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class MethodValidateSample {
    public String validate(@NotNull @Valid User user){
        return user.toString();
    }
}
