package model;

import java.util.Arrays;

public class Score {

    // 회차를 이 변수로 관리해도 괜찮을 것 같습니다.
    public static final int round = 10;

    private Long studentId; //학생 고유번호를 받아야 한다.
    private Long subjectId; // 과목 고유번호
    private String subType;

    private int[] scores = new int[round]; // 10회차 점수배열, 각 배열의 인덱스가 회차이다 -> scores[0]는 1회차 시험 점수
    private char[] ranks = new char[round]; // 10회차 등급배열, ranks[0]는 1회차 시험 등급

    public Score(Long studentId, Long subjectId, String subtype){
        Arrays.fill(this.scores, -1);// 입력되지 않은 값은 -1로 한다. 기본 초기화값이 0인데, 0점을 받는 학생도 있을 수 있기 때문
        Arrays.fill(this.ranks, ' ');
        this.studentId = studentId;
        this.subjectId = subjectId;
        this.subType = subtype;
    }

    public Long getStudentId(){
        return this.studentId;
    }

    public Long getSubjectId(){
        return this.subjectId;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int round,int score){
        this.scores[round] = score;
        if(this.subType.equals("SUBJECT_TYPE_MANDATORY")){
            if(score>=95 && score<=100) {
                this.ranks[round]='A';
            }
            else if(score>=90 && score<=94) {
                this.ranks[round]='B';
            }
            else if(score>=80 && score<=89) {
                this.ranks[round]='C';
            }
            else if(score>=70 && score<=79) {
                this.ranks[round]='D';
            }
            else if(score>=60 && score<=69) {
                this.ranks[round]='F';
            }
            else if(score<60) {
                this.ranks[round]='N';
            }
        }
        else if(this.subType.equals("SUBJECT_TYPE_CHOICE")){
            if(score>=90 && score<=100) {
                this.ranks[round]='A';
            }
            else if(score>=80 && score<=89) {
                this.ranks[round]='B';
            }
            else if(score>=70 && score<=79) {
                this.ranks[round]='C';
            }
            else if(score>=60 && score<=69) {
                this.ranks[round]='D';
            }
            else if(score>=50 && score<=59) {
                this.ranks[round]='F';
            }
            else if(score<50) {
                this.ranks[round]='N';
            }
        }

    }

    public char[] getRank() {
        return ranks;
    }

    public void setRank(char[] ranks){
        this.ranks = ranks;
    }
}
