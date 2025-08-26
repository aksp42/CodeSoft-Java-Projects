import java.io.IOException;
import java.util.*;

import models.Student;
import models.Teacher;
import services.*;
import utils.CSVUtils;

public class Main {

    private static final String STUDENT_FILE = "DSdata.csv";
    private static final String TEACHER_FILE = "TeacherData.csv";

    public static void main(String[] args) {
        try {
            // Load Data from CSV
            List<Student> students = CSVUtils.readStudents(STUDENT_FILE);
            List<Teacher> teachers = CSVUtils.readTeachers(TEACHER_FILE);

            // Initialize Services
            LoginService loginService = new LoginService(teachers, students);
            StudentService studentService = new StudentService(students);
            TeacherService teacherService = new TeacherService(teachers, students, TEACHER_FILE);

            Scanner sc = new Scanner(System.in);
            System.out.println("===== Student-Teacher Management System =====");
            System.out.println("1. Student Login");
            System.out.println("2. Teacher Login");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            if (choice == 1) {
                //  Student Login
                System.out.print("Enter Roll No: ");
                String rollNo = sc.nextLine();
                System.out.print("Enter Unique ID: ");
                String uniqueId = sc.nextLine();

                Student loggedStudent = loginService.studentLogin(rollNo, uniqueId);

                if (loggedStudent != null) {
                    System.out.println("Login Successful!\n");
                    studentService.displayStudentInfo(loggedStudent);
                } else {
                    System.out.println("Invalid Student credentials!");
                }

            } else if (choice == 2) {
                // Teacher Login
                System.out.print("Enter Username: ");
                String username = sc.nextLine();
                System.out.print("Enter Password: ");
                String password = sc.nextLine();

                Teacher loggedTeacher = loginService.teacherLogin(username, password);

                if (loggedTeacher != null) {
                    System.out.println("Login Successful! Welcome " + loggedTeacher.getName() + " (" + loggedTeacher.getRole() + ")\n");

                    boolean running = true;
                    while (running) {
                        System.out.println("===== Teacher Menu =====");
                        System.out.println("1. View All Students");
                        System.out.println("2. Search Student by Roll No");
                        System.out.println("3. View All Teachers");
                        if (loggedTeacher.isHOD()) {
                            System.out.println("4. Add Teacher");
                            System.out.println("5. Remove Teacher");
                        }
                        System.out.println("0. Logout");
                        System.out.print("Choose option: ");
                        int opt = sc.nextInt();
                        sc.nextLine(); // consume newline

                        switch (opt) {
                            case 1 -> studentService.displayAllStudents();
                            case 2 -> {
                                System.out.print("Enter Roll No to search: ");
                                String rollSearch = sc.nextLine();
                                Student s = studentService.findStudentByRoll(rollSearch);
                                studentService.displayStudentInfo(s);
                            }
                            case 3 -> teacherService.displayAllTeachers();
                            case 4 -> {
                                if (loggedTeacher.isHOD()) {
                                    System.out.print("Enter TeacherID: ");
                                    String tId = sc.nextLine();
                                    System.out.print("Enter Name: ");
                                    String tName = sc.nextLine();
                                    System.out.print("Enter Role (HOD/Coordinator/Faculty): ");
                                    String tRole = sc.nextLine();
                                    System.out.print("Enter Assigned Sections: ");
                                    String tSections = sc.nextLine();
                                    System.out.print("Enter Subjects: ");
                                    String tSubjects = sc.nextLine();
                                    System.out.print("Enter Mentor Roll Range: ");
                                    String tRange = sc.nextLine();
                                    System.out.print("Enter Username: ");
                                    String tUser = sc.nextLine();
                                    System.out.print("Enter Password: ");
                                    String tPass = sc.nextLine();

                                    Teacher newT = new Teacher(tId, tName, tRole, tSections, tSubjects, tRange, tUser, tPass);
                                    teacherService.addTeacher(newT, loggedTeacher);
                                } else {
                                    System.out.println("Only HOD can add teachers!");
                                }
                            }
                            case 5 -> {
                                if (loggedTeacher.isHOD()) {
                                    System.out.print("Enter Username of teacher to remove: ");
                                    String removeUser = sc.nextLine();
                                    teacherService.removeTeacher(removeUser, loggedTeacher);
                                } else {
                                    System.out.println("Only HOD can remove teachers!");
                                }
                            }
                            case 0 -> {
                                running = false;
                                System.out.println("Logged out successfully!");
                            }
                            default -> System.out.println("Invalid option!");
                        }
                    }
                } else {
                    System.out.println("Invalid Teacher credentials!");
                }
            } else {
                System.out.println("Invalid choice!");
            }

            //  Save student data back if updated (not required unless edits are added)
            CSVUtils.writeStudents(STUDENT_FILE, students);

            sc.close();

        } catch (IOException e) {
            System.out.println("Error reading files: " + e.getMessage());
        }
    }
}
