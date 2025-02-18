package com.example.app.service;

import com.example.app.model.User;
import com.google.gson.*;

import java.io.IOException;

import java.net.*;


import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class UserDB {

    List<User> currentUsersInApp = new ArrayList<>();

    public UserDB() {
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
//                String name = userJson.get("name").getAsString();
                String userName = userJson.get("username").getAsString();
                String gender = userJson.get("gender").getAsString();
                String email = userJson.get("email").getAsString();
                String password = userJson.get("password").getAsString();
                String phone = userJson.get("phone").getAsString();
                String role = userJson.get("role").getAsString();
                int age = userJson.get("age").getAsInt();

                if(!role.equals("admin")) {
                    role = "student";
                }


                //Create a user Object
                User userA = new User(id,userName,gender,age,phone,role,email,password);
                currentUsersInApp.add(userA);

            }

            System.out.println("✅ Successfully fetched  users from API." + "user are" + currentUsersInApp );


        } catch (InterruptedException | URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }


    }
        public List<User> getUsers () {
            return currentUsersInApp;
        }

        public User getUserById ( int id){

            return currentUsersInApp.stream()
                    .filter(usr -> usr.getId() == id)
                    .findFirst().orElse(null);
        }

        public User getUserByUsername (String username){

            return currentUsersInApp.stream()
                    .filter(usr -> Objects.equals(usr.getName(), username))
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
            delUser(usr.getId());
            return true;
        }
    }

