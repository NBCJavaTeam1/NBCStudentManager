package dummy;

import model.Score;
import model.Student;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static dummy.DummyDataFactory.MANDATORY_TYPE_SUBJECT_COUNT;

public class ScoreFactory {
    final int SCORE_RANGE = 100;
    final int ROUND_RANGE = 10;

    public void setRandomScore(Random rand, Score score) {
        for(int i = 0; i < 10; ++i) {
            score.setScores(rand.nextInt(ROUND_RANGE), rand.nextInt(SCORE_RANGE));
        }
    }

    // 랜덤한 학생 생성하는 함수
    public Score createRandomScore(Random rand, long studentId, long subjectId) {
        String subjectType = subjectId < MANDATORY_TYPE_SUBJECT_COUNT ? "SUBJECT_TYPE_MANDATORY" : "SUBJECT_TYPE_CHOICE";
        Score result = new Score(studentId, subjectId, subjectType);

        setRandomScore(rand, result);

        return result;
    }
}