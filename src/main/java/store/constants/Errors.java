package store.constants;

public enum Errors {
    //Name
    NULL_NAME("이름으로 null값이 입력되었습니다."),
    BLANK_NAME("이름으로 빈칸이 입력되었습니다."),
    LONG_NAME("이름이 %d글자를 초과했습니다"),

    //Money
    NEGATIVE_MONEY("돈은 음수일 수 없습니다"),
    OVER_MONEY_SUBTRACTION("돈을 음수로 만들 수 없습니다, 현재 돈은 %원 입니다."),

    //Price
    ZERO_PRICE("상품의 가격은 0보다 커야 합니다"),

    //Quantity
    NEGATIVE_QUANTITY("수량은 음수일 수 없습니다"),
    OVER_QUANTITY_SUBTRACTION("수량을 음수로 만들 수 없습니다, 현재 수량은 %d개 입니다."),

    //Product
    NULL_IN_PRODUCT_CREATION("상품의 구성요소는 null값이 들어갈 수 없습니다");

    private static final String ERROR_PREFIX = "[ERROR]";

    private String message;

    Errors(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
