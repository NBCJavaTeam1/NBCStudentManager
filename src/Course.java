enum Course {
    JAVA("Java", CourseType.REQUIRE),
    OOP("객체지향", CourseType.REQUIRE),
    SPRING("Spring", CourseType.REQUIRE),
    JPA("JPA", CourseType.REQUIRE),
    MYSQL("MySQL", CourseType.REQUIRE),
    DESIGN_PATTERNS("디자인 패턴", CourseType.SELECT),
    SPRING_SECURITY("Spring Security", CourseType.SELECT),
    REDIS("Redis", CourseType.SELECT),
    MONGODB("MongoDB", CourseType.SELECT);

    private final String courseName;
    private final CourseType courseType;

    Course(String courseName, CourseType courseType) {
        this.courseName = courseName;
        this.courseType = courseType;
    }

    public String getCourseName() {
        return courseName;
    }

    public CourseType getCourseType() {
        return courseType;
    }

}
