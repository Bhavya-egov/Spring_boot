package com.example.AssignmentSpringBootApplication;

public class UserClass {
    private int id;
    private String name,gender,phone_no,address;

    public UserClass() {
    }

    public UserClass(int id, String name, String gender, String phone_no, String address) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone_no = phone_no;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
