package store.vo;

import store.constants.Errors;

public class Money {

    private int money;

    public Money(int money) {
        isNotNegative(money);
        this.money = money;
    }

    public int getPrice() {
        return money;
    }

    public void add(int amount) {
        money+=amount;
    }

    public void subtract(int amount) {
        money-=amount;
    }

    private void isNotNegative(int money) {
        if(money < 0) {
            throw new IllegalArgumentException(Errors.NEGATIVE_MONEY.getMessage());
        }
    }

    private void validateSubtractionAmount(int subtractAmount) {
        if(subtractAmount > money){
            throw new IllegalArgumentException(String.format(Errors.OVER_MONEY_SUBTRACTION.getMessage(), money));
        }
    }

}
