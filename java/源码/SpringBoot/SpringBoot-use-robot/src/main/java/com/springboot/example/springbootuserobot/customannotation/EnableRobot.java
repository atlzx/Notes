package com.springboot.example.springbootuserobot.customannotation;


import com.springboot.example.springbootstarterrobot.autoconfiguration.RobotAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(RobotAutoConfiguration.class)
public @interface EnableRobot {

}
