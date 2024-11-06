package store.constants;

public enum Errors {
    //ItemName
    NULL_NAME("상품의 이름으로 null값이 입력되었습니다."),
    BLANK_NAME("상품의 이름으로 빈칸이 입력되었습니다."),
    LONG_NAME("상품의 이름이 %d글자를 초과했습니다"),

    //ItemPrice
    PRICE_UNDER_ZERO("상품의 가격은 0보다 커야합니다"),

    //ItemQuantity
    NEGATIVE_QUANTITY("상품의 개수는 음수일 수 없습니다"),
    OVER_QUANTITY_SELL("상품의 재고가 충분하지 않습니다");

    private static final String ERROR_PREFIX = "[ERROR]";

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
