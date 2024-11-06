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

    public void refill(int refillAmount) {
        itemQuantity += refillAmount;
    }

    public void sell(int sellAmount) {
        validateSellAmount(sellAmount);
        itemQuantity -= sellAmount;
    }

    private void isNotNegative(int itemQuantity) {
        if(itemQuantity < 0) {
            throw new IllegalArgumentException(Errors.NEGATIVE_QUANTITY.getMessage());
        }
    }

    private void validateSellAmount(int sellAmount) {
        if(sellAmount > itemQuantity){
            throw new IllegalArgumentException(Errors.OVER_QUANTITY_SELL.getMessage());
        }
    }
}
