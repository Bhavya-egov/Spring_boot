package com.example.AssignmentSpringBootApplication.controller;

import com.example.AssignmentSpringBootApplication.UserClass;
import com.example.AssignmentSpringBootApplication.UserSearchCriteria;
import com.example.AssignmentSpringBootApplication.repository_inter.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@Controller
public class UserController {
    @Autowired
    Repo repo;
    @GetMapping("/user/{id}")
    public ResponseEntity<UserClass> getTutorialByID(@PathVariable("id") int id){
        UserClass userClass = repo.find_by_id(id);
        if(userClass != null){
            return new ResponseEntity<>(userClass, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
  //  @GetMapping("/userSearch/{phone_no}")
//    public ResponseEntity<UserClass> getUserByPhone(@RequestBody UserSearchCriteria userSearchCriteria){\
//
//        if(userSearchCriteria.getId() != null){
//            return new ResponseEntity<>(new , HttpStatus.OK);
//        }
//        else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @PostMapping("/find")
    public ResponseEntity<UserClass> userFind(@RequestBody UserSearchCriteria userSearchCriteria){
        UserClass userClass= repo.find(userSearchCriteria.getId(), userSearchCriteria.getPhone_no());
        if(userClass.getId()!=0) {
            return new ResponseEntity<>(userClass, HttpStatus.OK);
        }
        else if(userClass.getPhone_no() != null){
            return new ResponseEntity<>(userClass,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/user")
    public ResponseEntity<String> create(@RequestBody UserClass userClass) {
        System.out.println(userClass.getAddress());
        try {
            repo.create(new UserClass(userClass.getId(), userClass.getName(), userClass.getGender(), userClass.getPhone_no(), userClass.getAddress()));
            return new ResponseEntity<>("UserClass was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Failed to create userClass: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<String> updateUser(@PathVariable("id") int id, @RequestBody UserClass userClass){
        UserClass _userClass = repo.find_by_id(id);
        //System.out.println("Hello");
        if(_userClass != null){
            _userClass.setId(id);
            _userClass.setName(userClass.getName());
            _userClass.setGender(userClass.getGender());
            _userClass.setPhone_no(userClass.getPhone_no());
            _userClass.setAddress(userClass.getAddress());

            repo.update(_userClass);
            return new ResponseEntity<>("UserClass was updated successfully.", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Cannot find UserClass with id=" + id, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id){
        try{
            //System.out.println("delete");
            int result= repo.delete_by_id(id);
            System.out.println("Delete Function");
            System.out.println(result);
            if(result==0){
                return new ResponseEntity<>("Cannot find user",HttpStatus.OK);
            }
            return new ResponseEntity<>("UserClass deleted successfully",HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Cannot delete user" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
