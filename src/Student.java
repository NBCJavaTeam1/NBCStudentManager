import java.util.List;

public class Student {

    // 고유 번호
    private Long studentId = 0L;

    // 이름
    private String name;

    // 과목 목록
    private List<Subject> studentSubjectList;

    Student(String name, List<Subject> studentSubjectList){
        this.studentId = ++studentId;
        this.name = name;
        this.studentSubjectList = studentSubjectList;
    }
}
