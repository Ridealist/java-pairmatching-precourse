package pairmatching.domain.pair;

import java.util.ArrayList;
import java.util.List;
import pairmatching.domain.course.Course;
import pairmatching.domain.level.Level;
import pairmatching.domain.mission.Mission;

public class PairsRepository {
    private final List<PairGroup> pairs = new ArrayList<>();

    public void create(Course course, Level level, Mission mission, Pair pair) {
        pairs.add(new PairGroup(course, level, mission, pair));
    }

    public PairGroup find(Course course, Level level, Mission mission) {
        return pairs.stream()
                .filter(pairGroup -> pairGroup.getCourse().equals(course))
                .filter(pairGroup -> pairGroup.getLevel().equals(level))
                .filter(pairGroup -> pairGroup.getMission().equals(mission))
                .findFirst()
                .orElse(null);
    }

    public void clear() {
        pairs.clear();
    }
}
