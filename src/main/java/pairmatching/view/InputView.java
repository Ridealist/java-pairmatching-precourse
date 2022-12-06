package pairmatching.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import pairmatching.domain.course.Course;
import pairmatching.domain.level.Level;
import pairmatching.domain.mission.Mission;

public class InputView {
    private static final String CHOICE_ERROR_MESSAGE = "숫자 1, 2, 3 또는 영어 대문자 Q 중 하나를 입력해주세요.";
    private static final String PAIR_MATCHING_ERROR_MESSAGE = "화면에 나온대로 과정, 레벨, 미션을 콤마로 이어 붙여 정확히 입력해주세요.";
    private static final String REMATCHING_OR_NOT_ERROR_MESSAGE = "네 또는 아니오 둘 중에 하나를 정확히 입력해주세요.";
    private static final List<String> VALID_CHOICES = Arrays.asList("1", "2", "3", "Q");
    private static final List<String> VALID_REMATCHING_CHOICES = Arrays.asList("네", "아니오");
    private static final String PARSING_REGEX = ",";
    private static final int PAIR_MATCHING_PARAMS_LENGTH = 3;

    public static String readMenuChoice() throws IllegalArgumentException {
        String input = Console.readLine();
        validateMenuChoice(input);
        return input;
    }

    public static List<String> readPairMatchingChoice() throws IllegalArgumentException {
        String input = Console.readLine();
        return validatePairMatchingChoice(input);
    }

    public static String readReMatchingOrNot() throws IllegalArgumentException {
        String input = Console.readLine();
        validateRematchingChoice(input);
        return input;
    }

    private static void validateMenuChoice(String input) {
        if (!VALID_CHOICES.contains(input)) {
            throw new IllegalArgumentException(CHOICE_ERROR_MESSAGE);
        }
    }

    private static List<String> validatePairMatchingChoice(String str) {
        List<String> inputs = stringParser(str);
        validateLength(inputs);
        validateCourse(inputs.get(0));
        validateLevel(inputs.get(1));
        validateMission(inputs.get(2));
        return inputs;
    }

    private static void validateLength(List<String> inputs) {
        if (inputs.size() != PAIR_MATCHING_PARAMS_LENGTH) {
            throw new IllegalArgumentException(PAIR_MATCHING_ERROR_MESSAGE);
        }
    }

    private static void validateCourse(String name) {
        if (Course.getValueOf(name) == null) {
            throw new IllegalArgumentException(PAIR_MATCHING_ERROR_MESSAGE);
        }
    }

    private static void validateLevel(String name) {
        if (Level.getValueOf(name) == null) {
            throw new IllegalArgumentException(PAIR_MATCHING_ERROR_MESSAGE);
        }
    }

    private static void validateMission(String name) {
        if (Mission.getValueOf(name) == null) {
            throw new IllegalArgumentException(PAIR_MATCHING_ERROR_MESSAGE);
        }
    }

    private static void validateRematchingChoice(String input) {
        if (!VALID_REMATCHING_CHOICES.contains(input)) {
            throw new IllegalArgumentException(REMATCHING_OR_NOT_ERROR_MESSAGE);
        }
    }

    private static List<String> stringParser(String input) {
        return Arrays.stream(input.split(PARSING_REGEX))
                .map(String::trim)
                .collect(Collectors.toList());
    }
}
