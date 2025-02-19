package com.example.app.service;

import com.example.app.impl.RoomServiceImpl;
import com.example.app.model.Admin;
import com.example.app.model.Room;
import com.example.app.model.Student;
import com.example.app.model.User;
import com.google.gson.*;

import javax.management.relation.Role;
import java.io.IOException;

import java.net.*;


import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.stream.Collectors;

public class UserDB {

    List<User> currentUsersInApp = new ArrayList<>();
    RoomServiceImpl roomServiceImpl;

    public UserDB() {
        this.roomServiceImpl = new RoomServiceImpl(new RoomDB());

        fetchUsers();
    }
    public void fetchUsers() {

        try {
            //Create Http Request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("https://dummyjson.com/users"))
                    .GET()
                    .build();

            //Set up Http Client
            HttpClient client = HttpClient.newHttpClient();

            //Send request and get Response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //Parse Json Response
            Gson gson = new Gson();
            JsonObject us = JsonParser.parseString(response.body()).getAsJsonObject();

            JsonArray userArray = us.getAsJsonArray("users");

            //Loop through the users and convert to java objects
            for (JsonElement element : userArray) {
                JsonObject userJson = element.getAsJsonObject();

                int id = userJson.get("id").getAsInt();
                String userName = userJson.get("username").getAsString();
                String gender = userJson.get("gender").getAsString();
                String email = userJson.get("email").getAsString();
                String password = userJson.get("password").getAsString();
                String phone = userJson.get("phone").getAsString();
                String role = userJson.get("role").getAsString();
                int age = userJson.get("age").getAsInt();

                if(!role.equals("admin")) {
                    role = "student";
                    Random num = new Random();

                    Room roomObj = roomServiceImpl.getRoomById(num.nextInt(9)).orElse(new Room(num.nextInt(9), 15, 13));
                    Student studentObj = new Student(id,userName,gender,age,phone,role,email,password,num.nextInt(9),roomObj,"Java",num.nextBoolean());
                    currentUsersInApp.add(studentObj);
                } else {
                    Admin adminObj = new Admin(role,userName, password, phone, age, gender,id,"warden");
                    currentUsersInApp.add(adminObj);

//                User userA = new Student(id,userName,gender,age,phone,role,userName,password);
//                currentUsersInApp.add(userA);
                }


            }

            System.out.println("✅ Successfully fetched  users from API." + "user are" + currentUsersInApp );


        } catch (InterruptedException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }


    }
        public List<User> getUsers () {
            return currentUsersInApp.stream()
                    .filter(user -> !"admin".equalsIgnoreCase(user.getRole()))
                    .map(user -> {
                        System.out.println("user id: " + user.getId() + "-Name: " + user.getUserName());
                        return user;
                    })
                    .collect(Collectors.toList());
        }

        public User getUserById ( int id){

            return currentUsersInApp.stream()
                    .filter(usr -> usr.getId() == id)
                    .findFirst().orElse(null);
        }

        public User getUserByUsername (String username){

            return currentUsersInApp.stream()
                    .filter(usr -> Objects.equals(usr.getUserName(), username))
                    .findFirst().orElse(null);
        }

        public void delUser ( int id){
            User usr = getUserById(id);
            if (usr == null) {
                System.out.println("User does not exist in system");
            } else {
                currentUsersInApp.
                        removeIf(existingUser -> existingUser.getId() == usr.getId());
            }
        }

        public boolean addUser (User user){
            return currentUsersInApp.add(user);
        }

        public boolean delUserByUsername (String username){
            User usr = getUserByUsername(username);
            if (usr == null) {
                System.out.println("User does not exist in system");
                return false;
            }
            delUser(usr.getId());
            return true;
        }
    }

