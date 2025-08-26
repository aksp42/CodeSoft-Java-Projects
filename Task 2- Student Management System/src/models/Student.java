package models;

public class Student {
    private String uniqueId;
    private String rollNo;
    private String name;
    private String section;
    private String semester;
    private String mentorId;
    private String coordinatorId;

    private int attPython;
    private int attDbms;
    private int attStats;
    private int attFods;
    private int attHpc;

    private String asgPython;
    private String asgDbms;
    private String asgStats;
    private String asgFods;
    private String asgHpc;

    private double cgpa;
    private String skills;
    private double totalAttendance;

    // Constructor (keep as it is)
    public Student(String uniqueId, String rollNo, String name, String section, String semester,
                   String mentorId, String coordinatorId,
                   int attPython, int attDbms, int attStats, int attFods, int attHpc,
                   String asgPython, String asgDbms, String asgStats, String asgFods, String asgHpc,
                   double cgpa, String skills, double totalAttendance) {
        this.uniqueId = uniqueId;
        this.rollNo = rollNo;
        this.name = name;
        this.section = section;
        this.semester = semester;
        this.mentorId = mentorId;
        this.coordinatorId = coordinatorId;
        this.attPython = attPython;
        this.attDbms = attDbms;
        this.attStats = attStats;
        this.attFods = attFods;
        this.attHpc = attHpc;
        this.asgPython = asgPython;
        this.asgDbms = asgDbms;
        this.asgStats = asgStats;
        this.asgFods = asgFods;
        this.asgHpc = asgHpc;
        this.cgpa = cgpa;
        this.skills = skills;

        // Automatically calculate total attendance
        updateTotalAttendance();
    }

    // Getters
    public String getUniqueId() { return uniqueId; }
    public String getRollNo() { return rollNo; }
    public String getName() { return name; }
    public String getSection() { return section; }
    public String getSemester() { return semester; }
    public String getMentorId() { return mentorId; }
    public String getCoordinatorId() { return coordinatorId; }

    public int getAttPython() { return attPython; }
    public int getAttDbms() { return attDbms; }
    public int getAttStats() { return attStats; }
    public int getAttFods() { return attFods; }
    public int getAttHpc() { return attHpc; }

    public String getAsgPython() { return asgPython; }
    public String getAsgDbms() { return asgDbms; }
    public String getAsgStats() { return asgStats; }
    public String getAsgFods() { return asgFods; }
    public String getAsgHpc() { return asgHpc; }

    public double getCgpa() { return cgpa; }
    public String getSkills() { return skills; }
    public double getTotalAttendance() { return totalAttendance; }

    // Display Info
    public void displayInfo() {
        System.out.println("===== Student Info =====");
        System.out.println("Name: " + name + " (" + rollNo + ")");
        System.out.println("Unique ID: " + uniqueId);
        System.out.println("Section: " + section + " | Semester: " + semester);
        System.out.println("Mentor ID: " + mentorId + " | Coordinator ID: " + coordinatorId);
        System.out.println("Attendance - Python: " + attPython + "%, DBMS: " + attDbms +
                "%, Stats: " + attStats + "%, FODS: " + attFods + "%, HPC: " + attHpc + "%");
        System.out.println("Assignments - Python: " + asgPython + ", DBMS: " + asgDbms +
                ", Stats: " + asgStats + ", FODS: " + asgFods + ", HPC: " + asgHpc);
        System.out.println("CGPA: " + cgpa + " | Skills: " + skills);
        System.out.println("Total Attendance: " + totalAttendance + "%");
        System.out.println("-------------------------------");
    }

    // Update total attendance automatically
    public void updateTotalAttendance() {
        this.totalAttendance = (attPython + attDbms + attStats + attFods + attHpc) / 5.0;
    }

    // Helper methods for safe parsing from CSV
    public static int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static double parseDoubleSafe(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
