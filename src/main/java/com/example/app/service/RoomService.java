package com.example.app.service;

import com.example.app.model.Room;
import com.example.app.model.Student;

import java.util.List;
import java.util.Optional;

public interface RoomService {
     void addRoom(Room room);
     void removeRoom(Room roomId);
     List<Room> viewAllRooms();
    List<Room> getAvailableRooms();
    Optional<Room> getRoomById(int roomId);
    boolean assignRoomToStudent(int roomId, Student student);


}
