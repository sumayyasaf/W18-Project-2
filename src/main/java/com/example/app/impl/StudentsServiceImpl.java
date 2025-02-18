package com.example.app.impl;

import com.example.app.model.Student;
import com.example.app.model.User;
import com.example.app.service.RoomDB;
import com.example.app.service.UserDB;
import com.example.app.service.UserService;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;
import java.util.logging.Logger;

@Slf4j
public class StudentsServiceImpl implements UserService {

    private UserDB userDB = new UserDB();
    private RoomServiceImpl roomServiceimpl;
    private RoomDB roomDB = new RoomDB();


    private Logger logger;

    public StudentsServiceImpl(UserDB userDB) {
        super();
        this.userDB = userDB;
        logger = Logger.getLogger(StudentsServiceImpl.class.getName());
        this.roomServiceimpl = new RoomServiceImpl(this.roomDB);
    }

    @Override
    public User getUserDetails(int id) {
        logger.info("Started getUserDetails with id: " + id + " from the student class");
        User user = userDB.getUserById(id);
        if(user == null) {
            logger.warning("User with id: " + id + " not found");
        }
        return user;
    }

    @Override
    public User authenticateUser(String username,  String password){
        logger.info("started authenticateUser");
        User user = userDB.getUserByUsername(username);
        if(user.getPassword().equals(password)){
            return user;
        }
        logger.info("User is not authenticated");
        return null;
    }

    @Override
    public void userScreen() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("\n\033[34m========================================================");
            System.out.println("\n             Student Menu          \n");
            System.out.println("=========================================================\033[0m");
            System.out.println("1. view Available Rooms");
            System.out.println("2. Check My Room Details");
            System.out.println("3. Pay Fees");
            System.out.println("4. Exit");
            System.out.println("Enter your choice: ");
            int choice = scanner.nextInt();
            switch(choice) {
                case 1:
                    //Display available rooms
                    System.out.println("Available Rooms: " + roomServiceimpl.getAvailableRooms());
                    break;
                    case 2:
                        //Fetch student details and show assigned room
                        System.out.println("Enter student id: ");
                        int studentId = scanner.nextInt();
                        scanner.nextLine();
                        User user = userDB.getUserById(studentId);
                        if(user == null) {
                            logger.warning("User with id: " + studentId + " not found");
                        } else {
                            Student student = (Student) user;
                            int assignedRoom = student.getRoomNumber();
                            if(assignedRoom == -1) {
                                System.out.println("You are not assigned to this room yet");
                            } else {
                                System.out.println("You are assigned to this room" + assignedRoom);
                            }
                        }
                        break;
                        case 3:
                            System.out.println("Enter student id: ");
                            int id = scanner.nextInt();
                            scanner.nextLine();
                            User studentUser = userDB.getUserById(id);
                            if(studentUser == null) {
                                logger.warning("User with id: " + id + " not found");
                            } else {
                                Student student = (Student) studentUser;
                                if(student.isFeePaid()) {
                                    System.out.println("You have already paid the fee");
                                } else {
                                    System.out.println("Pay fee");
                                    student.setFeePaid(true);
                                    System.out.println("You have paid the fee successfully");
                                }
                            }
                            break;
                            case 4:
                                System.out.println("Exit the student menu");
                                return;
                                default:
                                    System.out.println("Invalid choice..try again");

            }
        }


    }


}
