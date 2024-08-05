import model.Score;
import model.Student;
import model.Subject;

import javax.swing.text.html.Option;
import java.sql.Array;
import java.util.*;

/**
 * Notification
 * Java, 객체지향이 아직 익숙하지 않은 분들은 위한 소스코드 틀입니다.
 * main 메서드를 실행하면 프로그램이 실행됩니다.
 * model 의 클래스들과 아래 (// 기능 구현...) 주석 부분을 완성해주세요!
 * 프로젝트 구조를 변경하거나 기능을 추가해도 괜찮습니다!
 * 구현에 도움을 주기위한 Base 프로젝트입니다. 자유롭게 이용해주세요!
 */
public class Main {
    // 오류방지용 임시 객체
    private static List<Long> tmpSubjects = new ArrayList<Long>();

    // 데이터 저장소
    private static List<Student> studentStore;
    private static List<Subject> subjectStore;
    private static List<Score> scoreStore;

    // json load save 객체
    private static DataManager dataManager;

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

    public static void main(String[] args) throws Exception {
        setInitData();

        try {
            displayMainView();
        } catch (Exception e) {
            System.out.println("\n오류 발생!\n프로그램을 종료합니다.");
        }
    }

    // 초기 데이터 생성
    private static void setInitData() throws Exception {
        dataManager = new DataManager();
        dataManager.directoryInitialize();

        scoreStore = new ArrayList<>();
        scoreStore.add(new Score(0L, 1L));
        scoreStore.add(new Score(1L, 2L));

        dataManager.saveDatas("score", scoreStore);
        studentStore = dataManager.loadDatas("student", Student.class);
        scoreStore = dataManager.loadDatas("score", Score.class);

        if(scoreStore == null) {
            scoreStore = new ArrayList<>();
        }

        if(studentStore == null) {
            studentStore = new ArrayList<>();
        }

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

    private static void displayMainView() throws InterruptedException {
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

    private static void displayScoreView() {
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
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
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
        String studentId = getStudentId(); // 관리할 수강생 고유 번호
        // 기능 구현 (수정할 과목 및 회차, 점수)
        System.out.println("시험 점수를 수정합니다...");
        // 기능 구현
        System.out.println("\n점수 수정 성공!");
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

    // 해당 학생 정보와 과목 정보를 사용하는 Score객체를 반환하는 함수
    private static Score findScoreByStudentIDSubjectId(long studentId, long subjectId) {
        Score[] result = scoreStore.stream()
                .filter((s) ->s.getSubjectId().equals(subjectId) && s.getStudentId().equals(studentId))
                .toArray(Score[]::new);

        if(result.length == 0) {
            return null;
        }

        return result[0];
    }

    // ID로 Subject 가져오는 함수
    private static Subject findSubjectById(Long subjectId) {
        Subject[] result = subjectStore.stream()
                .filter((s) ->s.getSubjectId().equals(subjectId))
                .toArray(Subject[]::new);

        if(result.length == 0) {
            return null;
        }
        return result[0];
    }

    // 이름으로 Subject를 찾아 반환하는 함수
    private static Subject findSubjectByName(String subjectName) {
        Optional<Subject> subject = subjectStore.stream()
                .filter((s)->s.getSubjectName().equals(subjectName))
                .findAny();

        return subject.orElse(null);
    }

    // 해당 강의를 수강중 인지 확인
    private static boolean isBeInClass(Student student, Subject subject) {
        List<Long> subjects = student.getSubjects();

        Long[] result = subjects.stream()
                .filter((s)->s.equals(subject.getSubjectId()))
                .toArray(Long[]::new);

        return result.length > 0;
    }

    // 사용자로부터 입력받은 내용을 토대로 학생 객체를 찾아 반환하는 함수
    private static Student getStudentByInput() throws Exception {
        // 관리할 수강생 고유 번호
        Long studentId = Long.parseLong(getStudentId());
        Student student = findStudentById(studentId);

        // 수강생이 존재하지 않은 경우
        if (student == null) {
            throw new Exception("수강생이 존재하지 않습니다.");
        }

        return student;
    }

    // 사용자로부터 입력받은 내용을 토대로 과목 객체를 찾아 반환하는 함수
    private static Subject getSubjectByInput() throws Exception {
        String subjectName = getSubjectName();
        Subject foundSubject = findSubjectByName(subjectName);

        if (foundSubject == null) {
            throw new Exception("강의가 존재하지 않습니다.");
        }

        return foundSubject;
    }

    private static void preExit() {

    }
    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        try {
            Student student = getStudentByInput();
            Subject foundSubject = getSubjectByInput();
            Score subjectScore = findScoreByStudentIDSubjectId(foundSubject.getSubjectId(), student.getStudentId());

            if (subjectScore == null) {
                throw new Exception("해당 과목 시험을 본 이력이 존재하지 않습니다.");
            }  // 해당 강의를 수강중인지 확인하는 함수
            else if (!isBeInClass(student, foundSubject)) {
                throw new Exception(student.getStudentName() + " 학생은 " + foundSubject.getSubjectName() + " 강의를 수강하지 않습니다");
            }

            // 회차 입력 후 확인하는 함수
            int round = Integer.parseInt(getRound());
            if (round < 1 || round > Score.round) {
                throw new Exception("회차를 확인해주세요.");
            }

            // 회차 점수 확인
            int roundScore = subjectScore.getScores()[round - 1];
            if (roundScore == -1) {
                throw new Exception("해당 회차의 시험을 본 이력이 존재하지 않습니다.");
            }

            System.out.printf("%s의 %s과목 %d회차 등급은 %c입니다.", student.getStudentName(), foundSubject.getSubjectName(), round, subjectScore.getRank()[round]);

        } catch (Exception ex) {
            System.out.println("Error : " + ex.toString());
        }
    }
}
