package com.example.demo3.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //自动生成javaBean属性的getter/setter方法
@NoArgsConstructor //自动生成无参构造器
@AllArgsConstructor//自动生成全参构造器
public class Dog {
    private String name;
    private Integer age;
}
