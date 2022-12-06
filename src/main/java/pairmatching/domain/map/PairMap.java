package pairmatching.domain.map;

import java.util.Collections;
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
    private Map<String, Set<String>> pairMap;

    public PairMap(Course course, Level level, CrewRepository crewRepository) {
        this.course = course;
        this.level = level;
        this.pairMap = initMap(crewRepository);
    }

    private Map<String, Set<String>> initMap(CrewRepository crewRepository) {
        Map<String, Set<String>> pairMap = new HashMap<>();
        List<String> crewNames = crewRepository.getCrewNamesByCourse(course);
        for (String name : crewNames) {
            Set<String> pairedCrews = new HashSet<>();
            pairMap.put(name, pairedCrews);
        }
        return pairMap;
    }

    public boolean hasPaired(List<List<String>> pairCrews) {
        for (List<String> pairs : pairCrews) {
            if (hasPairedEachCrews(pairs)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasPairedEachCrews(List<String> crewNames) {
        while (crewNames.size() > 0) {
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
        for (int i = 0; i < crews.size() - 1; i++) {
            for (int j = i + 1; j < crews.size(); j++) {
                pairMap.get(crews.get(i)).add(crews.get(j));
                pairMap.get(crews.get(j)).add(crews.get(i));
            }
        }
    }

    public Map<String, Set<String>> getPairMap() {
        return Collections.unmodifiableMap(this.pairMap);
    }
}
