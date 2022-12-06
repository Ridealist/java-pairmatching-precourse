package pairmatching.controller;

import java.util.List;
import java.util.function.Supplier;
import pairmatching.domain.course.Course;
import pairmatching.domain.crew.CrewRepository;
import pairmatching.domain.level.Level;
import pairmatching.domain.mission.Mission;
import pairmatching.domain.pair.Pairs;
import pairmatching.domain.pair.PairsRepository;
import pairmatching.utils.FileHandler;
import pairmatching.validator.PairsValidator;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class MatchingController {
    private static final int COURSE_INDEX = 0;
    private static final int LEVEL_INDEX = 1;
    private static final int MISSION_INDEX = 2;
    private static final String REMATCHING_CHOICE = "네";

    private final CrewRepository crewRepository = new CrewRepository();

    public MatchingController() {
        setUp();
    }

    public String run() {
        OutputView.printStart();
        return repeatRequest(InputView::readMenuChoice);
    }

    private void setUp() {
        List<String> backendCrewNames = FileHandler.readCrewNames(Course.BACKEND);
        backendCrewNames.forEach(name -> crewRepository.save(Course.BACKEND, name));
        List<String> frontendCrewNames = FileHandler.readCrewNames(Course.FRONTEND);
        frontendCrewNames.forEach(name -> crewRepository.save(Course.FRONTEND, name));
    }

    public void setupPairMatching() {
        OutputView.printChoice();
        List<String> query = repeatRequest(InputView::readPairMatchingChoice);

        Course course = Course.getValueOf(query.get(COURSE_INDEX));
        Level level = Level.getValueOf(query.get(LEVEL_INDEX));
        Mission mission = Mission.getValueOf(query.get(MISSION_INDEX));

        Pairs pairs = new Pairs(course, level, mission);

        if (PairsRepository.contains(course, level, mission)) {
            OutputView.printRematchOrNot();
            String input = repeatRequest(InputView::readReMatchingOrNot);
            if (input.equals(REMATCHING_CHOICE)) {
                pairMatching(pairs);
            }
            return;
        }
        pairMatching(pairs);
    }

    public void pairMatching(Pairs pairs) {
        int count = 0;
        while (count < 3) {
            List<List<String>> pairedCrews = pairs.makePair(crewRepository);
            if (!pairs.hasPairAtLeastOnce(crewRepository, pairedCrews)) {
                break;
            }
            count++;
        }

        if (count == 3) {
            throw new IllegalStateException("페어 매칭이 불가능합니다.");
        }

        pairs.save(crewRepository);
        PairsRepository.create(pairs);

        OutputView.printResult(pairs);
    }

    public void searchPairMatching() {
        OutputView.printChoice();
        List<String> query = repeatRequest(InputView::readPairMatchingChoice);

        Course course = Course.getValueOf(query.get(COURSE_INDEX));
        Level level = Level.getValueOf(query.get(LEVEL_INDEX));
        Mission mission = Mission.getValueOf(query.get(MISSION_INDEX));

        hasMatchingHistory(course, level, mission);
    }

    private void hasMatchingHistory(Course course, Level level, Mission mission) {
        try {
            PairsValidator.validateMatchingHistory(course, level, mission);
            OutputView.printResult(PairsRepository.find(course, level, mission));
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
        }
    }

    public void initPairMatching() {
        PairsRepository.clear();
        OutputView.printInit();
    }

    private <T> T repeatRequest(Supplier<T> reader) {
        try {
            return reader.get();
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
            return repeatRequest(reader);
        }
    }
}
