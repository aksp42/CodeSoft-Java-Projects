package services;

import java.util.List;
import models.Student;
import models.Teacher;
import utils.CSVUtils;

public class TeacherService {
    private List<Teacher> teachers;
    private List<Student> students;
    private String teacherFilePath;

    public TeacherService(List<Teacher> teachers, List<Student> students, String teacherFilePath) {
        this.teachers = teachers;
        this.students = students;
        this.teacherFilePath = teacherFilePath;
    }

    //  Add Teacher (only HOD)
    public void addTeacher(Teacher newTeacher, Teacher loggedIn) {
        if (loggedIn == null || !loggedIn.isHOD()) {
            System.out.println("Only HOD can add teachers!");
            return;
        }
        if (newTeacher == null) {
            System.out.println("Invalid teacher data!");
            return;
        }
        teachers.add(newTeacher);
        System.out.println("Teacher added successfully!");
        saveTeachers();
    }

    //  Remove Teacher (only HOD)
    public void removeTeacher(String username, Teacher loggedIn) {
        if (loggedIn == null || !loggedIn.isHOD()) {
            System.out.println("Only HOD can remove teachers!");
            return;
        }
        if (username == null || username.isEmpty()) {
            System.out.println("Invalid username!");
            return;
        }
        boolean removed = teachers.removeIf(t -> t.getUsername().equalsIgnoreCase(username));
        if (removed) {
            System.out.println("Teacher removed successfully!");
            saveTeachers();
        } else {
            System.out.println("Teacher not found!");
        }
    }

    //  Display all teachers
    public void displayAllTeachers() {
        if (teachers == null || teachers.isEmpty()) {
            System.out.println("No teachers available.");
            return;
        }
        for (Teacher t : teachers) {
            t.displayInfo();
        }
    }

    //  Helper method to save teachers
    private void saveTeachers() {
        try {
            CSVUtils.writeTeachers(teacherFilePath, teachers);
        } catch (Exception e) {
            System.out.println("Error updating teacher file: " + e.getMessage());
        }
    }
}
