package store.vo;

import store.constants.Errors;

public class Quantity {

    private int quantity;

    public Quantity(int quantity) {
        isNotNegative(quantity);
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void add(int addAmount) {
        quantity += addAmount;
    }

    public void subtract(int subtractAmount) {
        validateSubtractionAmount(subtractAmount);
        quantity -= subtractAmount;
    }

    private void isNotNegative(int quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException(Errors.NEGATIVE_QUANTITY.getMessage());
        }
    }

    private void validateSubtractionAmount(int subtractQuantity) {
        if (subtractQuantity > quantity) {
            throw new IllegalArgumentException(String.format(Errors.OVER_QUANTITY_SUBTRACTION.getMessage(), quantity));
        }
    }
}
