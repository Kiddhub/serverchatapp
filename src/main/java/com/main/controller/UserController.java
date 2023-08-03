package com.main.controller;

import com.main.model.User;
import com.main.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User registerUser = userService.registerUser(user);
        return new ResponseEntity<>(registerUser, HttpStatus.CREATED);
    }

    @PostMapping("/user/login")
    public ResponseEntity<User> login(@RequestBody User user1){
        try{
            User loginUser = userService.loginUser(user1.getEmail(),user1.getPassword());
            return new ResponseEntity<>(loginUser,HttpStatus.OK);
        }catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser(@RequestParam("email") String email){
        boolean isLoggedOut = userService.logoutUSer(email);
        if(isLoggedOut){
            return ResponseEntity.ok("Logged out successfully");
        }else {
            return ResponseEntity.badRequest().body("Failed to log out");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        List<User> users1 = new ArrayList<>();
        for(User user: users){
            User user1 = new User();
            user1.setId(user.getId());
            user1.setEmail(user.getEmail());
            user1.setName(user.getName());
            user1.setImage(user.getImage());
            user1.setToken(user.getToken());
            users1.add(user1);
        }
        return ResponseEntity.ok(users1);
    }
}
