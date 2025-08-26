package services;

import java.util.List;
import models.Student;

public class StudentService {
    private List<Student> students;

    public StudentService(List<Student> students) {
        this.students = students;
    }

    // Display single student
    public void displayStudentInfo(Student student) {
        if (student != null) {
            student.displayInfo();
        } else {
            System.out.println("Student not found!");
        }
    }

    //  Display all students
    public void displayAllStudents() {
        if (students == null || students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }
        for (Student s : students) {
            s.displayInfo();
        }
    }

    //  Search student by roll number
    public Student findStudentByRoll(String rollNo) {
        if (rollNo == null || rollNo.isEmpty()) return null;
        for (Student s : students) {
            if (s.getRollNo().equalsIgnoreCase(rollNo)) {
                return s;
            }
        }
        return null;
    }
}
