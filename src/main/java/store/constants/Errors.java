package store.constants;

public enum Errors {
    //ItemName
    NULL_NAME("상품의 이름으로 null값이 입력되었습니다."),
    BLANK_NAME("상품의 이름으로 빈칸이 입력되었습니다."),
    LONG_NAME("상품의 이름이 %d글자를 초과했습니다"),

    //ItemPrice
    PRICE_UNDER_ZERO("상품의 가격은 0보다 커야합니다"),

    //ItemQuantity
    NEGATIVE_QUANTITY("상품의 수량은 음수일 수 없습니다"),
    OVER_QUANTITY_SUBTRACTION("상품의 수량을 음수로 만들 수 없습니다, 현재 수량은 %d개 입니다.");

    private static final String ERROR_PREFIX = "[ERROR]";

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
