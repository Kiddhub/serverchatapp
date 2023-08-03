package com.main.service;

import com.main.model.User;
import com.main.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user){
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw  new RuntimeException("Email exist");
        }
        user.setActive(false);
        user.setCreated_at(LocalDateTime.now());
        user.setToken(UUID.randomUUID().toString());
        /*User newUser = userRepository.save(user);*/
        return userRepository.save(user);
    }
    public User loginUser(String email, String password){
        User user1 = userRepository.findByEmail(email);
        if(user1 == null){
            throw new RuntimeException("Email is not exist");
        } else if (!user1.getPassword().equals(password)) {
            throw new RuntimeException("Password is not correct");
        }
        user1.setActive(true);
        return userRepository.save(user1);
    }
    public boolean logoutUSer(String email){
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setActive(false);
            userRepository.save(user);
            return true;
        }else {
            return false;
        }
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
