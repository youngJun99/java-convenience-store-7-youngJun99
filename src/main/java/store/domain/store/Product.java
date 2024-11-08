package store.domain.store;

import camp.nextstep.edu.missionutils.DateTimes;
import store.constants.InputErrors;
import store.domain.store.promotion.Promotion;
import store.domain.Purchase;
import store.dto.ReceiptDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static store.domain.Purchase.normalPurchaseFrom;

public class Product {

    private final String productName;
    private final Promotion promotion;
    private final int price;
    private int promotionInventory;
    private int normalInventory;

    public Product(String productName, Promotion promotion, int price, int promotionInventory, int normalInventory) {
        this.productName = productName;
        this.promotion = promotion;
        this.price = price;
        this.promotionInventory = promotionInventory;
        this.normalInventory = normalInventory;
    }

    public String getProductName() {
        return productName;
    }

    public Purchase makePendingPurchase(int requestAmount, LocalDate orderedTime) {
        validatePurchase(requestAmount);
        if (promotion.available(orderedTime)) {
            return checkPromotionRequest(requestAmount);
        }
        return normalPurchaseFrom(productName, requestAmount);
    }

    private Purchase checkPromotionRequest(int requestAmount) {
        return promotion.checkRequest(productName, requestAmount, promotionInventory);
    }


    public ReceiptDto executePurchase(Purchase purchase, LocalDate orderTime) {
        int promotableAmount = purchase.getPromotableAmount();
        int unPromotableAmount = purchase.getUnPromotableAmount();
        int totalAmount = promotableAmount + unPromotableAmount;
        int promotionBonus = promotion.calculateBonusToGive(promotableAmount);
        processInventory(totalAmount, orderTime);
        return new ReceiptDto(productName, price, totalAmount, promotionBonus);
    }

    private void processInventory(int totalAmount, LocalDate orderTime) {
        if (promotion.available(orderTime)) {
            if (totalAmount > promotionInventory) {
                totalAmount -= promotionInventory;
                promotionInventory = 0;
                normalInventory -= totalAmount;
            }
        }
        promotionInventory -= totalAmount;
    }

    private void validatePurchase(int requestAmount) {
        int TotalAmount = promotionInventory + normalInventory;
        if (TotalAmount < requestAmount) {
            throw new IllegalArgumentException(InputErrors.INVENTORY_SHORTAGE.getMessage());
        }
    }

}
