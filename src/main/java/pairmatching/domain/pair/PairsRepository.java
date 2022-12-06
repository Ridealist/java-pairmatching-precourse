package pairmatching.domain.pair;

import java.util.ArrayList;
import java.util.List;
import pairmatching.domain.course.Course;
import pairmatching.domain.level.Level;
import pairmatching.domain.mission.Mission;

public class PairsRepository {
    private static final List<Pairs> pairs = new ArrayList<>();

    public static boolean contains(Course course, Level level, Mission mission) {
        return pairs.stream()
                .filter(pairs -> pairs.getCourse().equals(course))
                .filter(pairs -> pairs.getLevel().equals(level))
                .anyMatch(pairs -> pairs.getMission().equals(mission));
    }

    public static void create(Pairs pairs) {
        PairsRepository.pairs.add(pairs);
    }

    public static Pairs find(Course course, Level level, Mission mission) {
        return pairs.stream()
                .filter(pairs -> pairs.getCourse().equals(course))
                .filter(pairs -> pairs.getLevel().equals(level))
                .filter(pairs -> pairs.getMission().equals(mission))
                .findFirst()
                .orElse(null);
    }

    public static void clear() {
        pairs.clear();
    }
}
