package pairmatching.validator;

import pairmatching.domain.course.Course;
import pairmatching.domain.level.Level;
import pairmatching.domain.mission.Mission;
import pairmatching.domain.pair.PairsRepository;

public class PairsValidator {
    private static final String NO_MATCHING_HISTORY_ERROR_MESSAGE = "매칭 이력이 없습니다.";

    public static void validateMatchingHistory(Course course, Level level, Mission mission, PairsRepository pairsRepository)
            throws IllegalArgumentException {
        if (pairsRepository.find(course, level, mission) == null) {
            throw new IllegalArgumentException(NO_MATCHING_HISTORY_ERROR_MESSAGE);
        }
    }
}
