package com.example.AssignmentSpringBootApplication.controller;

import com.example.AssignmentSpringBootApplication.User;
import com.example.AssignmentSpringBootApplication.UserSearchCriteria;
import com.example.AssignmentSpringBootApplication.repository_inter.Repo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api")
@Controller
public class UserController {
    @Autowired
    Repo repo;
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getTutorialByID(@PathVariable("id") UUID id){
        User user = repo.findById(id);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
  //  @GetMapping("/userSearch/{phone_no}")
//    public ResponseEntity<User> getUserByPhone(@RequestBody UserSearchCriteria userSearchCriteria){\
//
//        if(userSearchCriteria.getId() != null){
//            return new ResponseEntity<>(new , HttpStatus.OK);
//        }
//        else{
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
    @GetMapping("/getuser/{active}")
    public ResponseEntity<User> find_by_active(@PathVariable("active") boolean active){
        User user = repo.findByBool(active);
        if(user !=null) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/find")
    public ResponseEntity<User> userFind(@RequestBody UserSearchCriteria userSearchCriteria){
        User user = repo.find(userSearchCriteria.getId(), userSearchCriteria.getPhone_no());
        if(user.getId()!=null) {
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        else if(user.getPhone_no() != null){
            return new ResponseEntity<>(user,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
//    @PostMapping("/user")
//    public ResponseEntity<String> create(@RequestBody User user) {
//        System.out.println(user.getAddress());
//        try {
//            repo.create(new User(user.getName(), user.getGender(), user.getPhone_no(), user.getAddress(), user.isActive()));
//            return new ResponseEntity<>("User was created successfully.", HttpStatus.CREATED);
//        } catch (Exception e) {
//            String errorMessage = "Failed to create user: " + e.getMessage();
//            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    @PostMapping("/user")
    public ResponseEntity<String> createUsers(@RequestBody List<User> users){
        try{
            for(User user: users){
                repo.create(user);
            }
            return new ResponseEntity<>("Users were created successfully.",HttpStatus.CREATED);
        }catch (Exception e){
            String errorMessage = "Failed to create user: " + e.getMessage();
            return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @PutMapping("/user/{id}")
//    public ResponseEntity<String> updateUser(@PathVariable("id") int id, @RequestBody User user){
//        User _user = repo.findById(id);
//        //System.out.println("Hello");
//        if(_user != null){
//            //_user.setId(id);
//            _user.setName(user.getName());
//            _user.setGender(user.getGender());
//            _user.setPhone_no(user.getPhone_no());
//            _user.setAddress(user.getAddress());
//            _user.setActive(user.isActive());
//
//            repo.update(_user);
//            return new ResponseEntity<>("User was updated successfully.", HttpStatus.OK);
//        }else{
//            return new ResponseEntity<>("Cannot find User with id=" + id, HttpStatus.NOT_FOUND);
//        }
//    }

    @PutMapping("/user")
    public ResponseEntity<String> updateUser(@RequestBody List<User> users){
        //System.out.println("Hello");
        try {
            for(User user: users){
                User _user= repo.findById(user.getId());
                if (_user != null) {
                    //_user.setId(id);
                    _user.setName(user.getName());
                    _user.setGender(user.getGender());
                    _user.setPhone_no(user.getPhone_no());
                    _user.setAddress(user.getAddress());
                    _user.setActive(user.isActive());

                    repo.update(_user);

                }
            }
            return new ResponseEntity<>("Users was updated successfully.", HttpStatus.OK);
        }catch(Exception e){
            String errorMessage= "Failed to update" + e.getMessage();
            return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/user/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") UUID id){
        try{
            //System.out.println("delete");
            int result= repo.deleteByID(id);
            System.out.println("Delete Function");
            System.out.println(result);
            if(result==0){
                return new ResponseEntity<>("Cannot find user",HttpStatus.OK);
            }
            return new ResponseEntity<>("User deleted successfully",HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>("Cannot delete user" + e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
