package com.example.app.model;

import com.sun.nio.sctp.ShutdownNotification;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
//@AllArgsConstructor
public class Student extends User {
    private int roomNumber;
    private Room roomAssigned;
    private String course;
    private boolean isFeePaid;

}