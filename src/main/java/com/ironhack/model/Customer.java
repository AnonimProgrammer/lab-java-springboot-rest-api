package com.ironhack.model;

import java.util.UUID;

public class Customer {
    private final UUID id;
    private String name;
    private String email;
    private Integer age;
    private String address;

    public Customer(String name, String email, Integer age, String address) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.age = age;
        this.address = address;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
