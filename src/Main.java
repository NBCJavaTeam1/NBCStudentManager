import com.google.gson.internal.bind.util.ISO8601Utils;
import model.Score;
import model.Student;
import model.Subject;

import javax.swing.text.html.Option;
import java.sql.Array;
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
    private static String PATTERN_ONLY_0_BETWEEN_100 = "^(100|[1-9]?[0-9]|0)$";


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

        // issue : try catch문 위치 변경
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

        // issue : 학생 정보 불러올 때, 고유 번호 인덱스 늘려줘야함
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

    private static void displayMainView() throws Exception {
        boolean flag = true;
        while (flag) {
            System.out.println("\n==================================");
            System.out.println("내일배움캠프 수강생 관리 프로그램 실행 중...");
            System.out.println("1. 수강생 관리");
            System.out.println("2. 점수 관리");
            System.out.println("3. 프로그램 종료");
            System.out.print("관리 항목을 선택하세요...");
            int input;
            // 수정됨: 처음 화면에서 문자열 입력시 강제종료되지 않음
            try {
                input = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                sc.next(); // 잘못된 입력을 소비하여 다음 입력을 받을 수 있도록 함
                continue; // 루프를 계속 실행하도록 설정
            }

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
        preExit();
        System.out.println("프로그램을 종료합니다.");
    }

    private static void displayStudentView() throws Exception {
        boolean flag = true;
        while (flag) {
            System.out.println("==================================");
            System.out.println("수강생 관리 실행 중...");
            System.out.println("1. 수강생 등록");
            System.out.println("2. 수강생 목록 조회");
            System.out.println("3. 수강생 상태 수정");  // 수정됨: 상태 수정 옵션 추가
            System.out.println("4. 메인 화면 이동");  // 수정됨: 메뉴 번호 조정
            System.out.print("관리 항목을 선택하세요...");
            int input;

            // 수정됨: 문자열 입력시 강제종료되지 않게 try-catch문 추가
            try {
                input = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력해주세요.");
                sc.next(); // 잘못된 입력을 소비하여 다음 입력을 받을 수 있도록 함
                continue; // 루프를 계속 실행하도록 설정
            }

            try {
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
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    // 과목 선택 루프
    private static void createStudent() throws Exception {
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
                // issue : 문자열 입력시 프로그램 종료 이슈
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
        // issue : 수강생 정보 등록시 고유번호 충돌 처리
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
        System.out.println("\n수강생 목록을 조회합니다...");
        System.out.println("상태별 조회는 1을, 전체 목록 보기는 2번을 눌러주세요.");
        int StudentSearch = sc.nextInt();
        sc.nextLine();

        if (StudentSearch == 1) {
            inquireStudentsByStatus();
        } else if (StudentSearch == 2) {
            inquireAllStudents();
        } else {
            System.out.println("잘못된 입력.");
        }

        System.out.println("\n수강생 목록 조회 성공!");
    }

    private static void inquireAllStudents() {
        if (studentStore.isEmpty()) {
            System.out.println("등록된 수강생이 없습니다.");
        } else {
            for (Student student : studentStore) {
                System.out.println("==================================");
                System.out.println("이름 :" + student.getStudentName());
                System.out.println("고유 번호 :" + student.getStudentId());
                System.out.println("상태 :" + student.getStatus());
                System.out.println("수강 과목 :" + student.getSubjects());
                System.out.println("==================================");
            }
        }
    }

    private static void inquireStudentsByStatus() {
        System.out.println("상태별 수강생 목록을 조회합니다...");
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

            try {
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
            } catch(Exception ex){
                System.out.println(ex.getMessage());
            }
        }
    }

    private static Long getStudentId() {
        System.out.print("\n관리할 수강생의 번호를 입력하시오...");
        Long sId = sc.nextLong();
        sc.nextLine();
        return sId;
    }

    private static String getSubjectId(String studentId) throws Exception {

        // 타입이 안맞아서 안나오기 때문에 형변환이 필요함
        Long longStudentId = Long.parseLong(studentId);

         List<Long> studentSubjects = studentStore.stream()
                 .filter(student -> student.getStudentId().equals(longStudentId))
                 .map(Student::getSubjects)
                 .findFirst().orElseThrow(() -> new Exception("존재하지 않는 학생입니다."));

        // subjectIndex 값이 ids 리스트에 있는 Subject를 찾기
        List<Subject> matchingSubjects = subjectStore.stream()
                .filter(subject -> studentSubjects.contains((long) subject.getSubjectId()))
                .collect(Collectors.toList());

        matchingSubjects.forEach(subject ->
                System.out.println(subject.getSubjectId() + ". " + subject.getSubjectName()));

        System.out.print("수정하실 과목을 입력하세요.\n");

        return sc.next();
    }

    // 수강생의 과목별 시험 회차 및 점수 등록
    private static void createScore() {
        Long studentId = getStudentId(); // 관리할 수강생 고유 번호
        System.out.println("시험 점수를 등록합니다...");
        // 기능 구현
        boolean addflag = false;
        Long subId = 0L;
        String subType = "";
        int round;
        int score;

        // issue : 존재하지 않는 수강생 고유번호가 들어올 경우 예외처리
        while(true){ // 올바른 과목명을 입력할 때 까지 무한 반복
            boolean scoreflag = false; // 올바른 과목명일 경우 true로 만들어 반복문을 탈출하기 위한 flag
            System.out.println("등록할 과목을 입력하세요 : ");
            String sub = sc.nextLine();

            for(int i=0;i<subjectStore.size();i++){
                if(sub.equals(subjectStore.get(i).getSubjectName())){ //subjectStore를 순회하며 각 내용물 객체의 getSubjectName()을 호출하여 입력값과 비교
                    scoreflag = true;
                    subId = subjectStore.get(i).getSubjectId();
                    subType = String.valueOf(subjectStore.get(i).getSubjectType());
                    break;
                }
            }
            if(scoreflag){
                break;
            }
            else
                System.out.println("올바른 과목명을 입력하세요");
        }

        while(true){
            try{
                System.out.println("등록할 회차를 입력하세요 : ");
                round = sc.nextInt()-1;
                sc.nextLine();
                if(round < 0 || round > 9){
                    System.out.println("1부터 10까지의 숫자 중 하나를 입력하세요");
                    continue;
                }
                else{
                    break;
                }
            }catch(InputMismatchException e){ //숫자 이외의 값이 입력된다면 예외처리
                System.out.println("1부터 10까지의 숫자 중 하나를 입력하세요");
                continue;
            }

        }
        while(true){
            try{
                System.out.println("등록할 점수를 입력하세요 : ");
                // issue : 문자열 입력시 오류 체크
                score = sc.nextInt();
                sc.nextLine();
                if(score<0 || score>100){
                    System.out.println("0부터 100까지의 정수중 하나를 입력하세요");
                    continue;
                }
                else{
                    break;
                }
            }catch(InputMismatchException e){
                System.out.println("0부터 100까지의 정수중 하나를 입력하세요");
                continue;
            }
        }
        Score s = new Score(studentId,subId,subType);
        s.setScores(round,score);
        if(!scoreStore.isEmpty()){
            for(int i=0;i<scoreStore.size();i++){
                // issue : 해당 학생이 듣고 있는지에 대한 여부 체크 ( isBeInClass )
                if(studentId.equals(scoreStore.get(i).getStudentId()) && subId.equals(scoreStore.get(i).getSubjectId())){
                    int[] tempS = Arrays.copyOf(scoreStore.get(i).getScores(), scoreStore.get(i).getScores().length);
                    if(tempS[round]!=-1){
                        System.out.println("이미 등록된 회차 입니다.");

                        // 각 학생의 회차 점수들이 리스트에 제대로 등록되는지 확인하기 위한 반복문
                        for(int k=0;k<scoreStore.size();k++){
                            System.out.println(Arrays.toString(scoreStore.get(k).getScores()) + ", "+ scoreStore.get(k).getStudentId()+", "+scoreStore.get(k).getSubjectId());
                        }
                        return;
                    }
                    else{
                        addflag = true;
                        tempS[round] = score;
                        scoreStore.get(i).setScores(round,score);
                        break;
                    }
                }
            }
            if(!addflag){
                scoreStore.add(s);
            }
        }
        else
            scoreStore.add(s);

        // 각 학생의 회차 점수들이 리스트에 제대로 등록되는지 확인하기 위한 반복문
        for(int i=0;i<scoreStore.size();i++){
            System.out.println(Arrays.toString(scoreStore.get(i).getScores())+", "+ scoreStore.get(i).getStudentId()+", "+scoreStore.get(i).getSubjectId());
        }

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

            String insertStudentId = String.valueOf(getStudentId()); // 관리할 수강생 고유 번호
            if(!checkPattern(PATTERN_ONLY_INTEGER, insertStudentId)) {
                System.out.println("잘못된 입력 형태입니다.\n이전 단계로 이동...");
                break;
            }

            // 없는 학생을 조회하는 경우 exception
            String insertSubjectId;
            try {
                insertSubjectId = getSubjectId(insertStudentId);

                if(!checkPattern(PATTERN_ONLY_INTEGER, insertSubjectId)) {
                    System.out.println("잘못된 입력 형태입니다.\n이전 단계로 이동...");
                    break;
                }
            } catch(Exception e) {
                System.out.println(e.getMessage());
                flag = false;
                break;
            }

            // issue 숫자 5입력시 오류 발생
            System.out.print("\n수정하실 과목을 시험회차를 입력하세요. (1~10 회차 중 선택)");
            int insertRound = sc.nextInt();
            if(!checkPattern(PATTERN_ONLY_1_BETWEEN_10, String.valueOf(insertRound))) {
                System.out.println("잘못된 입력 형태입니다. 시험회차는 1~10 까지의 숫자만 입력가능합니다.\n이전 단계로 이동...");
                break;
            }


            // issue 숫자 1입력시 오류 발생
            System.out.print("\n수정하실 점수를 입력해주세요.");
            int insertScore = sc.nextInt();
            sc.nextLine();
            if(!checkPattern(PATTERN_ONLY_0_BETWEEN_100, String.valueOf(insertScore))) {
                System.out.println("잘못된 입력 형태입니다. 점수는 0~100 까지의 숫자만 입력가능합니다.\n이전 단계로 이동...");
                break;
            }
            
            // issue : 시험본 이력이 없는 경우 체크
            boolean checkStudentSubject = scoreStore.stream()
                    .filter(score -> Long.parseLong(insertStudentId) == (score.getStudentId()) &&
                            Long.parseLong(insertSubjectId) == (score.getSubjectId()))
                    .findFirst() // 첫 번째 일치하는 요소를 찾습니다.
                    .map(score -> {
                        int[] scores = score.getScores();
                        scores[insertRound - 1] = insertScore;

                        // 점수가 바뀌었으니 랭크도 바꿔준다
                        score.setScores(insertRound - 1, insertScore);

                        System.out.println("\n점수 수정 성공!");
                        return true;
                    }).orElseGet(() -> {
                        System.out.println("해당 과목을 찾을 수 없습니다.");
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

    // 해당 학생 정보와 과목 정보를 사용하는 Score객체를 반환하는 함수
    private static Score findScoreByStudentIDSubjectId(long subjectId, long studentId) {
        Score[] result = scoreStore.stream()
                .filter((s) ->s.getSubjectId().equals(subjectId) && s.getStudentId().equals(studentId))
                .toArray(Score[]::new);

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

    // 수강생의 특정 과목 회차별 등급 조회
    private static Student getStudentByInput() throws Exception {
        Long studentId = getStudentId(); // 관리할 수강생 고유 번호
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

    // 종료 전 데이터 저장
    private static void preExit() throws Exception {
        dataManager.saveDatas("score", scoreStore);
        dataManager.saveDatas("student", studentStore);
    }

    // 수강생의 특정 과목 회차별 등급 조회
    private static void inquireRoundGradeBySubject() {
        try {
            Student student = getStudentByInput();
            // 무슨 강의 듣고 있는지 확인
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

    private static <T> boolean checkPattern(String pattern, T t){
        return Pattern.matches(pattern, (CharSequence) t);
    }

}
