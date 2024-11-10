package store.domain.promotion;

import store.domain.shoppingcart.Purchase;

import java.time.LocalDate;

import static store.domain.shoppingcart.Purchase.*;

public class NullPromotion implements Promotion {

    private static NullPromotion instance;

    private NullPromotion() {
    }

    public static NullPromotion getInstance() {
        if (instance == null) {
            instance = new NullPromotion();
        }
        return instance;
    }

    @Override
    public boolean available(LocalDate orderedTime) {
        return false;
    }

    @Override
    public Purchase makePendingPromotablePurchase(String productName, int requestAmount, int promotionInventory) {
        return unPromotablePurchaseFrom(productName, requestAmount);
    }

    @Override
    public int calculateBonusToGive(int buyAmount) {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }

}
