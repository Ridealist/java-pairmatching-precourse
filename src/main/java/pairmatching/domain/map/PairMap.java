package pairmatching.domain.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import pairmatching.domain.course.Course;
import pairmatching.domain.crew.CrewRepository;
import pairmatching.domain.level.Level;

public class PairMap {

    private final Course course;
    private final Level level;

    private final Map<String, Set<String>> pairMap;

    public PairMap(Course course, Level level, CrewRepository crewRepository) {
        this.course = course;
        this.level = level;
        this.pairMap = initMap(crewRepository);
    }

    private Map<String, Set<String>> initMap(CrewRepository crewRepository) {
        Map<String, Set<String>> pairMap = new HashMap<>();
        List<String> crewNames = crewRepository.getCrewNamesByCourse(course);
        for (String name : crewNames) {
            Set<String> emptyPairedCrews = new HashSet<>();
            pairMap.put(name, emptyPairedCrews);
        }
        return pairMap;
    }

    public boolean hasPairAtLeastOnce(List<List<String>> pairCrews) {
        for (List<String> pairs : pairCrews) {
            if (hasPairedEachCrews(pairs)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPairedEachCrews(List<String> crewNames) {
        crewNames = new ArrayList<>(crewNames);
        while (crewNames.size() > 1) {
            String name = crewNames.remove(0);
            if (hasPairedOneAnother(name, crewNames)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPairedOneAnother(String name, List<String> crews) {
        for (String otherName : crews) {
            if (pairMap.get(name).contains(otherName)) {
                return true;
            }
        }
        return false;
    }

    public void updateMap(List<List<String>> pairCrews) {
        for (List<String> pairs : pairCrews) {
            mappingPairs(pairs);
        }
    }

    private void mappingPairs(List<String> crews) {
        for (int firstIdx = 0; firstIdx < crews.size() - 1; firstIdx++) {
            for (int secondIdx = firstIdx + 1; secondIdx < crews.size(); secondIdx++) {
                Set<String> emptyPairedCrews = new HashSet<>();
                pairMap.getOrDefault(crews.get(firstIdx), emptyPairedCrews).add(crews.get(secondIdx));
                pairMap.getOrDefault(crews.get(secondIdx), emptyPairedCrews).add(crews.get(firstIdx));
            }
        }
    }

    public Course getCourse() {
        return course;
    }

    public Level getLevel() {
        return level;
    }
}
