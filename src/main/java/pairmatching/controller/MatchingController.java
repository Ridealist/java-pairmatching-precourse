package pairmatching.controller;

import java.util.List;
import java.util.function.Supplier;
import pairmatching.domain.course.Course;
import pairmatching.domain.crew.CrewRepository;
import pairmatching.domain.level.Level;
import pairmatching.domain.map.PairMap;
import pairmatching.domain.map.PairMapRepository;
import pairmatching.domain.mission.Mission;
import pairmatching.domain.pair.Pair;
import pairmatching.domain.pair.PairGroup;
import pairmatching.domain.pair.PairsRepository;
import pairmatching.utils.FileHandler;
import pairmatching.validator.PairsValidator;
import pairmatching.view.InputView;
import pairmatching.view.OutputView;

public class MatchingController {
    private final CrewRepository crewRepository = new CrewRepository();
    private final PairMapRepository pairMapRepository = new PairMapRepository();
    private final PairsRepository pairsRepository = new PairsRepository();

    public MatchingController() {
        loadCrewData();
    }

    public String run() {
        OutputView.printStart();
        return repeatRequest(InputView::readMenuChoice);
    }

    private void loadCrewData() {
        List<String> backendCrewNames = FileHandler.readCrewNames(Course.BACKEND);
        backendCrewNames.forEach(name -> crewRepository.save(Course.BACKEND, name));
        List<String> frontendCrewNames = FileHandler.readCrewNames(Course.FRONTEND);
        frontendCrewNames.forEach(name -> crewRepository.save(Course.FRONTEND, name));
    }

    public void executePairMatching() {
        OutputView.printChoice();
        List<String> query = repeatRequest(InputView::readPairMatchingChoice);
        Course course = Course.getValueOf(query.get(SystemConstant.COURSE_INDEX));
        Level level = Level.getValueOf(query.get(SystemConstant.LEVEL_INDEX));
        Mission mission = Mission.getValueOf(query.get(SystemConstant.MISSION_INDEX));
        if (stopPairMatching(course, level, mission)) {
            return;
        }
        pairMatching(course, level, mission);
    }

    public boolean stopPairMatching(Course course, Level level, Mission mission) {
        if (pairsRepository.find(course, level, mission) != null) {
            OutputView.printRematchOrNot();
            String input = repeatRequest(InputView::readReMatchingOrNot);
            return input.equals(SystemConstant.QUIT_REMATCHING_CHOICE);
        }
        return false;
    }

    public void pairMatching(Course course, Level level, Mission mission) {
        PairMap pairMap = loadPairMap(course, level);
        Pair pair = new Pair(crewRepository, pairMap, course);
        pairsRepository.create(course, level, mission, pair);
        pairMap.updateMap(pair.getPairNames());
        OutputView.printResult(pair);
    }

    private PairMap loadPairMap(Course course, Level level) {
        if (pairMapRepository.find(course, level) == null) {
            PairMap pairMap = new PairMap(course, level, crewRepository);
            pairMapRepository.save(pairMap);
            return pairMap;
        }
        return pairMapRepository.find(course, level);
    }

    public void searchPairMatching() {
        OutputView.printChoice();
        List<String> query = repeatRequest(InputView::readPairMatchingChoice);
        Course course = Course.getValueOf(query.get(SystemConstant.COURSE_INDEX));
        Level level = Level.getValueOf(query.get(SystemConstant.LEVEL_INDEX));
        Mission mission = Mission.getValueOf(query.get(SystemConstant.MISSION_INDEX));
        hasMatchingHistory(course, level, mission);
    }

    private void hasMatchingHistory(Course course, Level level, Mission mission) {
        try {
            PairsValidator.validateMatchingHistory(course, level, mission, pairsRepository);
            OutputView.printResult(pairsRepository.find(course, level, mission).getPair());
        } catch (IllegalArgumentException e) {
            OutputView.printError(e.getMessage());
        }
    }

    public void initPairMatching() {
        pairsRepository.clear();
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
