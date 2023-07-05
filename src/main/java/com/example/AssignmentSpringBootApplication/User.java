package com.example.AssignmentSpringBootApplication;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

public class User {
    private UUID id;
    private boolean active;
    private String name,gender,phone_no;
    private Address address;
    private long createdTime;
    public User() {
        this.id=UUID.randomUUID();
        this.createdTime= Instant.now().getEpochSecond();
    }

//    private int generateRandomId() {
//        Random random = new Random();
//        return random.nextInt(1000000);
//    }

    public User(UUID id,String name, String gender, String phone_no, Address address, Boolean active) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.gender = gender;
        this.phone_no = phone_no;
        this.address = address;
        this.active=active;
        this.createdTime= Instant.now().getEpochSecond();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(long createdTime) {
        this.createdTime = createdTime;
    }


}
