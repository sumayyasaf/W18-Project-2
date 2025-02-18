package com.example.app.model;

import com.example.app.impl.AdminServiceImpl;
import com.example.app.impl.RoomServiceImpl;
import com.example.app.impl.StudentsServiceImpl;
import com.example.app.service.RoomDB;
import com.example.app.service.UserDB;
import com.example.app.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private String gender;
    private int age;
    private String contactNumber;
    private String role;
    private String userName;
    private String password;




    public UserService getUserService() throws URISyntaxException, IOException, InterruptedException {
        if (Objects.equals(this.role, "admin")) {
            return new AdminServiceImpl(new UserDB(), new RoomServiceImpl(new RoomDB()));
        }
            return new StudentsServiceImpl(new UserDB());

    }
}

