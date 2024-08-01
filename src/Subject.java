import java.util.ArrayList;
import java.util.List;

public class Subject {

    // 과목 고유번호
    private Long subjectId;

    // 과목병
    private String subjectName;

    // 과목타입
    private String subjectType;

    // 학생 리스트
    private List<Student> studentList;

    Subject(String subjectName, String subjectType){
        this.subjectId = ++subjectId;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
        this.studentList = new ArrayList<Student>();
    }
}
