package utils;

import java.io.*;
import java.util.*;
import models.Student;
import models.Teacher;

public class CSVUtils {

    // STUDENTS 
    public static List<Student> readStudents(String filePath) throws IOException {
        List<Student> students = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        String line;

        br.readLine(); // skip header

        while ((line = br.readLine()) != null) {
            String[] f = line.split(",");

            String uniqueId = f[0];
            String rollNo = f[1];
            String name = f[2];
            String section = f[3];
            String semester = f[4];
            String mentorId = f[5];
            String coordinatorId = f[6];

            int attPython = Student.parseIntSafe(f[7]);
            int attDbms   = Student.parseIntSafe(f[8]);
            int attStats  = Student.parseIntSafe(f[9]);
            int attFods   = Student.parseIntSafe(f[10]);
            int attHpc    = Student.parseIntSafe(f[11]);

            String asgPython = f[12];
            String asgDbms   = f[13];
            String asgStats  = f[14];
            String asgFods   = f[15];
            String asgHpc    = f[16];

            double cgpa          = Student.parseDoubleSafe(f[17]);
            String skills        = f[18];
            double totalAttendance = Student.parseDoubleSafe(f[19]);

            students.add(new Student(uniqueId, rollNo, name, section, semester, mentorId, coordinatorId,
                    attPython, attDbms, attStats, attFods, attHpc,
                    asgPython, asgDbms, asgStats, asgFods, asgHpc,
                    cgpa, skills, totalAttendance));
        }

        br.close();
        return students;
    }

    public static void writeStudents(String filePath, List<Student> students) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write("UniqueID,RollNo,Name,Section,Semester,MentorID,CoordinatorID,AttPython,AttDbms,AttStats,AttFods,AttHpc,AsgPython,AsgDbms,AsgStats,AsgFods,AsgHpc,CGPA,Skills,TotalAttendance\n");

        for (Student s : students) {
            bw.write(String.join(",",
                    s.getUniqueId(),
                    s.getRollNo(),
                    s.getName(),
                    s.getSection(),
                    s.getSemester(),
                    s.getMentorId(),
                    s.getCoordinatorId(),
                    String.valueOf(s.getAttPython()),
                    String.valueOf(s.getAttDbms()),
                    String.valueOf(s.getAttStats()),
                    String.valueOf(s.getAttFods()),
                    String.valueOf(s.getAttHpc()),
                    s.getAsgPython(),
                    s.getAsgDbms(),
                    s.getAsgStats(),
                    s.getAsgFods(),
                    s.getAsgHpc(),
                    String.valueOf(s.getCgpa()),
                    s.getSkills(),
                    String.valueOf(s.getTotalAttendance())
            ) + "\n");
        }

        bw.close();
    }

    //  TEACHERS 
    public static List<Teacher> readTeachers(String filePath) throws IOException {
        List<Teacher> teachers = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        br.readLine(); // skip header
        String line;
        while ((line = br.readLine()) != null) {
            String[] f = line.split(",");
            if (f.length < 8) continue; // skip invalid rows
            teachers.add(new Teacher(f[0], f[1], f[2], f[3], f[4], f[5], f[6], f[7]));
        }
        br.close();
        return teachers;
    }

    public static void writeTeachers(String filePath, List<Teacher> teachers) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
        bw.write("TeacherID,Name,Role,Sections,Subjects,MentorRange,Username,Password\n");
        for (Teacher t : teachers) {
            bw.write(String.join(",",
                    t.getTeacherId(),
                    t.getName(),
                    t.getRole(),
                    t.getAssignedSections(),
                    t.getSubjects(),
                    t.getMentorRollRange(),
                    t.getUsername(),
                    t.getPassword()) + "\n");
        }
        bw.close();
    }
}
