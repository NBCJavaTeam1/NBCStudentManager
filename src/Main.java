import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // 과목은 정해져 있으므로 바로 생성
//        Map<CourseType, List<Subject>> subjects = new HashMap<>();
//
//        List<Subject> requireSubjects = new ArrayList<>();
//        List<Subject> selectSubjects = new ArrayList<>();
//
//        for (Course course : Course.values()) {
//            // 필수과목 등록
//            Subject subject = new Subject(course.getCourseName(), course.getCourseType());
//            switch (course.getCourseType()){
//                case CourseType.REQUIRE:
//                    requireSubjects.add(subject);
//                    break;
//                case CourseType.SELECT:
//                    selectSubjects.add(subject);
//                    break;
//            }
//
//        }
//
//        subjects.put(CourseType.REQUIRE, requireSubjects);
//        subjects.put(CourseType.SELECT, selectSubjects);
//
//        System.out.println(subjects.toString());
    }
}