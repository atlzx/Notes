package com.springboot.example.springbootstarterrobot.autoconfiguration;

import com.springboot.example.springbootstarterrobot.controller.RobotController;
import com.springboot.example.springbootstarterrobot.properties.RobotProperties;
import com.springboot.example.springbootstarterrobot.service.RobotService;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({RobotProperties.class, RobotService.class, RobotController.class})
public class RobotAutoConfiguration {

}
