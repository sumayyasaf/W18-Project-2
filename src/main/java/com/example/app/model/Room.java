package com.example.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private int id;
    private int capacity;
    private int occupants;
    enum roomType {
        SINGLE,
        SHARED
    }

}
