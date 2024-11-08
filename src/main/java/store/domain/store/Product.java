package store.domain.store;

import store.constants.InputErrors;
import store.domain.store.promotion.Promotion;
import store.domain.Purchase;

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



    public Purchase makePendingPurchase(int requestAmount, LocalDateTime orderedTime) {
        validatePurchase(requestAmount);
        if (promotion.available(orderedTime)) {
            return checkPromotionRequest(requestAmount);
        }
        return normalPurchaseFrom(productName,requestAmount);
    }

    private Purchase checkPromotionRequest(int requestAmount) {
        return promotion.checkRequest(productName,requestAmount,promotionInventory);
    }


    public void confirmPurchase(int promotionInventoryRequest, int normalInventoryRequest) {
        processInventory(promotionInventoryRequest, normalInventoryRequest);
    }

    private void processInventory(int promotionInventoryRequest, int normalInventoryRequest) {
        this.promotionInventory -= promotionInventoryRequest;
        if(normalInventoryRequest >normalInventory) {
            normalInventoryRequest -= normalInventory;
            normalInventory=0;
            promotionInventory -= promotionInventoryRequest;
        }
        this.normalInventory -= normalInventoryRequest;
    }

    private void validatePurchase(int requestAmount) {
        int TotalAmount = promotionInventory + normalInventory;
        if (TotalAmount < requestAmount) {
            throw new IllegalArgumentException(InputErrors.INVENTORY_SHORTAGE.getMessage());
        }
    }

}
