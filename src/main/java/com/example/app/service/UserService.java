package com.example.app.service;

import com.example.app.model.User;

import java.util.List;

public interface UserService {
    public User getUserDetails(int id);

    public default List<User> getAllUsers(){
        System.out.println("You're not allowed to see all users");
        return null;
    };

    public User authenticateUser(String username,  String password);

    public default void addStudent(User student){
        System.out.println("You're not allowed to add a student");
    }

    public default void removeStudent(String username) {
        System.out.println("You're not allowed to remove a student");
    }

    public void userScreen();


}
