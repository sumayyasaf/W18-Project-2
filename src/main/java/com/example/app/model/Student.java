package com.example.app.model;

import com.sun.nio.sctp.ShutdownNotification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data

public class Student extends User {
    private int roomNumber;
    private Room roomAssigned;
    private String course;
    private boolean isFeePaid;


    public Student(int id, String userName, String gender, int age, String phone, String role, String email, String password, int roomNumber, Room roomAssigned, String course, boolean isFeePaid) {
        super(id,gender,age,phone,role,userName,password);

        this.roomNumber = roomNumber;
        this.roomAssigned = roomAssigned;
        this.course = course;
        this.isFeePaid = isFeePaid;

    }
}
