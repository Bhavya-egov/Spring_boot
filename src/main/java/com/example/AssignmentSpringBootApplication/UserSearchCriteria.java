package com.example.AssignmentSpringBootApplication;

import java.util.UUID;

public class UserSearchCriteria {
    private UUID id;
    private String phone_no;

    public UserSearchCriteria() {
    }

    public UserSearchCriteria(UUID id, String phone_no) {
        this.id = id;
        this.phone_no = phone_no;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }
}
