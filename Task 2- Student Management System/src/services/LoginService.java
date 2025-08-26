package services;

import java.util.List;
import models.Student;
import models.Teacher;

public class LoginService {
    private List<Teacher> teachers;
    private List<Student> students;

    public LoginService(List<Teacher> teachers, List<Student> students) {
        this.teachers = teachers;
        this.students = students;
    }

    //  Teacher Login
    public Teacher teacherLogin(String username, String password) {
        for (Teacher t : teachers) {
            if (t.getUsername().equalsIgnoreCase(username) && t.getPassword().equals(password)) {
                return t;
            }
        }
        return null;
    }

    //  Student Login
    public Student studentLogin(String rollNo, String uniqueId) {
        for (Student s : students) {
            if (s.getRollNo().equalsIgnoreCase(rollNo) && s.getUniqueId().equalsIgnoreCase(uniqueId)) {
                return s;
            }
        }
        return null;
    }
}
