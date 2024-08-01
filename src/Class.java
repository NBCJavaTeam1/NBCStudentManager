import java.util.ArrayList;
import java.util.List;

// ex) 1반, 2반, 3반...
public class Class {

    private String className;

    private Long classID;

    private List<Student> students;

    Class(String className) {
        // 고유번호 자동 셋팅
        this.classID = ++classID;
        students = new ArrayList<>();
        this.className = className;
    }

    // 학생 추가
    public void setStudents(Student student) {
        students.add(student);
    }

}
