public class Score {

    // 과목 고유 번호
    private Long subjectId;

    // 학생 고유 번호
    private Long studentId;

    // 회차
    private int count;

    // 점수
    private double score;

    // 등급
   private char lank;

    public Score(Long subjectId, Long studentId, int count, double score, char lank) {
        this.subjectId = subjectId;
        this.studentId = studentId;
        this.count = count;
        this.score = score;
        this.lank = lank;
    }
}
