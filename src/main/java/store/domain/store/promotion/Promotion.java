package store.domain.store.promotion;

import store.domain.Purchase;

import java.time.LocalDate;

public interface Promotion {

    boolean available(LocalDate orderedTime);

    Purchase makePendingPromotionPurchase(String productName, int requestAmount, int promotionInventory);

    int calculateBonusToGive(int buyAmount);

    String getName();

}
