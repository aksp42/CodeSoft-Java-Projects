package models;

public class Teacher {
    private String teacherId;
    private String name;
    private String role; // HOD / Coordinator / Faculty
    private String assignedSections;
    private String subjects;
    private String mentorRollRange;
    private String username;
    private String password;

    // ✅ Constructor
    public Teacher(String teacherId, String name, String role,
                   String assignedSections, String subjects,
                   String mentorRollRange, String username, String password) {
        this.teacherId = teacherId;
        this.name = name;
        this.role = role;
        this.assignedSections = assignedSections;
        this.subjects = subjects;
        this.mentorRollRange = mentorRollRange;
        this.username = username;
        this.password = password;
    }

    //  Getters
    public String getTeacherId() { return teacherId; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public String getAssignedSections() { return assignedSections; }
    public String getSubjects() { return subjects; }
    public String getMentorRollRange() { return mentorRollRange; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // Role checks
    public boolean isCoordinator() { return role.equalsIgnoreCase("Coordinator"); }
    public boolean isHOD() { return role.equalsIgnoreCase("HOD"); }

    //  Display info
    public void displayInfo() {
        System.out.println("===== Teacher Info =====");
        System.out.println("ID: " + teacherId + " | Name: " + name);
        System.out.println("Role: " + role + " | Sections: " + assignedSections);
        System.out.println("Subjects: " + subjects);
        System.out.println("Mentor Roll No Range: " + mentorRollRange);
        System.out.println("Username: " + username);
        System.out.println("-------------------------------");
    }

    //  Safe CSV helper (if needed in future)
    public static String safeString(String value) {
        return value == null ? "" : value.trim();
    }
}
