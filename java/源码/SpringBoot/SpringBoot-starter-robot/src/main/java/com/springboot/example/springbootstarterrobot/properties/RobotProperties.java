package com.springboot.example.springbootstarterrobot.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "robot")
@Component
@Data
public class RobotProperties {
    private String name;
    private Integer age;
    private String email;
}
