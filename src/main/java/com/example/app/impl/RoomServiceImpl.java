package com.example.app.impl;

import com.example.app.model.Room;
import com.example.app.model.Student;
import com.example.app.service.RoomDB;
import com.example.app.service.RoomService;
import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

//RoomServiceImpl uses RoomDB to provide business logic:
//Assigning rooms
//Fetching available rooms
//Adding/removing rooms
@Data public class RoomServiceImpl implements RoomService {
    private RoomDB roomDB;
    private static final Logger logger = Logger.getLogger(RoomServiceImpl.class.getName());

    public RoomServiceImpl(RoomDB roomDB) {
        this.roomDB = roomDB;
    }

    @Override
    public void addRoom(Room room) {
        roomDB.addRoom(room);
        logger.info("Room added: " + room);
    }

    @Override
    public void removeRoom(Room roomId) {
        roomDB.removeRoom(roomId);
        logger.info("Room removed: " + roomId);
    }

    @Override
    public List<Room> viewAllRooms() {
        return roomDB.getAllRooms();
    }

    @Override
    public List<Room> getAvailableRooms() {
        return roomDB.getAvailableRooms();
    }

    @Override
    public Optional<Room> getRoomById(int roomId) {
        return roomDB.getRoomById(roomId);
    }

    @Override
    public boolean assignRoomToStudent(int roomId, Student student) {
        Optional<Room> roomOpt = roomDB.getRoomById(roomId);
        if (roomOpt.isPresent()) {
            Room roomToAssign = roomOpt.get();
            if(roomToAssign.getOccupants() < roomToAssign.getCapacity()){
                roomToAssign.setOccupants(roomToAssign.getOccupants() + 1);
                student.setRoomNumber(roomToAssign.getId());
                logger.info("Room assigned to student: " + student.getUserName() + "-> Room" + roomToAssign.getId());
                return true;
            }

        }
        logger.warning("Room assignment failed: No available space in Room" + roomId);
        return false;
    }
}
