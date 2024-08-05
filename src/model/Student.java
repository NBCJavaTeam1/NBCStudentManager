package model;

import java.util.List;

public class Student {
    private Long studentId;
    private String studentName;
    private List<Long> subjects;
    private String status; // 상태 (Green, Red, Yellow)


    public Student(Long studentId, String studentName, List<Long> subjects, String status) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.subjects = List.copyOf(subjects);
        this.status = status; // status 필드 초기화
    }

    // Setter
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setSubjects(List<Long> subjects) { this.subjects = List.copyOf(subjects); }
    public void setStatus(String status) { this.status = status; }


    // Getter
    public Long getStudentId() {
        return studentId; //push 실험
    }
    public String getStudentName() {
        return studentName;
    }
    public List<Long> getSubjects() { return subjects; }
    public String getStatus() { return status; }

}

