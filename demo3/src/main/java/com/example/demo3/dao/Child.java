package com.example.demo3.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data //自动生成javaBean属性的getter/setter方法
@NoArgsConstructor //自动生成无参构造器
@AllArgsConstructor//自动生成全参构造器
public class Child {
    private String name;
    private Integer age;
    private Date birthday;
    private Boolean like;
    private List<String> text;//数组
}
