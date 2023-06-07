package com.example.demo3.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="person") //和配置文件person前缀属性进行绑定
@Data //自动生成javaBean属性的getter/setter方法
@NoArgsConstructor //自动生成无参构造器
@AllArgsConstructor//自动生成全参构造器
public class Person {
    private String name;
    private Integer age;
    private Date birthday;
    private boolean like;

    private Child child;//嵌套对象

    private List<Dog> dogs;//数组(对象)
    private Map<String,Cat> cats;//标识Map
}
