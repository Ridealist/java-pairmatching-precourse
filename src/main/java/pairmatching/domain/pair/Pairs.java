package pairmatching.domain.pair;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import pairmatching.domain.course.Course;
import pairmatching.domain.crew.Crew;
import pairmatching.domain.crew.CrewRepository;
import pairmatching.domain.level.Level;
import pairmatching.domain.mission.Mission;

public class Pairs {

    private final Course course;
    private final Level level;
    private final Mission mission;
    private List<List<String>> pair;

    public Pairs(Course course, Level level, Mission mission) {
        this.course = course;
        this.level = level;
        this.mission = mission;
    }

    public List<List<String>> makePair(CrewRepository crewRepository) {
        List<String> shuffledCrews = Randoms.shuffle(crewRepository.getCrewNamesByCourse(course));
        List<List<String>> pairCrews = new ArrayList<>();
        addSubList(shuffledCrews, pairCrews);
        return pairCrews;
    }

    private void addSubList(List<String> shuffledCrews, List<List<String>> pairCrews) {
        int size = shuffledCrews.size();
        for (int i = 0; i <= size - 2; i += 2) {
            if (i == size - 3) {
                pairCrews.add(shuffledCrews.subList(i, i + 3));
                break;
            }
            pairCrews.add(shuffledCrews.subList(i, i + 2));
        }
    }

    public boolean hasPairAtLeastOnce(CrewRepository crewRepository, List<List<String>> pairs) {
        for (List<String> pair : pairs) {
            List<Crew> pairedCrew = mapNameToCrew(crewRepository, pair);
            if (hasPairedEachCrews(pairedCrew)) {
                return true;
            }
        }
        return false;
    }

    private List<Crew> mapNameToCrew(CrewRepository crewRepository, List<String> names) {
        return Arrays.stream(names.toArray())
                .map(name -> crewRepository.getCrewByName((String) name))
                .collect(Collectors.toList());
    }

    private boolean hasPairedEachCrews(List<Crew> crews) {
        while (crews.size() > 0) {
            Crew crewOne = crews.remove(0);
            if (hasPairedOneAnother(crewOne, crews)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPairedOneAnother(Crew crewOne, List<Crew> crews) {
        for (Crew crew : crews) {
            if (crewOne.hasPaired(level, crew)) {
                return true;
            }
        }
        return false;
    }

    public void save(CrewRepository crewRepository) {
        pair = makePair(crewRepository);
    }

    public List<List<String>> getPair() {
        return Collections.unmodifiableList(pair);
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
}
