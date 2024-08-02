package model;

public class Subject {
    public enum SUBJECT_TYPE { SUBJECT_TYPE_MANDATORY, SUBJECT_TYPE_CHOICE };

    private Long subjectId;
    private String subjectName;
    private SUBJECT_TYPE subjectType;

    public Subject(Long subjectId, String subjectName, SUBJECT_TYPE subjectType) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectType = subjectType;
    }

    // Getter
    public Long getSubjectId() { return subjectId; }
    public String getSubjectName() {
        return subjectName;
    }
    public SUBJECT_TYPE getSubjectType() {
        return subjectType;
    }
}
