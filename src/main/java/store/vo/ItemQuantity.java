package store.vo;

import store.constants.Errors;

public class ItemQuantity {

    private final int itemQuantity;

    public ItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    private void isBiggerThanZero(int itemQuantity) {
        if(itemQuantity <= 0) {
            throw new IllegalArgumentException(Errors.PRICE_UNDER_ZERO.getMessage());
        }
    }
}
