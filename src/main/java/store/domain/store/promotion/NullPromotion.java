package store.domain.store.promotion;

import store.domain.Purchase;

import java.time.LocalDateTime;

import static store.domain.Purchase.*;

public class NullPromotion implements Promotion{

    @Override
    public boolean available(LocalDateTime orderedTime) {
        return false;
    }

    @Override
    public Purchase checkRequest(String productName, int requestAmount, int promotionInventory) {
        return normalPurchaseFrom(productName,requestAmount);
    }
}
