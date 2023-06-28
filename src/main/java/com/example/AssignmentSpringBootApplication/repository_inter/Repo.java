package com.example.AssignmentSpringBootApplication.repository_inter;

import com.example.AssignmentSpringBootApplication.UserClass;
import com.example.AssignmentSpringBootApplication.UserSearchCriteria;

public interface Repo {
    int create(UserClass userClass);
    int update(UserClass userClass);
    UserClass find_by_id(int id);
    UserClass find(int id,String phone_no);
    int delete_by_id(int id);


}
