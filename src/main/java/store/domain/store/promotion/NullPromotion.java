package store.domain.store.promotion;

import store.domain.Purchase;

import java.time.LocalDate;

import static store.domain.Purchase.*;

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
    public Purchase makePendingPromotionPurchase(String productName, int requestAmount, int promotionInventory) {
        return normalPurchaseFrom(productName, requestAmount);
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
