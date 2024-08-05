import model.Score;
import model.Student;
import model.Subject;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Notification
 * Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다.
 * main 메서드를 실행하면 프로그램이 실행됩니다.
 * model 의 클래스들과 아래 (// 기능 구현...) 주석 부분을 완성해주세요!
 * 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다!
 * 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게 이용해주세요!
 */
public class Main {

    // 숫자만 입력가능 패턴
    private static String PATTERN_ONLY_INTEGER = "\\d+";
    // 1~10 까지만 입력가능 패턴
    private static String PATTERN_ONLY_1_BETWEEN_10 = "^(10|[1-9])$";
    // 0~100 까지만 입력가능 패턴
    private static String PATTERN_ONLY_0_BETWEEN_100 = "^(100|[1-9]?[0-9])$";


    // 오류방지용 임시 객체
    private static List<Long> tmpSubjects = new ArrayList<Long>();

    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // 과목 타입
    private static String SUBJECT_TYPE_MANDATORY = "MANDATORY";
    private static String SUBJECT_TYPE_CHOICE = "CHOICE";

    // index 관리 필드
    private static long studentIndex;
    private static final String INDEX_TYPE_STUDENT = "ST";
    private static long subjectIndex;
    private static final String INDEX_TYPE_SUBJECT = "SU";
    private static long scoreIndex;
    private static final String INDEX_TYPE_SCORE = "SC";

    // 스캐너
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        setInitData();
        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private static void setInitData() {
        studentStore = new ArrayList<>();
        tmpSubjects = List.of(0L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L );
        subjectStore = List.of(
                new Subject(
                        subjectIndex++,
                        "Java",
                        Subject.SUBJECT_TYPE.SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        subjectIndex++,
                        "객체지향",
                        Subject.SUBJECT_TYPE.SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        subjectIndex++,
                        "Spring",
                        Subject.SUBJECT_TYPE.SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        subjectIndex++,
                        "JPA",
                        Subject.SUBJECT_TYPE.SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        subjectIndex++,
                        "MySQL",
                        Subject.SUBJECT_TYPE.SUBJECT_TYPE_MANDATORY
                ),
                new Subject(
                        subjectIndex++,
                        "디자인 패턴",
                        Subject.SUBJECT_TYPE.SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        subjectIndex++,
                        "Spring Security",
                        Subject.SUBJECT_TYPE.SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        subjectIndex++,
                        "Redis",
                        Subject.SUBJECT_TYPE.SUBJECT_TYPE_CHOICE
                ),
                new Subject(
                        subjectIndex++,
                        "MongoDB",
                        Subject.SUBJECT_TYPE.SUBJECT_TYPE_CHOICE
                )
        );
        scoreStore = new ArrayList<>();
    }

    // index 자동 증가
    private static String sequence(String type) {
        switch (type) {
            case INDEX_TYPE_STUDENT -> {
                studentIndex++;
                return INDEX_TYPE_STUDENT + studentIndex;
            }
            case INDEX_TYPE_SUBJECT -> {
                subjectIndex++;
                return INDEX_TYPE_SUBJECT + subjectIndex;
            }
            default -> {
                scoreIndex++;
                return INDEX_TYPE_SCORE + scoreIndex;
            }
        }
    }

    private static void displayMainView() throws Exception, InterruptedException {
        boolean flag = true;
        while (flag) {
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> displayStudentView(); // 수강생 관리
                case 2 -> displayScoreView(); // 점수 관리
                case 3 -> flag = false; // 프로그램 종료
                default -> {
                    System.out.println("잘못된 입력입니다.\n되돌아갑니다!");
                    Thread.sleep(2000);
                }
            }
        }
        System.out.println("프로그램을 종료합니다.");
    }

    private static void displayStudentView() {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    // 수강생 등록 ( 예환씨 파트 )
    private static void createStudent() {
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();
        // 기능 구현 (필수 과목, 선택 과목)


        // tmpSubject : 오류 방지용 임시 객체
        Student student = new Student(studentIndex++, studentName, tmpSubjects ); // 수강생 인스턴스 생성 예시 코드
        // 기능 구현
        System.out.println("수강생 등록 성공!\n");
    }

    // 수강생 목록 조회
    private static void inquireStudent() {
        System.out.println("\n수강생 목록을 조회합니다...");
        // 기능 구현
        System.out.println("\n수강생 목록 조회 성공!");
    }

    private static void displayScoreView() throws Exception {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("점수 관리 실행 중...");
            System.out.println("1. 수강생의 과목별 시험 회차 및 점수 등록");
            System.out.println("2. 수강생의 과목별 회차 점수 수정");
            System.out.println("3. 수강생의 특정 과목 회차별 등급 조회");
            System.out.println("4. 메인 화면 이동");
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createScore(); // 수강생의 과목별 시험 회차 및 점수 등록
                case 2 -> updateRoundScoreBySubject(); // 수강생의 과목별 회차 점수 수정
                case 3 -> inquireRoundGradeBySubject(); // 수강생의 특정 과목 회차별 등급 조회
                case 4 -> flag = false; // 메인 화면 이동
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    private static String getStudentId() {
        System.out.print("관리할 수강생의 번호를 입력하시오...\n");

        // 수강생들의 아이디를 출력
        for(Student student : studentStore){
            System.out.println(student.getStudentId() + ". " + student.getStudentName());
        }

        return sc.next();
    }

    private static String getSubjectId(String studentId) {

        System.out.print("수정하실 과목을 입력하세요.\n");

        // 타입이 안맞아서 안나오기 때문에 형변환이 필요함
        Long longStudentId = Long.parseLong(studentId);

        List<Long> studentSubjects  = studentStore.stream()
                .filter(student -> student.getStudentId().equals(longStudentId))
                .map(Student::getSubjects)
                .findFirst().get();

        // subjectIndex 값이 ids 리스트에 있는 Subject를 찾기
        List<Subject> matchingSubjects = subjectStore.stream()
                .filter(subject -> studentSubjects.contains((long) subject.getSubjectId()))
                .collect(Collectors.toList());

        matchingSubjects.forEach(subject ->
                System.out.println(subject.getSubjectId() + ". " + subject.getSubjectName()));

        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");
        // 기능 구현

        while(true){ // 올바른 과목명을 입력할 때 까지 무한 반복
            boolean scoreflag = false; // 올바른 과목명일 경우 true로 만들어 반복문을 탈출하기 위한 flag
            System.out.println("등록할 과목을 입력하세요 : ");
            String sub = sc.nextLine();
            for(int i=0;i<subjectStore.size();i++){
                if(sub.equals(subjectStore.get(i).getSubjectName())){ //subjectStore를 순회하며 각 내용물 객체의 getSubjectName()을 호출하여 입력값과 비요
                    scoreflag = true;
                    break;
                }
            }
            if(scoreflag){
                break;
            }
            else
                System.out.println("올바른 과목명을 입력하세요");
        }

//        while(true){
//            boolean scoreflag = false;
//            System.out.println("등록할 회차를 입력하세요 : ");
//            int round = sc.nextInt()+1;
//            if(round<1 && round > 10){
//
//            }
//        }

        //System.out.println("등록할 점수를 입력하세요 : ");
        int score = sc.nextInt();
        System.out.println("\n점수 등록 성공!");
    }


    // 수강생의 과목별 회차 점수 수정
    private static void updateRoundScoreBySubject() {
        boolean flag = true;
        while (flag){
            if(scoreStore.isEmpty()) {
                System.out.print("점수 데이터가 존재하지 않습니다. 이전 단계로 이동합니다.\n");
                flag = false;
                break;
            }

            String insertStudentId = getStudentId(); // 관리할 수강생 고유 번호
            if(!checkPattern(PATTERN_ONLY_INTEGER, insertStudentId)) {
                System.out.println("잘못된 입력 형태입니다.\n이전 단계로 이동...");
                break;
            }

            String insertSubjectId = getSubjectId(insertStudentId);
            if(!checkPattern(PATTERN_ONLY_INTEGER, insertSubjectId)) {
                System.out.println("잘못된 입력 형태입니다.\n이전 단계로 이동...");
                break;
            }

            System.out.print("\n수정하실 과목을 시험회차를 입력하세요. (1~10 회차 중 선택)");
            int insertRound = sc.nextInt();
            if(!checkPattern(PATTERN_ONLY_1_BETWEEN_10, String.valueOf(insertSubjectId))) {
                System.out.println("잘못된 입력 형태입니다. 시험회차는 1~10 까지의 숫자만 입력가능합니다.\n이전 단계로 이동...");
                break;
            }

            System.out.print("\n수정하실 점수를 입력해주세요.");
            int insertScore = sc.nextInt();
            if(!checkPattern(PATTERN_ONLY_0_BETWEEN_100, String.valueOf(insertScore))) {
                System.out.println("잘못된 입력 형태입니다. 점수는 0~100 까지의 숫자만 입력가능합니다.\n이전 단계로 이동...");
                break;
            }

            boolean checkStudentSubject = scoreStore.stream()
                    .filter(score -> Long.parseLong(insertStudentId) == (score.getStudentId()) &&
                            Long.parseLong(insertSubjectId) == (score.getSubjectId()))
                    .findFirst() // 첫 번째 일치하는 요소를 찾습니다.
                    .map(score -> {
                        int[] scores = score.getScores();

                        scores[insertRound - 1] = insertScore;
                        score.setScores(scores);

                        // 점수가 바뀌었으니 랭크도 바꿔준다
                        score.init(insertRound - 1, insertScore);

                        System.out.println("\n점수 수정 성공!");
                        return true;
                    }).orElseGet(() -> {
                        System.out.println("해당 학생이나 과목을 찾을 수 없습니다.");
                        return false;
                    });

            flag = false;
        }

    }

    private static String getSubjectName() {
        System.out.print("\n과목 이름을 입력하시오...");
        return sc.next();
    }

    private static String getRound() {
        System.out.print("\n회차를 입력하시오...");
        return sc.next();
    }

    // 학생을 찾는 함수
    private static Student findStudentById(Long studentId) {
        Student[] result = studentStore.stream()
                .filter((s)->s.getStudentId().equals(studentId))
                .toArray(Student[]::new);

        if(result.length == 0) {
            return null;
        }
        return result[0];
    }

    private static Subject findSubjectByName(String subjectName) {
        Subject[] result = subjectStore.stream()
                .filter((s) ->s.getSubjectName().equals(subjectName))
                .toArray(Subject[]::new);

        if(result.length == 0) {
            return null;
        }
        return result[0];
    }

    private static Score[] findScoreBySubjectName(String subjectName) {
        Score[] result = scoreStore.stream()
               // .filter((s) ->s.getSubjectName().equals(subjectName))
                .toArray(Score[]::new);

        if(result.length == 0) {
            return null;
        }
        return result;
    }

    private static boolean isBeInClass(Student student, Subject subject) {
        List<Long> subjects = student.getSubjects();

        Long[] result = subjects.stream()
                .filter((s)->s.equals(subject.getSubjectId()))
                .toArray(Long[]::new);

        return result.length > 0;
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        Long studentId = Long.parseLong(getStudentId()); // 관리할 수강생 고유 번호
        Student student = findStudentById(studentId);

        // 수강생이 존재하지 않은 경우
        if(student == null) {
            System.out.println("수강생이 존재하지 않습니다.");
            return;
        }

        String subjectName = getSubjectName();
        Subject foundSubject = findSubjectByName(subjectName);

        if(foundSubject == null) {
            System.out.println("강의가 존재하지 않습니다.");
            return;
        } else if(isBeInClass(student, foundSubject)) {
            System.out.println(student.getStudentName() + " 학생은 " + foundSubject.getSubjectName() + " 강의를 수강하지 않습니다");
            return;
        }

        int round = Integer.parseInt(getRound());
        Score[] subjectScores = findScoreBySubjectName(subjectName);

        if(subjectScores == null) {
            System.out.println("시험을 본 이력이 존재하지 않습니다.");
        }

        // 기능 구현 (조회할 특정 과목)

        // 기능 구현
        System.out.println("\n등급 조회 성공!");
    }

    private static <T> boolean checkPattern(String pattern, T t){
        return Pattern.matches(pattern, (CharSequence) t);
    }

}
