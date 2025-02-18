package com.example.app;

import com.example.app.impl.AdminServiceImpl;
import com.example.app.impl.RoomServiceImpl;
import com.example.app.impl.StudentsServiceImpl;
import com.example.app.model.User;
import com.example.app.service.RoomDB;
import com.example.app.service.UserDB;
import com.example.app.service.UserService;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.logging.Logger;

public class HostelManagement {
    Scanner scanner;
    UserService userService;
    AdminServiceImpl adminService;
    StudentsServiceImpl studentsService;
    User currentUser;
    private static final Logger logger = Logger.getLogger(HostelManagement.class.getName());

    public HostelManagement() throws URISyntaxException, IOException, InterruptedException {
        scanner = new Scanner(System.in);
        adminService = new AdminServiceImpl(new UserDB(), new RoomServiceImpl(new RoomDB()));
        studentsService = new StudentsServiceImpl(new UserDB());
    }


    public void menu() throws URISyntaxException, IOException, InterruptedException {
        while (true) {
            System.out.println("\n\033[34m==============================================");
            System.out.println("\n         DCI Hostel Management System \n");
            System.out.println("==============================================\033[0m");
            logger.info("Hostel Management System Started");
            System.out.println("\033[33m 1. ADMIN-LOGIN");
            System.out.println("\033[33m 2. STUDENT-LOGIN");
            System.out.println("\033[33m 3. ABOUT DCI HOSTEL MANAGEMENT SYSTEM");
            System.out.println("\033[33m 4. EXIT PROGRAM \033[0m");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Admin Username: ");
                    String adminUsername = scanner.next();  // ✅ Read username correctly
                    System.out.print("Enter Admin Password: ");
                    String adminPassword = scanner.next();  // ✅ Read password correctly
                    this.currentUser = adminService.authenticateUser(adminUsername, adminPassword);
                    break;

                case 2:
                    System.out.print("Enter Student Username: ");
                    String studentUsername = scanner.next();
                    System.out.print("Enter Student Password: ");
                    String studentPassword = scanner.next();
                    this.currentUser = studentsService.authenticateUser(studentUsername, studentPassword);
                    break;
                case 3:
                    aboutDciHostel();
                    break;
                case 4:
                    System.out.println("Exiting.....Thank you for using Hostel Management System");
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
           if (this.currentUser != null) {
               this.userService = this.currentUser.getUserService(); //service: studentService/admin service
                this.userService.userScreen();
           }

        }
    }


    public void aboutDciHostel() {
        System.out.println("DCI Hostel provides a safe, comfortable, and well-maintained living environment for students. \n The hostel is designed to offer a home-like atmosphere while ensuring discipline and academic focus.\n Spacious and well-furnished rooms with single, double, and shared occupancy options.Quiet and well-lit study rooms to facilitate learning and concentration.\n Our Hostel ensures that students have a comfortable and supportive living experience while focusing on their studies\n");
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        HostelManagement hostelManagement = new HostelManagement();
        hostelManagement.menu();
        UserDB userDB = new UserDB();


    }
}









