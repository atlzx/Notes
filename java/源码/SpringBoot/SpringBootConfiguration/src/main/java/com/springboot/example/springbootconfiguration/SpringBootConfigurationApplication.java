package com.springboot.example.springbootconfiguration;

import com.springboot.example.springbootconfiguration.pojo.Cat;
import com.springboot.example.springbootconfiguration.pojo.Dog;
import com.springboot.example.springbootconfiguration.pojo.People;
import com.springboot.example.springbootconfiguration.pojo.Sheep;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
public class SpringBootConfigurationApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(SpringBootConfigurationApplication.class);
        // 使用代码的方式配置banner
        springApplication.setBannerMode(Banner.Mode.LOG);
        /*
            也可以通过SpringApplicationBuilder来进行批量的配置

            new SpringApplicationBuilder()
            .sources(SpringBootConfigurationApplication.class)
            .main(SpringBootConfigurationApplication.class)
            .bannerMode(Banner.Mode.LOG)
            .run(args);
         */

        var context=springApplication.run(args);
        test(context);
    }

    public static void test(ApplicationContext context){
        try{
            Cat cat = context.getBean(Cat.class);
            log.info("Cat:{}",cat);
        }catch(Exception e){

        }

        try{
            Dog dog = context.getBean(Dog.class);
            log.info("Dog:{}",dog);
        }catch(Exception e){

        }

        try{
            Sheep sheep = context.getBean(Sheep.class);
            log.info("Sheep:{}",sheep);
        }catch(Exception e){

        }

        try{
            People people = context.getBean(People.class);


            log.info("People:{}",people);
        }catch(Exception e){

        }
    }

}
