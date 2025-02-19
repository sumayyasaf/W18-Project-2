package com.example.app.impl;

import com.example.app.model.Student;
import com.example.app.model.User;
import com.example.app.service.UserDB;
import com.example.app.service.UserService;
import lombok.Data;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Data
public class AdminServiceImpl implements UserService {
    private UserDB userDB;
    private RoomServiceImpl roomServiceImpl;

    private Logger logger;

    public AdminServiceImpl(UserDB userDB, RoomServiceImpl roomService) {
        logger = Logger.getLogger(AdminServiceImpl.class.getName());
        this.userDB = userDB;
        this.roomServiceImpl = roomService;
    }

    @Override
    public User getUserDetails(int id) {

        logger.info("started getUserDetails with id: " + id + "from the admin class");
        return userDB.getUserById(id);
    }

    @Override
    public List<User> getAllUsers() {
        logger.info("able to access users");
        return this.userDB.getUsers();
    }

    @Override
    public User authenticateUser(String username, String password) {
        logger.info("started authenticateUser for username: " + username);
        User user = userDB.getUserByUsername(username);
        if (user == null) {
            logger.warning("User not found");
            return null;
        }
        if (user.getPassword().equals(password)) {
            logger.info("User successfully authenticated");
            return user;
        }
        logger.warning("User not authenticated");
        return null;
    }


    @Override
    public void addStudent(User student) {
        userDB.addUser(student);
        System.out.println("Student added: " + student);

    }

    @Override
    public void removeStudent(String username) {
        userDB.delUserByUsername(username);
        System.out.println("Student removed: " + username);
    }

    public void assignRoom(int studentroomNumber, int roomId) {
     User user = userDB.getUserById(studentroomNumber);
     if(user instanceof Student) {
         System.out.println("Student is there");
         Student student = (Student) user;
         boolean assigned = roomServiceImpl.assignRoomToStudent(roomId, student);
         if(assigned) {
             System.out.println("Room" + roomId + " assigned to student: " + student.getUserName());
         } else
             System.out.println("Room assignment failed: No available space in Room " + roomId);
         }
         }

    public void viewStudents() {
        System.out.println("current users in system" + userDB.getUsers().toString());
    }


    @Override
    public void userScreen() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\033[34mWelcome to Admin Dashboard");
            System.out.println("\033[33m1. Add Student");
            System.out.println("\033[33m2. Remove Student");
            System.out.println("\033[33m3. Assign Room");
            System.out.println("\033[33m4. View Students");
            System.out.println("\033[33m5. Check room Availability ");
            System.out.println("\033[33m6. Exit\033[0m");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
//                    List<String> students = userDB.getUsers().stream().filter(u -> u.getRole()!= null && u.equals("admin")).map(u -> u.getUserName()).collect(Collectors.toList());
                    System.out.println("Enter student name: " );
                    String studentName = scanner.next();
                    scanner.nextLine();
                    System.out.println("Enter Course name: " );
                    String courseName = scanner.next();
                    scanner.nextLine();
                    System.out.println("Has the student paid fees?(Y/N): "  );
                    boolean isFeePaid = scanner.nextLine().equalsIgnoreCase("y");
                    Student student = new Student(1,"userName","N/A",2,"phone","role","email","password",0,null,"course",false);
                    student.setUserName(studentName);
                    student.setCourse(courseName);
                    student.setFeePaid(isFeePaid);
                    if(userDB.addUser(student)) {
                        System.out.println("Student" + studentName + " added successfully..");
                    } else {
                        System.out.println("Failed to add student" + studentName);
                    }
//                    addStudent(student);
                    break;
                case 2:
                    System.out.println("Enter student name to remove: ");
                    String userName = scanner.next();
                    removeStudent(userName);
                    break;
                case 3:
                    System.out.println("Enter student Id to assign room: ");
                     int studentId = scanner.nextInt();
                    System.out.println("Enter room Id to view: ");
                    int roomId = scanner.nextInt();
                    assignRoom(studentId,roomId);
                    System.out.println("roomId: " + roomId + "  has been assigned to the student with studentId " + studentId);

                    break;
                case 4:
                    viewStudents();
                    break;
                case 5:
                    System.out.println("Available rooms: " + roomServiceImpl.getAvailableRooms());
                    break;
                    case 6:
                        System.out.println("Exit Admin Dashboard.....");
                        return;
                        default:
                            System.out.println("Invalid choice! Please try again.");

            }

        }


    }
}
