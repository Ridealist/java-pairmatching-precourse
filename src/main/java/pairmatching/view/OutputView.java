package pairmatching.view;

import java.util.List;
import pairmatching.domain.pair.Pairs;

public class OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";

    private static final String START_MESSAGE
            = "기능을 선택하세요.\n"
            + "1. 페어 매칭\n"
            + "2. 페어 조회\n"
            + "3. 페어 초기화\n"
            + "Q. 종료";

    private static final String CHOICE_MESSAGE
            = "#############################################\n"
            + "과정: 백엔드 | 프론트엔드\n"
            + "미션:\n"
            + "  - 레벨1: 자동차경주 | 로또 | 숫자야구게임\n"
            + "  - 레벨2: 장바구니 | 결제 | 지하철노선도\n"
            + "  - 레벨3: \n"
            + "  - 레벨4: 성능개선 | 배포\n"
            + "  - 레벨5: \n"
            + "############################################\n"
            + "과정, 레벨, 미션을 선택하세요.\n"
            + "ex) 백엔드, 레벨1, 자동차경주";

    private static final String RE_MATCHING_MESSAGE
            = "매칭 정보가 있습니다. 다시 매칭하시겠습니까?\n"
            + "네 | 아니오";

    private static final String RESULT_MESSAGE = "페어 매칭 결과입니다.";
    private static final String INIT_MESSAGE = "초기화 되었습니다.";
    private static final String DELIMITER = " : ";

    public static void printError(String message) {
        System.out.println(ERROR_PREFIX + message);
    }

    public static void printStart() {
        System.out.println();
        System.out.println(START_MESSAGE);
    }

    public static void printChoice() {
        System.out.println();
        System.out.println(CHOICE_MESSAGE);
    }

    public static void printRematchOrNot() {
        System.out.println();
        System.out.println(RE_MATCHING_MESSAGE);
    }

    public static void printInit() {
        System.out.println();
        System.out.println(INIT_MESSAGE);
    }

    public static void printResult(Pairs pairs) {
        System.out.println();
        System.out.println(RESULT_MESSAGE);
        List<List<String>> pairCrews = pairs.getPair();
        for (List<String> pairUnit : pairCrews) {
            System.out.println(String.join(DELIMITER, pairUnit));
        }
    }
}
