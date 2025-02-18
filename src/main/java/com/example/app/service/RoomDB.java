package com.example.app.service;
import com.example.app.model.Room;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class RoomDB {
    private final List<Room> rooms;

    public RoomDB() {
        this.rooms = new ArrayList<>();
        new Room(0,3,2);
        this.rooms.add(new Room(1,2,3));
        this.rooms.add(new Room(2,0,4));
        this.rooms.add(new Room(3,8,2));
        this.rooms.add(new Room(4,4,3));
    }
   public void addRoom(Room room) {
        rooms.add(room);
   }
   public void removeRoom(Room room) {
        rooms.removeIf(r -> r.getId() == room.getId());
   }
   public List<Room> getAllRooms() {
        return rooms;
   }
   public List<Room> getAvailableRooms() {
        return rooms.stream()
                .filter(r->r.getOccupants() < r.getCapacity())
                .collect(Collectors.toList());
   }
   public Optional<Room> getRoomById(int roomId) {
        return rooms.stream().filter(r -> r.getId() == roomId).findFirst();
   }
}


