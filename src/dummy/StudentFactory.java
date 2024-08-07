package dummy;

import model.Student;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StudentFactory {
    final String[] FAMILY_NAMES = { "김", "이", "박", "최", "정", "강", "조", "윤", "장", "임", "한", "오"};
    final String[] FIRST_NAMES = {"민준", "서준", "도윤", "시우","지호","지후","도현","건우","우진","선우","연우","서진","현준",
            "시윤","윤우","지우","유찬","수호","승민","진우","민성","지원","시현","한결","지안","시원","윤호","은호","서우","우주",
            "민규","민찬","하율","준","지율","승준","현서","민호","로","윤재","이현","지성","하민","민혁","성준","태양","도하",
            "예찬","다온","이든","주안"
    };
    final String[] STATUS = {"Red", "Yello", "Green"};

    final int MIN_MANDATORY_COUNT = 3;
    final int MIN_CHOICE_COUNT = 2;

    private Long[] mantatorySubjectIds;
    private Long[] choiceSubjectIds;
    private Long studentId;

    public StudentFactory() {
        studentId = 0L;
    }

    public void initialize() {
        mantatorySubjectIds = new Long[DummyDataFactory.MANDATORY_TYPE_SUBJECT_COUNT];
        choiceSubjectIds = new Long[DummyDataFactory.CHOICE_TYPE_SUBJECT_COUNT];

        for(long i = 0; i < DummyDataFactory.MANDATORY_TYPE_SUBJECT_COUNT; ++i) {
            mantatorySubjectIds[(int)i] = i;
        }

        for(long i = 0; i < DummyDataFactory.CHOICE_TYPE_SUBJECT_COUNT; ++i) {
            choiceSubjectIds[(int)i] = DummyDataFactory.MANDATORY_TYPE_SUBJECT_COUNT + i;
        }
    }

    // 무작위 학생 이름 생성해주는 함수
    public String createStudentName(Random rand) {
        return FAMILY_NAMES[rand.nextInt(FAMILY_NAMES.length)] + FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)];
    }

    // 무작위 과목들을 추출하는 함수
    public List<Long> createSubjects(Random rand) {
        int mandatorySubjectCount = rand.nextInt(MIN_MANDATORY_COUNT, mantatorySubjectIds.length + 1);
        int choiceSubjectCount = rand.nextInt(MIN_CHOICE_COUNT, choiceSubjectIds.length + 1);

        List<Long> mandatorySubjects = Utility.permutation(mantatorySubjectIds, rand, mandatorySubjectCount);
        List<Long> choiceSubjects = Utility.permutation(choiceSubjectIds, rand, choiceSubjectCount);

        return Stream.concat(mandatorySubjects.stream(), choiceSubjects.stream())
                .collect(Collectors.toList());
    }

    // 랜덤한 스테이트 생성하는 함수
    public String createStatus(Random rand) {
        return STATUS[rand.nextInt(STATUS.length)];
    }

    // 랜덤한 학생 생성하는 함수
    public Student createRandomStudent(Random rand) {
        return new Student(studentId++, createStudentName(rand), createSubjects(rand), createStatus(rand));
    }
}
