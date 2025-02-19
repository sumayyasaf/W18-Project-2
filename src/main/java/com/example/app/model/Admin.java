package com.example.app.model;



public class Admin extends User {
    private String designation;

    public Admin(String role, String password, String username, String contactNumber, int age, String gender,int id, String designation) {
        super(id,gender,age,contactNumber,role,password,username);

        this.designation = designation;
    }
}
