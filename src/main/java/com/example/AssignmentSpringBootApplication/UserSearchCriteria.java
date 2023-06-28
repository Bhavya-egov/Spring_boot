package com.example.AssignmentSpringBootApplication;

public class UserSearchCriteria {
    private int id;
    private String phone_no;

    public UserSearchCriteria() {
    }

    public UserSearchCriteria(int id, String phone_no) {
        this.id = id;
        this.phone_no = phone_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
