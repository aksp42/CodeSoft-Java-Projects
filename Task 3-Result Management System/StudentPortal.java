import java.io.*;
import java.util.*;

class Student {
    String username;
    String password;
    String rollNumber;
    String classLevel;
    String streamOrDegree;
    Map<String, Integer> subjects = new LinkedHashMap<>();      
    Map<String, Integer> subjectsMax = new LinkedHashMap<>();   

    Student(String username, String password, String rollNumber, String classLevel) {
        this.username = username;
        this.password = password;
        this.rollNumber = rollNumber;
        this.classLevel = classLevel;
    }
}

public class StudentPortal {
    static Scanner sc = new Scanner(System.in);
    static String studentFile = "students.csv";
    static String resultFile = "results.csv";

    public static void main(String[] args) throws Exception {
        System.out.println("====================================");
        System.out.println("      Student Result Management");
        System.out.println("====================================");
        System.out.println("1) Login   2) Register   3) Exit");
        System.out.print("Choose option (1/2/3): ");
        int choice = Integer.parseInt(sc.nextLine());

        if (choice == 1) {
            login();
        } else if (choice == 2) {
            register();
        } else {
            System.out.println("Thank you!");
        }
    }

    static void login() throws Exception {
        System.out.print("Enter Username: ");
        String uname = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();

        Student student = getStudent(uname, pass);
        if (student == null) {
            System.out.println("\nInvalid Username or Password!");
            System.out.println("Redirecting to Register Page...\n");
            register();
        } else {
            System.out.println("\nLogin Successful!\n");
            showResult(student.rollNumber);
        }
    }

    static void register() throws Exception {
        System.out.print("Enter Username: ");
        String uname = sc.nextLine();
        System.out.print("Enter Password: ");
        String pass = sc.nextLine();
        System.out.print("Enter Roll Number: ");
        String roll = sc.nextLine();
        System.out.print("Choose (10/12/UG/PG): ");
        String cls = sc.nextLine();

        Student s = new Student(uname, pass, roll, cls);

        if (cls.equals("10")) {
            putMarks(s, "Maths");
            putMarks(s, "Science");
            putMarks(s, "English");
            putMarks(s, "Social Science");
            putMarks(s, "Hindi");
        } else if (cls.equals("12")) {
            System.out.print("Enter Stream (PCM/PCB/Commerce): ");
            s.streamOrDegree = sc.nextLine();
            if (s.streamOrDegree.equalsIgnoreCase("PCM")) {
                putMarks(s, "Physics");
                putMarks(s, "Chemistry");
                putMarks(s, "Maths");
                putMarks(s, "English");
                putMarks(s, "Optional");
            } else if (s.streamOrDegree.equalsIgnoreCase("PCB")) {
                putMarks(s, "Physics");
                putMarks(s, "Chemistry");
                putMarks(s, "Biology");
                putMarks(s, "English");
                putMarks(s, "Optional");
            } else {
                putMarks(s, "Accounts");
                putMarks(s, "Business Studies");
                putMarks(s, "Economics");
                putMarks(s, "English");
                putMarks(s, "Optional");
            }
        } else {
            System.out.print("Enter Degree (BA/BSc/BCom/BTech/BcA/others): ");
            s.streamOrDegree = sc.nextLine();
            System.out.print("Enter number of subjects: ");
            int n = Integer.parseInt(sc.nextLine());

            for (int i = 1; i <= n; i++) {
                System.out.print("Enter subject " + i + " name: ");
                String sub = sc.nextLine();
                putMarks(s, sub);
            }
        }

        saveStudent(s);
        saveResult(s);
        showResult(s.rollNumber);
    }

    // helper method to input marks & max
    static void putMarks(Student s, String sub) {
        System.out.print("Enter Max Score for " + sub + ": ");
        int max = Integer.parseInt(sc.nextLine());

        System.out.print("Enter marks for " + sub + " (out of " + max + "): ");
        int marks = Integer.parseInt(sc.nextLine());

        s.subjects.put(sub, marks);
        s.subjectsMax.put(sub, max);
    }

