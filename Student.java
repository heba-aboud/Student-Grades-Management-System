public class Student {

    private String studentId;
    private String studentName;
    private int grade;

    public Student(String studentId, String studentName, int grade) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.grade = grade;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId
                + " Name: " + studentName
                + " Grade: " + grade;
    }
}
