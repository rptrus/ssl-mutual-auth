package com.alphastar.SSLServer.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

//Incoming rest message. don't really do anything other than log it for demonstration.
public class Person {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    String name;

    @Min(0)
    @Max(100)
    Integer age;

}
