package store.constants;

public enum InputErrors {

    INVALID_AMOUNT_OR_NAME("올바르지 않은 형식으로 입력했습니다. 다시 입력해 주세요."),
    NO_SUCH_ITEM("존재하지 않는 상품입니다. 다시 입력해 주세요."),
    INVENTORY_SHORTAGE("재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."),
    WRONG_INPUT("잘못된 입력입니다. 다시 입력해 주세요."),

    STORE_BUILDING_ERROR("재고와 프로모션을 읽어오는 과정에서 에러가 발생했습니다."),
    EXCEED_MAX_RETRY_LIMIT("최대 재입력 횟수를 초과했습니다. 어플리케이션이 종료됩니다.");

    private static final String ERROR_PREFIX = "[ERROR] ";

    private String message;

    InputErrors(String message) {
        this.message = ERROR_PREFIX + message;
    }

    public String getMessage() {
        return message;
    }
}
