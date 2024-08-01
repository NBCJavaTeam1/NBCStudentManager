public enum selectSubjectEnum {
    DESIGN_PATTERNS("디자인 패턴"),
    SPRING_SECURITY("Spring Security"),
    REDIS("Redis"),
    MONGODB("MongoDB");

    private final String courseName;

    selectSubjectEnum(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }
}
