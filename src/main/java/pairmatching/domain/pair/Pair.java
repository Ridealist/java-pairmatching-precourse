package pairmatching.domain.pair;

import camp.nextstep.edu.missionutils.Randoms;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import pairmatching.domain.course.Course;
import pairmatching.domain.crew.CrewRepository;
import pairmatching.domain.map.PairMap;

public class Pair {

    private static final int PAIR_MATCHING_ATTEMPT_BOUND = 3;
    private static final String PAIR_MATCHING_ERROR_MESSAGE = "페어 매칭이 불가능합니다.";
    private final List<List<String>> pairNames;

    public Pair(CrewRepository crewRepository, PairMap pairMap, Course course) throws IllegalStateException {
        this.pairNames = validatePairs(crewRepository, pairMap, course);
    }

    private List<List<String>> validatePairs(CrewRepository crewRepository, PairMap pairMap, Course course) {
        for (int i = 0; i < PAIR_MATCHING_ATTEMPT_BOUND; i++) {
            List<List<String>> pairs = makePairs(crewRepository, course);
            if (pairMap.hasPairAtLeastOnce(pairs)) {
                continue;
            }
            return pairs;
        }
        throw new IllegalStateException(PAIR_MATCHING_ERROR_MESSAGE);
    }

    private List<List<String>> makePairs(CrewRepository crewRepository, Course course) {
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

    public List<List<String>> getPairNames() {
        return Collections.unmodifiableList(pairNames);
    }
}
