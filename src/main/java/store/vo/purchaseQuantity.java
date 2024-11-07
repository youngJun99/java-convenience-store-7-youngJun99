package store.vo;

import store.constants.Errors;

public class purchaseQuantity {

    private int quantity;

    public purchaseQuantity(int quantity) {
        isNotNegative(quantity);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    private void isNotNegative(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(Errors.INVALID_AMOUNT_OR_NAME.getMessage());
        }
    }
}
