package pairmatching.domain.pair;

import pairmatching.domain.course.Course;
import pairmatching.domain.level.Level;
import pairmatching.domain.mission.Mission;

public class PairGroup {

    private final Course course;
    private final Level level;
    private final Mission mission;
    private final Pair pair;

    public PairGroup(Course course, Level level, Mission mission, Pair pair) {
        this.course = course;
        this.level = level;
        this.mission = mission;
        this.pair = pair;
    }

    public Course getCourse() {
        return course;
    }

    public Level getLevel() {
        return level;
    }

    public Mission getMission() {
        return mission;
    }

    public Pair getPair() {
        return pair;
    }
}
