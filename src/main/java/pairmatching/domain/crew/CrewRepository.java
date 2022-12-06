package pairmatching.domain.crew;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import pairmatching.domain.course.Course;

public class CrewRepository {

    private final List<Crew> crews = new ArrayList<>();

    public void save(Course course, String name) {
        crews.add(new Crew(course, name));
    }

    public List<Crew> getCrewsByCourse(Course course) {
        return crews.stream()
                .filter(x -> x.getCourse().equals(course))
                .collect(Collectors.toList());
    }

    public Crew getCrewByName(String name) {
        return crews.stream()
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public List<String> getCrewNamesByCourse(Course course) {
        return crews.stream()
                .filter(x -> x.getCourse().equals(course))
                .map(Crew::getName)
                .collect(Collectors.toList());
    }
}
