package store.vo;

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
            throw new IllegalArgumentException("품목의 가격은 0보다 커야합니다");
        }
    }
}
