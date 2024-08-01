public enum RequireEnum {
    JAVA("Java"),
    OOP("객체지향"),
    SPRING("Spring"),
    JPA("JPA"),
    MYSQL("MySQL");

    private final String courseName;

    RequireEnum(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseName() {
        return courseName;
    }
}
