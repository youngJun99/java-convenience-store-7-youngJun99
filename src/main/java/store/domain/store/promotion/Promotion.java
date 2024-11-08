package store.domain.store.promotion;

import store.domain.Purchase;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface Promotion {

    boolean available(LocalDate orderedTime);

    Purchase checkRequest(String productName, int requestAmount, int promotionInventory);

    int calculateBonusToGive(int buyAmount);
}
