import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        System.out.println("Hello world!");

        List<Subject> RequireSubjects = new ArrayList<Subject>();
        // 과목은 정해져 있으므로 바로 생성
        for (RequireEnum course : RequireEnum.values()) {
            // 필수과목 등록
            RequireSubjects.add(new Subject(course.getCourseName(), "require"));
        }

        // 선택 과목 목록 출력
        for (selectSubjectEnum course : selectSubjectEnum.values()) {
            // 선택과목 등록
            RequireSubjects.add(new Subject(course.getCourseName(), "select"));
        }
    }
}