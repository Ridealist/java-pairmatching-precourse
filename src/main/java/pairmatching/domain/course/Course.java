package pairmatching.domain.course;

import java.util.Arrays;

public enum Course {
    BACKEND("백엔드", "backend-crew"),
    FRONTEND("프론트엔드", "frontend-crew");

    private final String name;

    private final String fileName;

    Course(String name, String fileName) {
        this.name = name;
        this.fileName = fileName;
    }

    public static Course getValueOf(String name) {
        return Arrays.stream(Course.values())
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String getName() {
        return name;
    }

    public String getFileName() {
        return fileName;
    }
}
