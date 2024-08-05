package model;

import java.util.List;

public class Student {
    private Long studentId;
    private String studentName;
    private List<Long> subjects;

    public Student(Long studentId, String studentName, List<Long> subjects) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjects = List.copyOf(subjects);
    }

    // Setter
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setSubjects(List<Long> subjects) { this.subjects = List.copyOf(subjects); }

    // Getter
    public Long getStudentId() {
        return studentId; //push 실험
    }
    public String getStudentName() {
        return studentName;
    }
    public List<Long> getSubjects() { return subjects; }
}
