package store.vo;

import store.constants.InputErrors;

public class PurchaseQuantity {

    private int quantity;

    public PurchaseQuantity(int quantity) {
        isNotNegative(quantity);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    private void isNotNegative(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(InputErrors.INVALID_AMOUNT_OR_NAME.getMessage());
        }
    }
}
