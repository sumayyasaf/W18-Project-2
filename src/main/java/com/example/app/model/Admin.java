package com.example.app.model;



public class Admin extends User {
    private String designation;

    public Admin(String role, String password, String username, String contactNumber, int age, String gender, String name, int id) {
        super(id, password, username, age,contactNumber, gender, name, role);
    }
}
