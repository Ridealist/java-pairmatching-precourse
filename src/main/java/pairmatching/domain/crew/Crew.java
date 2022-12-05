package pairmatching.domain.crew;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import pairmatching.domain.course.Course;
import pairmatching.domain.level.Level;

public class Crew {
    private final Course course;
    private final String name;
    private Map<Level, List<Crew>> matchingCrews = new HashMap<>();

    public Crew(Course course, String name) {
        this.course = course;
        this.name = name;
        initMatchingCrew();
    }

    private void initMatchingCrew() {
        for (Level level : Level.values()) {
            List<Crew> crews = new ArrayList<>();
            matchingCrews.put(level, crews);
        }
    }

    public void setMatchingCrews(Level level, Crew other) {
        List<Crew> crews = matchingCrews.get(level);
        crews.add(other);
        matchingCrews.put(level, crews);
    }

    public boolean hasPaired(Level level, Crew other) {
        return matchingCrews.get(level).contains(other);
    }

    public String getName() {
        return name;
    }

    public Course getCourse() {
        return course;
    }
}
