import model.Score;
import model.Student;
import model.Subject;

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
    private static Long sequence(String type) {
        switch (type) {
            // sequence 사용을 위해 Long으로 수정함
            case INDEX_TYPE_STUDENT -> {
                return studentIndex++;
            }
            case INDEX_TYPE_SUBJECT -> {
                return subjectIndex++;
            }
            default -> {
                return scoreIndex++;
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
            System.out.println("3. 수강생 상태 수정");  // 수정됨: 상태 수정 옵션 추가
            System.out.println("4. 메인 화면 이동");  // 수정됨: 메뉴 번호 조정
            System.out.print("관리 항목을 선택하세요...");
            int input = sc.nextInt();

            switch (input) {
                case 1 -> createStudent(); // 수강생 등록
                case 2 -> inquireStudent(); // 수강생 목록 조회
                case 3 -> updateStudentStatus(); // 수정됨: 수강생 상태 수정 추가
                case 4 -> flag = false; // 수정됨: 메인 화면 이동 번호 조정
                default -> {
                    System.out.println("잘못된 입력입니다.\n메인 화면 이동...");
                    flag = false;
                }
            }
        }
    }

    // 과목 선택 루프
    private static void createStudent() {
        // 수강생 이름 입력
        System.out.println("\n수강생을 등록합니다...");
        System.out.print("수강생 이름 입력: ");
        String studentName = sc.next();

        List<Long> selectedSubjects = new ArrayList<>();
        Set<Long> mandatorySubjects = new HashSet<>();  // 필수 과목을 저장할 집합
        Set<Long> choiceSubjects = new HashSet<>();     // 선택 과목을 저장할 집합

        // 과목 선택 루프
        while (true) {
            // 과목 선택 메뉴 출력
            System.out.println("\n과목을 선택하세요. (필수 과목과 선택 과목을 모두 선택해야 합니다)");
            System.out.println("1. 필수 과목 선택");
            System.out.println("2. 선택 과목 선택");
            System.out.println("3. 선택 완료");
            System.out.print("옵션을 선택하세요: ");
            int subjectOption = sc.nextInt();

            if (subjectOption == 1) {
                // 필수 과목 목록 출력
                System.out.println("\n필수 과목 목록:");
                for (Subject subject : subjectStore) {
                    if (subject.getSubjectType() == Subject.SUBJECT_TYPE.SUBJECT_TYPE_MANDATORY) {
                        // 필수 과목일 경우 과목 ID와 이름 출력
                        System.out.println(subject.getSubjectId() + ". " + subject.getSubjectName());
                    }
                }

                // 사용자가 필수 과목의 ID를 입력
                System.out.print("필수 과목 ID를 선택하세요: ");
                Long selectedSubjectId = sc.nextLong();

                // 선택한 과목이 유효한지 확인
                if (isValidSubject(selectedSubjectId, Subject.SUBJECT_TYPE.SUBJECT_TYPE_MANDATORY)) {
                    // 유효하다면 selectedSubjects 리스트와 mandatorySubjects 집합에 추가
                    if (mandatorySubjects.add(selectedSubjectId)) {  // 수정됨: 필수 과목 집합에 추가
                        System.out.println("과목이 추가되었습니다.");
                    } else {
                        System.out.println("이미 선택한 과목입니다.");
                    }
                } else {
                    // 유효하지 않다면 오류 메시지 출력
                    System.out.println("잘못된 과목 ID입니다.");
                }
            } else if (subjectOption == 2) {
                // 선택 과목 목록 출력
                System.out.println("\n선택 과목 목록:");
                for (Subject subject : subjectStore) {
                    if (subject.getSubjectType() == Subject.SUBJECT_TYPE.SUBJECT_TYPE_CHOICE) {
                        // 선택 과목일 경우 과목 ID와 이름 출력
                        System.out.println(subject.getSubjectId() + ". " + subject.getSubjectName());
                    }
                }

                // 사용자가 선택 과목의 ID를 입력
                System.out.print("선택 과목 ID를 선택하세요: ");
                Long selectedSubjectId = sc.nextLong();

                // 선택한 과목이 유효한지 확인
                if (isValidSubject(selectedSubjectId, Subject.SUBJECT_TYPE.SUBJECT_TYPE_CHOICE)) {
                    // 유효하다면 selectedSubjects 리스트와 choiceSubjects 집합에 추가
                    if (choiceSubjects.add(selectedSubjectId)) {  // 수정됨: 선택 과목 집합에 추가
                        System.out.println("과목이 추가되었습니다.");
                    } else {
                        System.out.println("이미 선택한 과목입니다.");
                    }
                } else {
                    // 유효하지 않다면 오류 메시지 출력
                    System.out.println("잘못된 과목 ID입니다.");
                }
            } else if (subjectOption == 3) {
                // 과목 선택 완료
                if (mandatorySubjects.size() >= 3 && choiceSubjects.size() >= 2) {  // 수정됨: 최소 개수 조건 확인
                    selectedSubjects.addAll(mandatorySubjects);  // 수정됨: 필수 과목 추가
                    selectedSubjects.addAll(choiceSubjects);     // 수정됨: 선택 과목 추가
                    break;  // 루프 종료
                } else {
                    // 조건을 만족하지 않은 경우 경고 메시지 출력
                    System.out.println("필수 과목을 최소 3개, 선택 과목을 최소 2개 선택해야 합니다.");
                }
            } else {
                // 유효하지 않은 메뉴 선택
                System.out.println("잘못된 입력입니다. 다시 시도하세요.");
            }
        }

        // 수강생 상태 입력
        System.out.println("\n수강생 상태를 입력하세요 (Green, Red, Yellow 중 하나): ");
        String studentStatus;
        while (true) {
            studentStatus = sc.next().toUpperCase();
            if (studentStatus.equals("GREEN") || studentStatus.equals("RED") || studentStatus.equals("YELLOW")) {
                break;
            } else {
                System.out.println("잘못된 입력입니다. 상태는 Green, Red, Yellow 중 하나여야 합니다.");
            }
        }

        // 수강생 생성 및 저장
        Student newStudent = new Student(sequence(INDEX_TYPE_STUDENT), studentName, selectedSubjects, studentStatus);
        studentStore.add(newStudent);
        System.out.println("\n수강생 등록이 완료되었습니다.");
    }


    // 과목 ID의 유효성을 검사하는 메서드
    private static boolean isValidSubject(Long subjectId, Subject.SUBJECT_TYPE type) {
        for (Subject subject : subjectStore) {
            if (subject.getSubjectId().equals(subjectId) && subject.getSubjectType() == type) {
                return true;
            }
        }
        return false;
    }

    // 수강생 목록 조회
    private static void inquireStudent() {
        System.out.println("\n수강생 목록:");
        for (Student student : studentStore) {
            System.out.println("ID: " + student.getStudentId() + ", 이름: " + student.getStudentName() +
                    ", 상태: " + student.getStatus() + ", 과목: " + getSubjectNames(student.getSubjects()));
        }
    }

    // 과목 ID 리스트를 받아 과목 이름 리스트를 반환하는 메서드
    private static String getSubjectNames(List<Long> subjectIds) {
        List<String> subjectNames = new ArrayList<>();
        for (Long id : subjectIds) {
            for (Subject subject : subjectStore) {
                if (subject.getSubjectId().equals(id)) {
                    subjectNames.add(subject.getSubjectName());
                    break;
                }
            }
        }
        return String.join(", ", subjectNames);
    }

    // 수강생 상태 수정 (수정됨)
    private static void updateStudentStatus2() {
        System.out.println("\n수강생 상태를 수정합니다...");
        System.out.print("수정할 수강생의 ID를 입력하세요: ");
        String studentId = sc.next();
        boolean studentFound = false;

        for (Student student : studentStore) {
            if (student.getStudentId().equals(studentId)) {
                studentFound = true;
                System.out.println("현재 상태: " + student.getStatus());
                System.out.println("새로운 상태를 입력하세요 (Green, Red, Yellow 중 하나): ");
                String newStatus;
                while (true) {
                    newStatus = sc.next().toUpperCase();
                    if (newStatus.equals("GREEN") || newStatus.equals("RED") || newStatus.equals("YELLOW")) {
                        student.setStatus(newStatus); // 수정됨: 상태 수정
                        System.out.println("상태가 수정되었습니다.");
                        break;
                    } else {
                        System.out.println("잘못된 입력입니다. 상태는 Green, Red, Yellow 중 하나여야 합니다.");
                    }
                }
                break;
            }
        }

        if (!studentFound) {
            System.out.println("해당 ID를 가진 수강생을 찾을 수 없습니다.");
        }
    }

    // 과목 ID를 통해 과목을 조회하는 메서드
    private static Subject findSubjectById(Long subjectId) {
        // 과목 저장소(subjectStore)를 순회하여 해당 ID를 가진 과목을 찾음
        for (Subject subject : subjectStore) {
            if (subject.getSubjectId().equals(subjectId)) {
                return subject;  // 과목을 찾으면 반환
            }
        }
        return null;  // 해당 ID의 과목이 없으면 null 반환
    }

    private static void updateStudentStatus() {
        // 수강생 ID 입력
        System.out.print("상태를 수정할 수강생의 ID를 입력하세요: ");
        Long studentId = sc.nextLong();

        // 해당 수강생 찾기
        Student student = findStudentById(studentId);
        if (student == null) {
            System.out.println("해당 ID의 수강생을 찾을 수 없습니다.");
            return;
        }

        // 새로운 상태 입력
        System.out.println("새로운 상태를 입력하세요 (Green, Red, Yellow 중 하나): ");
        String newStatus;
        while (true) {
            newStatus = sc.next();
            if (newStatus.equals("Green") || newStatus.equals("Red") || newStatus.equals("Yellow")) {
                break;  // 유효한 상태 값이 입력되면 루프 종료
            } else {
                System.out.println("잘못된 입력입니다. 다시 입력하세요.");
            }
        }

        // 수강생의 상태 업데이트
        student.setStatus(newStatus);
        System.out.println("상태가 성공적으로 변경되었습니다.");
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

}
