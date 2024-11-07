package store.vo;

import store.constants.Errors;

public class ItemQuantity {

    private int itemQuantity;

    public ItemQuantity(int itemQuantity) {
        isNotNegative(itemQuantity);
        this.itemQuantity = itemQuantity;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void add(int addAmount) {
        itemQuantity += addAmount;
    }

    public void subtract(int subtractAmount) {
        validateSubtractionAmount(subtractAmount);
        itemQuantity -= subtractAmount;
    }

    private void isNotNegative(int itemQuantity) {
        if(itemQuantity < 0) {
            throw new IllegalArgumentException(Errors.NEGATIVE_QUANTITY.getMessage());
        }
    }

    private void validateSubtractionAmount(int sellAmount) {
        if(sellAmount > itemQuantity){
            throw new IllegalArgumentException(String.format(Errors.OVER_QUANTITY_SUBTRACTION.getMessage(),itemQuantity));
        }
    }
}
