package store.constants;

public enum Errors {

    PURCHASE_AMOUNT("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NO_SUCH_ITEM("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVENTORY_SHORTAGE("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    WRONG_INPUT("잘못된 입력입니다. 다시 입력해 주세요.");

    private static final String ERROR_PREFIX = "[ERROR] ";

    private String message;

    Errors(String message) {
        this.message = ERROR_PREFIX+message;
    }

    public String getMessage() {
        return message;
    }
}
