package pairmatching.domain.mission;

import java.util.Arrays;

public enum Mission {
    RACING("자동차경주"),
    LOTTO("로또"),
    BASEBALL("숫자야구게임"),
    SHOPPING_CART("장바구니"),
    PAY("결제"),
    SUBWAY("지하철노선도"),
    UPGRADE("성능개선"),
    DEPLOY("배포");

    private final String name;

    Mission(String name) {
        this.name = name;
    }

    public static Mission getValueOf(String name) {
        return Arrays.stream(Mission.values())
                .filter(x -> x.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public String getName() {
        return name;
    }
}
