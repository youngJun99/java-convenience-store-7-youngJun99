package store.vo;

import store.constants.Errors;

public class ItemPrice {

    private final int price;

    public ItemPrice(int price) {
        isBiggerThanZero(price);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    private void isBiggerThanZero(int price) {
        if(price <= 0) {
            throw new IllegalArgumentException(Errors.PRICE_UNDER_ZERO.getMessage());
        }
    }
}
