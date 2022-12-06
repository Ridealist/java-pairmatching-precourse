package pairmatching.domain.map;

import java.util.ArrayList;
import java.util.List;
import pairmatching.domain.course.Course;
import pairmatching.domain.level.Level;

public class PairMapRepository {

    private final List<PairMap> pairMaps = new ArrayList<>();

    public void save(PairMap pairMap) {
        pairMaps.add(pairMap);
    }

    public PairMap find(Course course, Level level) {
        return pairMaps.stream()
                .filter(x -> x.getCourse().equals(course))
                .filter(x -> x.getLevel().equals(level))
                .findFirst()
                .orElse(null);
    }
}
