package dummy;

import model.Score;
import model.Student;

import java.util.*;

public class DummyDataFactory {
    Random rand;
    StudentFactory studentFactory;
    ScoreFactory scoreFactory;

    static int MANDATORY_TYPE_SUBJECT_COUNT = 5;
    static int CHOICE_TYPE_SUBJECT_COUNT = 4;

    public DummyDataFactory() {
        rand = new Random();
        studentFactory = new StudentFactory();
        scoreFactory = new ScoreFactory();

        studentFactory.initialize();
    }

    public Student getRandomStudent(List<Student> students) {
        int index = rand.nextInt(students.size());

        return students.get(index);
    }

    public long getRandomSubject(List<Long> subjects) {
        int index = rand.nextInt(subjects.size());

        return subjects.get(index);
    }

    // 학생 더미데이터를 추가해주는 함수
    public List<Student> createStudentDummyData(int count) {
        List<Student> students = new ArrayList<Student>();

        for (int i = 0; i < count; i++) {
            students.add(studentFactory.createRandomStudent(rand));
        }

        return students;
    }


    // Score 더미데이터 생성하는 함수
    public List<Score> createScoreDummyData(int count, List<Student> students) {
        List<Score> scores = new ArrayList<Score>();
        // 학생 과목 아이디 조합으로 이미 생성된 조합인지 확인하기 위한 용도
        Set<String> createdCombination = new HashSet<String>();

        while(scores.size() < count) {
            Student student = getRandomStudent(students);
            long subjectId = getRandomSubject(student.getSubjects());
            String combination = String.format("%d %d", student.getStudentId(), subjectId);

            // 이미 생성된 경우
            if(createdCombination.contains(combination)) {
               continue;
            }

            createdCombination.add(combination);
            scores.add(scoreFactory.createRandomScore(rand, student.getStudentId(), subjectId));
        }

        return scores;
    }
}
