package store.domain.promotion;

import store.domain.shoppingcart.Purchase;

import java.time.LocalDate;

public interface Promotion {

    boolean available(LocalDate orderedTime);

    Purchase makePendingPromotablePurchase(String productName, int requestAmount, int promotionInventory);

    int calculateBonusToGive(int buyAmount);

    String getName();

}
