package com.example.demo3;

import com.example.demo3.dao.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Demo3Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ioc = SpringApplication.run(Demo3Application.class, args);
        Person person = ioc.getBean(Person.class);
        System.out.println("person:" + person);
    }

}
