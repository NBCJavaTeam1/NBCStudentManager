package model;

import java.util.Arrays;

public class Score {
    private Long studentsId; //학생 고유번호를 받아야 한다.
    private Long subjectId; // 과목 고유번호

    int[] scores = new int[10]; // 10회차 점수배열, 각 배열의 인덱스가 회차이다 -> scores[0]는 1회차 시험 점수
    char[] ranks = new char[10]; // 10회차 등급배열, ranks[0]는 1회차 시험 등급

    Score(Long studentNumber){
        Arrays.fill(this.scores, -1);// 입력되지 않은 값은 -1로 한다. 기본 초기화값이 0인데, 0점을 받는 학생도 있을 수 있기 때문
        Arrays.fill(this.ranks, ' ');
        this.studentsId = studentNumber;
    }


    //현재 필수과목, 선택과목을 구분하지 않고 점수계산함
    public void init(int round, int score) { // round : 회차입력받기, score : 점수입력 받기
        this.scores[round] = score;
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

}
