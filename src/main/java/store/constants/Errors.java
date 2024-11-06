package store.constants;

public enum Errors {
    //ItemName
    NULL_NAME("품목의 이름으로 null값이 입력되었습니다."),
    BLANK_NAME("품목의 이름으로 빈칸이 입력되었습니다."),
    LONG_NAME("품목의 이름이 %d글자를 초과했습니다"),

    //ItemPrice
    PRICE_UNDER_ZERO("품목의 가격은 0보다 커야합니다");

    private static final String ERROR_PREFIX = "[ERROR]";

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