    static void saveStudent(Student s) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(studentFile, true));
        bw.write(s.username + "," + s.password + "," + s.rollNumber + "," + s.classLevel + "," + (s.streamOrDegree == null ? "" : s.streamOrDegree));
        bw.newLine();
        bw.close();
    }

    static void saveResult(Student s) throws Exception {
        BufferedWriter bw = new BufferedWriter(new FileWriter(resultFile, true));
        StringBuilder sb = new StringBuilder();
        sb.append(s.rollNumber);
        for (String sub : s.subjects.keySet()) {
            sb.append(",").append(sub).append(":").append(s.subjects.get(sub)).append("/").append(s.subjectsMax.get(sub));
        }
        bw.write(sb.toString());
        bw.newLine();
        bw.close();
    }

    static Student getStudent(String uname, String pass) throws Exception {
        File f = new File(studentFile);
        if (!f.exists()) return null;
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split(",");
            if (arr[0].equals(uname) && arr[1].equals(pass)) {
                Student s = new Student(arr[0], arr[1], arr[2], arr[3]);
                if (arr.length > 4) s.streamOrDegree = arr[4];
                br.close();
                return s;
            }
        }
        br.close();
        return null;
    }

    static void showResult(String roll) throws Exception {
        File f = new File(resultFile);
        if (!f.exists()) {
            System.out.println("No result found!");
            return;
        }
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split(",");
            if (arr[0].equals(roll)) {
                LinkedHashMap<String, Integer> subs = new LinkedHashMap<>();
                LinkedHashMap<String, Integer> maxSubs = new LinkedHashMap<>();

                for (int i = 1; i < arr.length; i++) {
                    if (!arr[i].contains(":")) continue; // skip invalid entries
                    String[] kv = arr[i].split(":");
                    if (kv.length < 2) continue;
                    String sub = kv[0];
                    String[] marksMax = kv[1].split("/");
                    if (marksMax.length < 2) continue;

                    int marks = Integer.parseInt(marksMax[0]);
                    int max = Integer.parseInt(marksMax[1]);
                    subs.put(sub, marks);
                    maxSubs.put(sub, max);
                }

                int total = 0, maxTotal = 0;
                int best = -1, worst = Integer.MAX_VALUE;
                String bestSub = "", worstSub = "";
                boolean failFlag = false;

                System.out.println("====== RESULT ======");
                for (String sub : subs.keySet()) {
                    int marks = subs.get(sub);
                    int max = maxSubs.get(sub);
                    total += marks;
                    maxTotal += max;

                    // check fail condition (less than 33% of max)
                    if (marks < (max * 0.33)) failFlag = true;

                    double percent = (marks * 100.0) / max;
                    String grade = getGrade(percent);
                    System.out.printf("%-15s : %3d / %3d | %6.2f%% | Grade: %s\n", sub, marks, max, percent, grade);

                    if (marks > best) {
                        best = marks;
                        bestSub = sub;
                    }
                    if (marks < worst) {
                        worst = marks;
                        worstSub = sub;
                    }
                }
                double percentage = (total * 100.0) / maxTotal;
                double cgpa = percentage / 9.5;
                System.out.println("-----------------------------------");
                System.out.printf("Total        : %d / %d\n", total, maxTotal);
                System.out.printf("Percentage   : %.2f%%\n", percentage);
                System.out.printf("CGPA         : %.2f\n", cgpa);
                System.out.printf("Best Subject : %s (%d)\n", bestSub, best);
                System.out.printf("Weakest Subj : %s (%d)\n", worstSub, worst);
                System.out.println("-----------------------------------");

                if (failFlag) {
                    System.out.println("FINAL RESULT : FAIL ");
                } else {
                    System.out.println("FINAL RESULT : PASS ");
                }
                System.out.println("============================");
                System.exit(0); // Program exit after showing result
            }
        }
        br.close();
    }

    static String getGrade(double percent) {
        if (percent >= 90) return "A+";
        if (percent >= 80) return "A";
        if (percent >= 70) return "B+";
        if (percent >= 60) return "B";
        if (percent >= 50) return "C";
        if (percent >= 40) return "D";
        return "F";
    }
}
